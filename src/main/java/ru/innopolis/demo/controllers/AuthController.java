package ru.innopolis.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.innopolis.demo.configurations.TokenAuthenticationProvider;
import ru.innopolis.demo.data.UserRepository;
import ru.innopolis.demo.models.Role;
import ru.innopolis.demo.models.UserProfile;
import ru.innopolis.demo.payloads.LogInRequest;
import ru.innopolis.demo.payloads.LogInResponse;
import ru.innopolis.demo.payloads.SignUpRequest;
import ru.innopolis.demo.payloads.UserPayload;

@RestController
public class AuthController {
    private final UserRepository userRepository;
    private final TokenAuthenticationProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, TokenAuthenticationProvider tokenProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

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
}
