package com.bksproject.bksproject.Service;


import com.bksproject.bksproject.Model.ResetPasswordToken;
import com.bksproject.bksproject.Model.Users;
import com.bksproject.bksproject.Repository.ResetPasswordTokenRepository;
import com.bksproject.bksproject.Repository.UserRepository;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;

@Service
public class MailjetService  {
//    @Value("${mailjet.api.key}")
//    private String apiKey;
//
//    @Value("${mailjet.api.secret}")
//    private String apiSecret;
//
//    @Value("${mailgun.api-key}")
//    private String apiKey2;
//
//    @Value("${mailgun.domain}")
//    private String domain;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;


//    public void sendEmail(String to, String subject, String text) {
//        MailjetClient client;
//        MailjetRequest request;
//        MailjetResponse response;
//        client = new MailjetClient(apiKey, apiSecret);
//        request = new MailjetRequest(Emailv31.resource)
//                .property(Emailv31.MESSAGES, new JSONArray()
//                        .put(new JSONObject()
//                                .put("From", new JSONObject().put("Email", "sonnvt05@gmail.com"))
//                                .put("To", new JSONArray().put(new JSONObject().put("Email", to)))
//                                .put("Subject", subject)
//                                .put(Emailv31.Message.TEXTPART, "Email to get a new password!")
//                                .put(Emailv31.Message.HTMLPART, "<h3>Please click here to change password <a href=\"http://www.mailjet.com\"></a>!</h3><br />May the delivery force be with you!")
//                                .put(Emailv31.Message.URLTAGS, "token=" + text)
//                        )
//                );
//        try {
//            response = client.post(request);
//            System.out.println(response.getStatus());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void sendEmail2(String to, String subject, String text) throws MailjetException, MailjetSocketTimeoutException {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient("29655c3a492e4518df19a9fd373fcedd", "0ab6900a9c44b3e54b151dbe830cc80c", new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "sonnvt05@gmail.com")
                                        .put("Name", "son"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", to)))
                                .put(Emailv31.Message.SUBJECT, subject)
                                .put(Emailv31.Message.TEXTPART, text)
                                .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger 1, welcome to <a href='https://www.mailjet.com/'>Mailjet</a>!</h3><br />May the delivery force be with you!")
                                .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }

    public String verifyResetPasswordToken(String token) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token);
        if(resetPasswordToken == null) {
            return "Invalid token";
        }
        if (resetPasswordToken.getExpiryDate().isBefore(Instant.now())) {
            return "Token has expired";
        }
        return "Valid token";
    }

    public void saveNewPassword(Users user, String password){
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }
}
