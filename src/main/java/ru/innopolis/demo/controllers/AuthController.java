package ru.innopolis.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.innopolis.demo.configurations.TokenAuthenticationProvider;
import ru.innopolis.demo.data.UserRepository;
import ru.innopolis.demo.models.Role;
import ru.innopolis.demo.models.UserProfile;
import ru.innopolis.demo.models.UserProfileDetails;
import ru.innopolis.demo.payloads.*;
import ru.tinkoff.eclair.annotation.Log;

/**
 * This class contains API for requests connected with users
 */
@RestController
public class AuthController {
    private final UserRepository userRepository;
    private final TokenAuthenticationProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, TokenAuthenticationProvider tokenProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Log(LogLevel.INFO)
    @PostMapping("/login")
    public LogInResponse login(@RequestBody LogInRequest user){
        try{
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(auth);

            String token =tokenProvider.createToken(auth);

            return new LogInResponse(token);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
        }

    }

    @Log(LogLevel.INFO)
    @PostMapping("/signup")
    public UserPayload signUp(@RequestBody SignUpRequest user){
        if (userRepository.existsByEmail(user.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with such email already exists");
        }

        UserProfile userProfile = new UserProfile();
        userProfile.setEmail(user.getEmail());
        userProfile.setName(user.getName());
        userProfile.setSurname(user.getSurname());
        userProfile.setBirthday(user.getBirthday());

        userProfile.setRole(Role.ROLE_CUSTOMER);
        userProfile.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userProfile);

        return new UserPayload(userProfile);

    }

    @Log(LogLevel.INFO)
    @PatchMapping("/update/login")
    public void updateLogin(@RequestBody EmailPayload newEmail, @AuthenticationPrincipal UserProfileDetails currentUser){

        if (userRepository.existsByEmail(newEmail.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A user with such email already exists");
        }

        UserProfile userProfile = userRepository.findByEmail(currentUser.getUsername()).get();
        userProfile.setEmail(newEmail.getEmail());
        userRepository.save(userProfile);
    }

    @Log(LogLevel.INFO)
    @PatchMapping("/update/password")
    public void updatePassword(@RequestBody PasswordPayload newPassword){

        UserProfileDetails currentUser = (UserProfileDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfile user = userRepository.findByEmail(currentUser.getUsername()).get();
        user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
        userRepository.save(user);

    }
}
