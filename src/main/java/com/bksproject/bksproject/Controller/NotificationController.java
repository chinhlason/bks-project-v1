package com.bksproject.bksproject.Controller;

import com.bksproject.bksproject.DTO.PostDTO;
import com.bksproject.bksproject.Enum.NotificationTypes;
import com.bksproject.bksproject.Model.Comments;
import com.bksproject.bksproject.Model.Notification;
import com.bksproject.bksproject.Model.Posts;
import com.bksproject.bksproject.Model.Users;
import com.bksproject.bksproject.Repository.NotificationRepository;
import com.bksproject.bksproject.Repository.UserRepository;
import com.bksproject.bksproject.Service.ModelMapperService;
import com.bksproject.bksproject.advice.CustomMapper;
import com.bksproject.bksproject.exception.System.NotificationException;
import com.bksproject.bksproject.exception.System.UserNotFoundException;
import com.bksproject.bksproject.payload.request.PaymentRequest;
import com.bksproject.bksproject.payload.response.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000",maxAge = 3600,allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ModelMapperService mapperService;

    @PostMapping("/notification/create")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> createNotificationByUser(@Valid @RequestParam("serial_course") String serial_course, Authentication authentication) throws UserNotFoundException {
        String username = authentication.getName();
        String usernameAdmin = "sonnvt";
        NotificationTypes type = NotificationTypes.PAYMENT_REQUEST;
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Users receiver = userRepository.findByUsername(usernameAdmin).orElseThrow(() -> new UserNotFoundException(usernameAdmin));
        Notification notification = new Notification(
            serial_course,
            user,
            type.name(),
            receiver
        );
        notificationRepository.save(notification);
        return ResponseEntity.ok().body(new MessageResponse("Create notification success!"));
    }

    @PostMapping("/notification/admin/create")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createNotificationByAdmin(@Valid Authentication authentication,
                                                @RequestBody PaymentRequest paymentRequest,
                                                @RequestParam("receiver_id") Long receiver_id) throws UserNotFoundException {
        String username = authentication.getName();
        NotificationTypes type = NotificationTypes.PAYMENT_CONFIRMATION;
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Users receiver = userRepository.findById(receiver_id).orElseThrow(() -> new UsernameNotFoundException(receiver_id.toString()));
        if(paymentRequest.getMessage().isEmpty()){
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","Message can not be empty"));
        }
        Notification notification = new Notification(
                paymentRequest.getMessage(),
                user,
                type.name(),
                receiver
        );
        notificationRepository.save(notification);
        return ResponseEntity.ok().body(new MessageResponse("Create notification success!"));
    }

    @PostMapping("/notification/system/create")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createNotificationBySystem(@Valid Authentication authentication,
                                                @RequestBody PaymentRequest paymentRequest,
                                                @RequestParam("receiver_id") Long receiver_id) throws UserNotFoundException {
        String username = authentication.getName();
        NotificationTypes type = NotificationTypes.SYSTEM_TO_USER;
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Users receiver = userRepository.findById(receiver_id).orElseThrow(() -> new UsernameNotFoundException(receiver_id.toString()));
        if(paymentRequest.getMessage().isEmpty()){
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","Message can not be empty"));
        }
        Notification notification = new Notification(
                paymentRequest.getMessage(),
                user,
                type.name(),
                receiver
        );
        notificationRepository.save(notification);
        return ResponseEntity.ok().body(new MessageResponse("Create notification success!"));
    }

    public CustomMapper<Notification, NotificationResponse> customMapper = notification -> {
        NotificationResponse notificationResponse = mapper.map(notification,NotificationResponse.class);
        notificationResponse.setUserSend(notification.getUserNotification().getUsername());
        return notificationResponse;
    };

    @GetMapping("/notification/get-notification")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> getNotificationByUser(Authentication authentication) throws UserNotFoundException, NotificationException{
        String username = authentication.getName();
        String exception = "";
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        List<Notification> notification = notificationRepository.getNotificationByReceiverID(user.getId());
        return ResponseEntity.ok(mapperService.mapList(notification,customMapper));
    }

    @GetMapping("/notification/admin/get-notification")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getNotificationByAdmin(Authentication authentication) throws UserNotFoundException, NotificationException{
        String username = authentication.getName();
        String exception = "";
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        List<Notification> notification = notificationRepository.getNotificationByReceiverID(user.getId());
        return ResponseEntity.ok(mapperService.mapList(notification,customMapper));
    }

    @GetMapping("/general/get-notification")
    public ResponseEntity<?> getNotificationGeneral() throws UserNotFoundException, NotificationException{
        NotificationTypes type = NotificationTypes.SYSTEM_TO_USER;
        List<Notification> notification = notificationRepository.getNotificationByType(type.name());
        return ResponseEntity.ok(mapperService.mapList(notification,customMapper));
    }

    @PutMapping("/notification-was-read")
    public ResponseEntity<?> readNotification(@RequestParam("id") Long id, Authentication authentication)
            throws UserNotFoundException, NotificationException{
        String username = authentication.getName();
        String exception = "";
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new NotificationException(exception));
        List<Notification> notifications = notificationRepository.getNotificationByReceiverID(user.getId());
        if(notifications.contains(notification)){
            notification.setRead(true);
            notificationRepository.save(notification);
            return ResponseEntity.ok(new MessageResponse("Action succesfully"));
        }
        return ResponseEntity.ok(new MessageResponse("Action failed"));
        }

//    public List<NotificationResponse> convertNotificationListToResponseList(List<Notification> notifications) {
//        return notifications.stream()
//                .map(notification -> new NotificationResponse(
//                        notification.getId(),
//                        notification.getCreateAt(),
//                        notification.getMessage(),
//                        notification.getType(),
//                        notification.isRead(),
//                        notification.getUserNotification()
//                ))
//                .collect(Collectors.toList());
//    }
}
