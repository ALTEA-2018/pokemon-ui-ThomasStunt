package com.miage.altea.tp.pokemon_ui.config;

import com.miage.altea.tp.pokemon_ui.trainers.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private TrainerService trainerService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().ignoringAntMatchers("/**");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(() -> "ROLE_USER");
        return s ->
                Optional.ofNullable(this.trainerService.getTrainer(s))
                        .map(trainer -> new User(trainer.getName(), trainer.getPassword(), true, true, true, true, roles))
                        .orElseThrow(() -> new BadCredentialsException("No such user"));
    }

    @Autowired
    public void setTrainersService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }
}