package bsd.kschmitt.eval.controller;

import bsd.kschmitt.eval.model.User;
import bsd.kschmitt.eval.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@CrossOrigin
public class ConnexionController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestBody User User) {

        try {
            AppUserDetails appUserDetails = (AppUserDetails) authenticationProvider
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    User.getEmail(),
                                    User.getPassword()))
                    .getPrincipal();

            return ResponseEntity.ok(jwtUtils.generationToken(appUserDetails.getUsername()));

        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}