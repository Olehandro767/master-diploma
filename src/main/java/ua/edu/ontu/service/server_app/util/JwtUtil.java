package ua.edu.ontu.service.server_app.util;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ua.edu.ontu.service.server_app.dto.jwt.TokenResult;

import java.util.UUID;

@Service
public class JwtUtil {

    @Getter
    private final String JWT_START_KEY_PART = "ONTU ";
    private final String secretJwtCode = UUID.randomUUID().toString();

    public String generateAdminToken(String login) {
        return null;
    }

    public TokenResult getAdminInfoFromToken(String jwtToken) {
        return null;
    }
}