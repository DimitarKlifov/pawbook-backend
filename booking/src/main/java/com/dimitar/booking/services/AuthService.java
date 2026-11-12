package com.dimitar.booking.services;

import com.dimitar.booking.config.CurrentUserProvider;
import com.dimitar.booking.config.JwtUtil;
import com.dimitar.booking.dto.RegisterRequest;
import com.dimitar.booking.dto.UpdateProfileRequest;
import com.dimitar.booking.dto.UserResponse;
import com.dimitar.booking.entities.User;
import com.dimitar.booking.repositories.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.SplittableRandom;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final JwtUtil jwtUtil;
    private final CurrentUserProvider currentUserProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender, JwtUtil jwtUtil, CurrentUserProvider currentUserProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.jwtUtil = jwtUtil;
        this.currentUserProvider = currentUserProvider;
    }


    public void register(RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already registered." );
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
        String verificationLink = "http://localhost:5173/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verify your email - PawBook");
        message.setText("Please click the link below to verify your email:\n\n" + verificationLink);

        mailSender.send(message);
    }

    public void forgotPassword(String email){
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null){
            return;
        }

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        sendResetEmail(user.getEmail(), token);
    }

    public void resetPassword(String token, String newPassword){
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired reset token"));

        if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Invalid or expired reset token");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }


    private void sendResetEmail(String toEmail, String token){
        String resetLink = "http://localhost:5173/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Reset your password - PawBook");
        message.setText("Click the link below to reset your password.This link expires in 1 hour:\n\n" + resetLink);

        mailSender.send(message);
    }


    public UserResponse updateProfile(UpdateProfileRequest request) {
        User currentUser = currentUserProvider.getCurrentUser();

        currentUser.setName(request.getName());
        currentUser.setPhoneNumber(request.getPhoneNumber());

        if (request.getNewPassword() != null && !request.getNewPassword().isBlank()) {
            currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        User saved = userRepository.save(currentUser);

        return new UserResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getPhoneNumber(), saved.getRole());
    }

}