/**
 * SPDX-FileCopyrightText: 2018-2023 SAP SE or an SAP affiliate company and Cloud Security Client Java contributors
 * <p>
 * SPDX-License-Identifier: Apache-2.0
 */
package com.sap.cloud.security.samples.resecurity.config;

import com.sap.cloud.security.spring.config.IdentityServicesPropertySourceFactory;
import com.sap.cloud.security.spring.token.authentication.AuthenticationToken;
import com.sap.cloud.security.token.TokenClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity(debug = true) // TODO "debug" may include sensitive information. Do not use in a production system!
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@PropertySource(factory = IdentityServicesPropertySourceFactory.class, ignoreResourceNotFound = true, value = { "" })
public class SecurityConfiguration {

    @Autowired
    Converter<Jwt, AbstractAuthenticationToken> authConverter; // Required only when Xsuaa is used

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/iasusers");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(authz ->
                        authz.requestMatchers("/companies/*").hasAuthority("Read")
                                .requestMatchers("/locations/*").hasAuthority("Read")
                                .requestMatchers("/profits/*").hasAuthority("Read")
                                .requestMatchers("/projects/*").hasAuthority("Read")
                                .requestMatchers("/buildings/*").hasAuthority("Read")
                                .requestMatchers("/buildingtypes/*").hasAuthority("Read")
                                .requestMatchers("/units/*").hasAuthority("Read")
                                .requestMatchers("/unitorientations/*").hasAuthority("Read")
                                .requestMatchers("/unitfixture/*").hasAuthority("Read")
                                .requestMatchers("/unitstatuses/*").hasAuthority("Read")
                                .requestMatchers("/unitviews/*").hasAuthority("Read")
                                .requestMatchers("/usagetype/*").hasAuthority("Read")
                                .requestMatchers("/unitsubtypes/*").hasAuthority("Read")
                                .requestMatchers("/unitfloors/*").hasAuthority("Read")
                                .requestMatchers("/areas/*").hasAuthority("Read")
                                .requestMatchers("/projectareas/*").hasAuthority("Read")
                                .requestMatchers("/buildingareas/*").hasAuthority("Read")
                                .requestMatchers("/unitareas/*").hasAuthority("Read")
                                .requestMatchers("/measurements/*").hasAuthority("Read")
                                .requestMatchers("/*").authenticated()
                                .anyRequest().denyAll())
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(new MyCustomHybridTokenAuthenticationConverter());

        http.csrf().ignoringRequestMatchers("/companies/*","/locations/*","/profits/*","/projects/*",
                "/buildings/*","/buildingtypes/*","/units/*","/unitorientations/*","/unitfixture/*",
                "/unitstatuses/*","/unitviews/*","/usagetype/*","/unitsubtypes/*","/unitfloors/*","/areas/*",
                "/projectareas/*","/buildingareas/*","/unitareas/*","/measurements/*","/iasusers/*");

        return http.build();
    }

    /**
     * Workaround for hybrid use case until Cloud Authorization Service is globally available.
     */
    class MyCustomHybridTokenAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

        public AbstractAuthenticationToken convert(Jwt jwt) {
            if (jwt.hasClaim(TokenClaims.XSUAA.EXTERNAL_ATTRIBUTE)) {
                return authConverter.convert(jwt);
            }
            return new AuthenticationToken(jwt, deriveAuthoritiesFromGroup(jwt));
        }

        private Collection<GrantedAuthority> deriveAuthoritiesFromGroup(Jwt jwt) {
            Collection<GrantedAuthority> groupAuthorities = new ArrayList<>();
            if (jwt.hasClaim(TokenClaims.GROUPS)) {
                List<String> groups = jwt.getClaimAsStringList(TokenClaims.GROUPS);
                for (String group : groups) {
                    groupAuthorities.add(new SimpleGrantedAuthority(group.replace("IASAUTHZ_", "")));
                }
            }
            return groupAuthorities;
        }
    }

    /**
     * Workaround for IAS only use case until Cloud Authorization Service is globally available.
     */

    static class MyCustomIasTokenAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

        public AbstractAuthenticationToken convert(Jwt jwt) {
            final List<String> groups = jwt.getClaimAsStringList(TokenClaims.GROUPS);
            final List<GrantedAuthority> groupAuthorities = groups == null ? Collections.emptyList()
                    : groups.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            return new AuthenticationToken(jwt, groupAuthorities);
        }
    }
}