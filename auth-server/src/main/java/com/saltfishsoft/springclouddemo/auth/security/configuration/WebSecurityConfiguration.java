package com.saltfishsoft.springclouddemo.auth.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

/**
 * Created by Shihongbing on 2019/10/16.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public static void main(String agrs[]){
        //BCryptPasswordEncoder aa = new BCryptPasswordEncoder();
        //$2a$10$0zwUeaUvQyorIftgvsTjbu.rA/yu0TTrFMs3nsPYc56PgPVaBWbJe
        //$2a$10$hTNdZzoB3i/11JDEf9rUk.F1FKWDX4gdflRbO16PXfA226/Zvtok.
        String token ="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXV0aC1zZXJ2ZXIiLCJhY2NvdW50LXNlcnZlciJdLCJ1c2VyX25hbWUiOiLlj7LnuqLlhbUiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdLCJleHAiOjE1OTUzMTc2MjUsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiNjgyMWI4ZjYtOTFlZC00N2Q1LWIzMzktOTQ0MTkyNGRiNzEwIiwiY2xpZW50X2lkIjoiNEFfV0VCIn0.NiSdIVrRBx_XAj_boRgxjNl16TrhCBuKq8PKtJx6EzgX3xVfWW6wmryxyS--woPkZGlyTBp5c5sk-Yfo6sCbb6rvzBDxars-PnEbzEkbDLaR4YFpUoSbFonGAl4hFIR4idQdxAvFw2Zygm8kjp2OG71cuxtsjgvETRzufxkeWQv3VqbIVc1-qQ3yRdc9g23aUhgVL9Hd6jml6Z3XSY_ZBpBjUT7kC6vUAWn_f-VK-OaNJF4Ca2jTSGLfh-bBCzS2HlpzsvHa297c5s8icCMVLhsuR35EISTMeQ0Me3uOC-k3oZx0bzQAY8VZ1NjLif-6H5w9WpszZPB8661q267zJw";
        Jwt jwt = JwtHelper.decode(token);
        System.out.println(jwt.toString());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
