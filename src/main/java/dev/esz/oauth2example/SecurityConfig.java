package dev.esz.oauth2example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.oauth2Login();

        http.authorizeRequests()
                .mvcMatchers("/login/**").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(ClientRegistration clientRegistration) {
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    @Bean
    public ClientRegistration clientRegistration(@Value("${client.id}") String clientId,
                                                 @Value("${client.secret}") String clientSecret,
                                                 @Value("${authorization.uri}") String authorizationUri,
                                                 @Value("${token.uri}") String tokenUri,
                                                 @Value("${userinfo.uri}") String userInfoUri,
                                                 @Value("${redirect.uri}") String redirectUri,
                                                 @Value("${jwks.uri}") String jwksUri) {
        return ClientRegistration.withRegistrationId("cognito")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope("openid")
                .authorizationUri(authorizationUri)
                .tokenUri(tokenUri)
                .userInfoUri(userInfoUri)
                .userNameAttributeName("cognito:username")
                .clientName("cognito")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(redirectUri)
                .jwkSetUri(jwksUri)
                .build();
    }
}
