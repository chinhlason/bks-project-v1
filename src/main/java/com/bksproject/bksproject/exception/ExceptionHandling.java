package com.bksproject.bksproject.exception;

import com.bksproject.bksproject.Model.Notification;
import com.bksproject.bksproject.exception.System.*;
import org.springframework.security.authentication.BadCredentialsException;
import com.bksproject.bksproject.payload.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final String EMAIL_EXIST = "Email is already taken!";
    private static final String USER_NOT_FOUND = "User not found!";
    private static final String WRONG_INF_TO_LOGIN = "Username or password is incorrect!";
    private static final String POST_NOT_FOUND = "Post not found";

    private static final String COMMENT_NOT_FOUND = "Comment not found";
    private static final String NOTIFICATION_NOT_FOUND = "Notification not found";


    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus,String reason, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                reason.toUpperCase(), message), httpStatus);
    }
    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> emailExistException(EmailExistException exception) {
        LOGGER.warn(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, EMAIL_EXIST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException exception) {
        LOGGER.warn(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, USER_NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> wrongLoginInforException(BadCredentialsException exception) {
        LOGGER.warn(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, WRONG_INF_TO_LOGIN);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<HttpResponse> postNotFoundException(PostNotFoundException exception) {
        LOGGER.warn(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, POST_NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<HttpResponse> commentNotFound(CommentNotFoundException exception) {
        LOGGER.warn(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, COMMENT_NOT_FOUND);
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<HttpResponse> notificationException(NotificationException exception) {
        LOGGER.warn(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, NOTIFICATION_NOT_FOUND);
    }
}
