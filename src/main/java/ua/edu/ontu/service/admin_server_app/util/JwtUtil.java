package ua.edu.ontu.service.admin_server_app.util;

import static com.auth0.jwt.JWT.create;
import static com.auth0.jwt.JWT.require;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ua.edu.ontu.service.LogUtil;
import ua.edu.ontu.service.admin_server_app.dto.jwt.TokenResult;
import ua.edu.ontu.service.admin_server_app.enumeration.Role;

@Slf4j
@Service
public class JwtUtil {

	@Getter
	private final String JWT_START_KEY_PART = "ONTU ";
	@Getter
	private final int JWT_START_KEY_PART_LENGTH = this.JWT_START_KEY_PART.length();
	private final String SECRET_JWT_CODE = UUID.randomUUID().toString();
	private final Algorithm ALGORITHM = Algorithm.HMAC256(this.SECRET_JWT_CODE);

	private String generateToken(Role role, String login) {
		return create().withClaim("role", role.getLowercaseName()).withClaim("login", login)
				.withClaim("end-session", LocalDateTime.now().plusHours(2).toString()).withIssuer("OAuth2")
				.sign(this.ALGORITHM);
	}

	private TokenResult decodeToken(Role role, String token) {
		try {
			var decodedJwt = require(this.ALGORITHM).withIssuer("OAuth2").withClaim("role", role.getLowercaseName())
					.build().verify(token.substring(this.JWT_START_KEY_PART_LENGTH));
			var parsedLocalDateTime = LocalDateTime.parse(decodedJwt.getClaim("end-session").asString());
			return new TokenResult(decodedJwt.getClaim("login").asString(),
					LocalDateTime.now().isBefore(parsedLocalDateTime));
		} catch (SignatureVerificationException exception) {
			LogUtil.logError(log, LogUtil.ADMIN_PANEL_JWT_ERROR,
					"Can not decode token { token = \"" + token + "\", role = " + role.name() + " }");
			return null;
		}
	}

	public String generateAdminToken(String login) {
		return this.generateToken(Role.ADMIN, login);
	}

	public TokenResult getAdminInfoFromToken(String jwtToken) {
		return this.decodeToken(Role.ADMIN, jwtToken);
	}
}