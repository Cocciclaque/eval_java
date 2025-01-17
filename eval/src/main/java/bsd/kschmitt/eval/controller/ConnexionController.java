package bsd.kschmitt.eval.controller;

import bsd.kschmitt.eval.model.Users;
import bsd.kschmitt.eval.security.AppUserDetails;
import bsd.kschmitt.eval.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ConnexionController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestBody Users User) {

        AppUserDetails appUserDetails = (AppUserDetails) authenticationProvider
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                User.getEmail(),
                                User.getPassword()))
                .getPrincipal();

        return ResponseEntity.ok(jwtUtils.generationToken(appUserDetails.getUsername()));

    }
}