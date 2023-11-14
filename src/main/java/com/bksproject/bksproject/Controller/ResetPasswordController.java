package com.bksproject.bksproject.Controller;

import com.bksproject.bksproject.Model.ResetPasswordToken;
import com.bksproject.bksproject.Model.Users;
import com.bksproject.bksproject.Repository.ResetPasswordTokenRepository;
import com.bksproject.bksproject.Repository.UserRepository;
import com.bksproject.bksproject.Service.Impl.MailjetService;
import com.bksproject.bksproject.exception.System.UserNotFoundException;
import com.bksproject.bksproject.payload.response.HttpResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
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
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email)  throws MailjetException, MailjetSocketTimeoutException {
        Instant expiryInstant = Instant.now().plus(30, ChronoUnit.MINUTES);
        String subject = "Reset password";
        Users user = userRepository.findByEmail(email);
        if(user == null){
            return new ResponseEntity<>(new HttpResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,"",String.format("No user found with email %s", email)),HttpStatus.BAD_REQUEST);
        }
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByUserIdReset(user.getId());
        String token = UUID.randomUUID().toString();
        if(resetPasswordToken != null){
            resetPasswordToken.setToken(token);
            resetPasswordToken.setExpiryDate(expiryInstant);
            resetPasswordTokenRepository.save(resetPasswordToken);
            mailjetService.sendEmail2(email, subject, resetPasswordToken.getToken());
        } else {
            ResetPasswordToken tokenToReset = new ResetPasswordToken(
                    token,
                    expiryInstant,
                    user
            );
            resetPasswordTokenRepository.save(tokenToReset);
            mailjetService.sendEmail2(email, subject, tokenToReset.getToken());
        }
        return new ResponseEntity<>(new HttpResponse(OK.value(), OK,"","Email sent"), OK);
    }

    @PostMapping("general/reset-password")
    public ResponseEntity<?> verifyTokenAndResetPassword(@RequestParam("token") String token, @RequestParam("password") String password) throws UserNotFoundException{
        String verifyToken = mailjetService.verifyResetPasswordToken(token);
        if(verifyToken == "Invalid token"){
            return new ResponseEntity<>(new HttpResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,"",String.format(verifyToken)),HttpStatus.BAD_REQUEST);
        } else if (verifyToken == "Token has expired") {
            return new ResponseEntity<>(new HttpResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,"",String.format(verifyToken)),HttpStatus.BAD_REQUEST);
        }
        ResetPasswordToken tokenFound = resetPasswordTokenRepository.findByToken(token);
        Users userChange = userRepository.findByUsername(tokenFound.getUserIdReset().getUsername())
                .orElseThrow(() -> new UserNotFoundException(tokenFound.getUserIdReset().getUsername()));
        mailjetService.saveNewPassword(userChange, password);
        tokenFound.setToken("");
        return new ResponseEntity<>(new HttpResponse(OK.value(), OK,"","Change password success!"), OK);
    }

}
