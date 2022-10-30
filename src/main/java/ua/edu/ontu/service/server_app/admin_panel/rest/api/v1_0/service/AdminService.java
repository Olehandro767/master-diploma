package ua.edu.ontu.service.server_app.admin_panel.rest.api.v1_0.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ontu.service.server_app.admin_panel.rest.api.v1_0.database.repo.IAdministratorRepository;
import ua.edu.ontu.service.server_app.admin_panel.rest.api.v1_0.dto.CheckAdminResult;
import ua.edu.ontu.service.server_app.admin_panel.rest.api.v1_0.request.common.SignInRequest;
import ua.edu.ontu.service.server_app.dto.jwt.TokenResult;
import ua.edu.ontu.service.server_app.util.EncryptionUtil;
import ua.edu.ontu.service.server_app.util.JwtUtil;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final IAdministratorRepository administratorRepository;
    private final EncryptionUtil encryptionUtil;
    private final JwtUtil jwtUtil;

    public TokenResult checkToken(String token) {
        return this.jwtUtil.getAdminInfoFromToken(token);
    }

    public CheckAdminResult checkAdmin(SignInRequest signInRequest) {
        try {
            var admin = this.administratorRepository.findByLogin(signInRequest.getLogin());
            return new CheckAdminResult(admin, Objects.nonNull(admin)
                    && admin.checkPassword(signInRequest.getPassword(), this.encryptionUtil));
        } catch (Exception ignore) {}
        return new CheckAdminResult(null, false);
    }

    public String generateToken(String login) {
        return this.jwtUtil.generateAdminToken(login);
    }
}