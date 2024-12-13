package bsd.kschmitt.eval.security;


import bsd.kschmitt.eval.model.Users;
import bsd.kschmitt.eval.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {

        Users optionalUtilisateur = UserRepository.findByEmail(Email);

        //if (optionalUtilisateur.isEmpty()) {
          //  throw new UsernameNotFoundException("Email introuvable");
        //}

        return new AppUserDetails(optionalUtilisateur);
    }
}