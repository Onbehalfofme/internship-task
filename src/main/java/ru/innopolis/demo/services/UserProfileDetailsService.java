package ru.innopolis.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.demo.data.UserRepository;
import ru.innopolis.demo.models.UserProfile;
import ru.innopolis.demo.models.UserProfileDetails;


import java.util.Optional;

/**
 * This class is used for web security
 */
@Service
public class UserProfileDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public UserProfileDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        Optional<UserProfile> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Email: " + email + " not found");
        }

        return new UserProfileDetails(user.get());
    }

}
