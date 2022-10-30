package ua.edu.ontu.service.server_app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.edu.ontu.service.server_app.admin_panel.rest.api.v1_0.database.repo.IAdministratorRepository;
import ua.edu.ontu.service.server_app.admin_panel.rest.api.v1_0.database.service.SessionService;
import ua.edu.ontu.service.server_app.admin_panel.rest.api.v1_0.database.util.InitializationUtilV1_0;
import ua.edu.ontu.service.server_app.util.EncryptionUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ServerConfiguration implements WebMvcConfigurer {
    private final String corsUrls;
    private final IAdministratorRepository administratorRepository;

    @Autowired
    public ServerConfiguration(
            @Value("${server.cors}") String corsUrls,
            IAdministratorRepository administratorRepository,
            EncryptionUtil encryptionUtil
    ) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        this.corsUrls = corsUrls;
        this.administratorRepository = administratorRepository;
        new InitializationUtilV1_0(this.administratorRepository, encryptionUtil).setUpTheFirstLaunchOfTheApplication();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var urls = this.corsUrls.trim().split(",");
        String[] methods = { HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name() };
        registry.addMapping("/api/**").allowedOrigins(urls).allowedMethods(methods).allowedHeaders("*");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/**").setViewName("forward:/static/admin/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Bean
    public WebSecurityCustomizer ignoringUrlsConfiguration() {
        return (web) -> web.ignoring().antMatchers("/static/**");
    }

    @Bean
    public SecurityFilterChain httpConfiguration(
            HttpSecurity http,
            @Qualifier("session_service_v1.0") SessionService sessionServiceV1_0
    ) throws Exception {
        var paths = new String[] { "/admin", "/api/v1.0/admin/sign-in", };
        return http
                .authorizeRequests().antMatchers(paths).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .securityContext()
                .and()
                .addFilterBefore(new RequestFilter(sessionServiceV1_0), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public ObjectMapper createGlobalJacksonObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return (authentication) -> authentication;
    }
}

@RequiredArgsConstructor
class RequestFilter extends OncePerRequestFilter {

    private final SessionService sessionServiceV1_0;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication =
                (this.sessionServiceV1_0.authorizationHeaderIsValid(request.getHeader(HttpHeaders.AUTHORIZATION)))
                        ? new UsernamePasswordAuthenticationToken(null, null, new ArrayList<>(
                        List.of((GrantedAuthority) () -> "USER")
                )) : new UsernamePasswordAuthenticationToken(null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}