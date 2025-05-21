package com.quizzapp.config;


import com.quizzapp.config.filter.JwtTokenValidator;
import com.quizzapp.service.UserDetailServiceImpl;
import com.quizzapp.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;







    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { // httpSecurity se pasa por todos los filtros

        return httpSecurity
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf-> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http-> {
                    //configurar endpoints publicos
                    http.requestMatchers(HttpMethod.POST, "auth/**").permitAll();
//

                    //configurar endpoints privados


                    http.requestMatchers(HttpMethod.GET,"api/user/profile").hasAnyRole("ADMIN","USER");
                    http.requestMatchers(HttpMethod.PUT,"api/user/edit-profile").hasAnyRole("ADMIN","USER");
                    http.requestMatchers(HttpMethod.GET, "admin/users/findAll").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT,"admin/users/updateUser/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE,"admin/users/deleteUser/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/admin/questions/saveQuestion").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/admin/questions/deleteQuestion/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/admin/questions/AllQuestion").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/admin/questions/updateQuestion/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/admin/questions/findQuestion/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/admin/questions/random").hasAnyRole("ADMIN","USER");


                    http.requestMatchers(HttpMethod.POST, "/api/games/startGame").hasAnyAuthority("JUGAR");
                    http.requestMatchers(HttpMethod.POST, "/api/games/saveGame").hasAnyAuthority("JUGAR");
                    http.requestMatchers(HttpMethod.GET, "/api/games/findGamesByUserId/**").hasAnyRole("ADMIN","USER"); // Permitir acceso al historial de partidas del usuario
                    http.requestMatchers(HttpMethod.GET, "/api/games/ranking").hasAnyRole("ADMIN","USER"); // Permitir acceso al ranking
                    http.requestMatchers(HttpMethod.DELETE, "/admin/games/deleteGame/**").hasRole("ADMIN"); // Solo administradores
                    http.requestMatchers(HttpMethod.GET, "/admin/games/findAll").hasRole("ADMIN"); // Solo administradores



                    http.requestMatchers(HttpMethod.POST, "method/post").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "method/update").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "method/delete").hasRole("ADMIN");

                    http.requestMatchers(HttpMethod.GET, "method/play").hasAnyAuthority("JUGAR");


                    // cualquier otro endpoint, prohibe
                    http.anyRequest().denyAll();
//                    http.anyRequest().authenticated();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();

    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200")); // Permitir Angular frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }



    /* componenete que adminstra la autorizacion, se crea a partir del objeto AuthenticationConfiguration */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;

    }

    /* definir usuarios en memoria, de momento, para simular que se traen los users desde una  base de datos */



    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("1234"));
    }









}
