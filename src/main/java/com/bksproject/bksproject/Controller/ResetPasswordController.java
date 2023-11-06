package com.bksproject.bksproject.Controller;

import com.bksproject.bksproject.Model.ResetPasswordToken;
import com.bksproject.bksproject.Model.Users;
import com.bksproject.bksproject.Repository.ResetPasswordTokenRepository;
import com.bksproject.bksproject.Repository.UserRepository;
import com.bksproject.bksproject.Service.MailjetService;
import com.bksproject.bksproject.payload.response.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000",maxAge = 3600,allowCredentials = "true")
public class ResetPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    private MailjetService mailjetService;

    @PostMapping("general/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email){
        Instant expiryInstant = Instant.now().plus(30, ChronoUnit.MINUTES);
        String subject = "Reset password";
        Users user = userRepository.findByEmail(email);
        if(user == null){
            return new ResponseEntity<>(new HttpResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,"",String.format("No user found with email %s", email)),HttpStatus.BAD_REQUEST);
        }
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByUserIdReset(user.getId());
        if(resetPasswordToken != null){
            resetPasswordTokenRepository.deleteById(resetPasswordToken.getId());
        }
        String token = UUID.randomUUID().toString();
        ResetPasswordToken tokenToReset = new ResetPasswordToken(
                token,
                expiryInstant,
                user
        );
        resetPasswordTokenRepository.save(tokenToReset);
        mailjetService.sendEmail(email, subject, tokenToReset.getToken());
        return new ResponseEntity<>(new HttpResponse(OK.value(), OK,"","Email sent"), OK);
    }

}
