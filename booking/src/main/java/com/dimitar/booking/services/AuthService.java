package com.dimitar.booking.services;

import com.dimitar.booking.config.JwtUtil;
import com.dimitar.booking.dto.RegisterRequest;
import com.dimitar.booking.entities.User;
import com.dimitar.booking.repositories.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.SplittableRandom;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already registered: " + request.getEmail());
        }

        User user = new User(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getPhoneNumber(), User.Role.CLIENT);

        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setEmailVerified(false);

        userRepository.save(user);

        sendVerificationEmail(request.getEmail(), token);
    }

    public String verifyEmail(String token){
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired verification token: "));

        user.setEmailVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);

        return jwtUtil.generateToken(user.getEmail());
    }

    public String login(String email, String rawPassword){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        if (!user.isEmailVerified()) {
            throw new IllegalArgumentException("Please verify your email before logging in");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    public void sendVerificationEmail(String toEmail, String token){
        String verificationLink = "http://localhost:8080/auth/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verify your email - PawBook");
        message.setText("Please click the link below to verify your email:\n\n" + verificationLink);

        mailSender.send(message);
    }


}
