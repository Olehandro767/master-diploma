package ua.edu.ontu.service.admin_server_app.util;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import org.springframework.stereotype.Service;
import ua.edu.ontu.service.admin_server_app.dto.jwt.TokenResult;
import ua.edu.ontu.service.admin_server_app.enumeration.Role;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.auth0.jwt.JWT.create;
import static com.auth0.jwt.JWT.require;

@Service
public class JwtUtil {

    @Getter
    private final String JWT_START_KEY_PART = "ONTU ";
    private final String SECRET_JWT_CODE = UUID.randomUUID().toString();
    private final Algorithm ALGORITHM = Algorithm.HMAC256(this.SECRET_JWT_CODE);
    private final int JWT_START_KEY_PART_LENGTH = this.JWT_START_KEY_PART.length();


    private String generateToken(Role role, String login) {
        return create()
                .withClaim("role", role.getLowercaseName())
                .withClaim("login", login)
                .withClaim("end-session", LocalDateTime.now().plusHours(2).toString())
                .withIssuer("OAuth2")
                .sign(this.ALGORITHM);
    }

    private TokenResult decodeToken(Role role, String token) {
        var decodedJwt = require(this.ALGORITHM).withIssuer("OAuth2")
                .withClaim("role", role.getLowercaseName()).build()
                .verify(token.substring(this.JWT_START_KEY_PART_LENGTH));
        var parsedLocalDateTime = LocalDateTime.parse(decodedJwt.getClaim("end-session").asString());
        return new TokenResult(decodedJwt.getClaim("login").asString(),
                LocalDateTime.now().isBefore(parsedLocalDateTime));
    }

    public String generateAdminToken(String login) {
        return this.generateToken(Role.ADMIN, login);
    }

    public TokenResult getAdminInfoFromToken(String jwtToken) {
        return this.decodeToken(Role.ADMIN, jwtToken);
    }
}