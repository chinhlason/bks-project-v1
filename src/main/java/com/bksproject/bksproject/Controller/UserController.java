package com.bksproject.bksproject.Controller;

import com.bksproject.bksproject.Enum.User_roles;
import com.bksproject.bksproject.Model.Role;
import com.bksproject.bksproject.Model.Users;
import com.bksproject.bksproject.Repository.UserRepository;
import com.bksproject.bksproject.Service.ModelMapperService;
import com.bksproject.bksproject.Service.UserDetailsImp;
import com.bksproject.bksproject.advice.CustomMapper;
import com.bksproject.bksproject.exception.System.EmailExistException;
import com.bksproject.bksproject.exception.System.UserNotFoundException;
import com.bksproject.bksproject.payload.request.UpdateUserRequest;
import com.bksproject.bksproject.payload.response.LoginResponse;
import com.bksproject.bksproject.payload.response.MessageResponse;
import com.bksproject.bksproject.payload.response.UserInforResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "http://localhost:3000",maxAge = 3600,allowCredentials = "true")
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ModelMapperService mapperService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;



    public CustomMapper<Users, UserInforResponse> customMapper = user -> {
        UserInforResponse userResponse = mapper.map(user,UserInforResponse.class);
        Set<Role> setRole = user.getRoles();
        List<User_roles> listRole = setRole.stream().map(role -> role.getName()).collect(Collectors.toList());
        userResponse.setRole(listRole);
        return userResponse;
    };

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("list-all")
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateByUsername(@RequestParam("username") String username, @Valid @RequestBody UpdateUserRequest infoUpdate) throws UserNotFoundException,EmailExistException {
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        if(!user.getEmail().equals(infoUpdate.getEmail())){
            user.setEmail(infoUpdate.getEmail());
            if(userRepository.existsByEmail(infoUpdate.getEmail())){
                throw new EmailExistException("");
            }
        }
        user.setFullname(infoUpdate.getFullname());
        user.setPhone(infoUpdate.getPhone());
        userRepository.save(user);
        return new ResponseEntity(new MessageResponse("Update succesfully"), OK);

    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/get-user-by-username")
    public ResponseEntity<?> getCurrentUserInformation(@RequestParam("username") String username) throws UserNotFoundException {
        Users userInfor = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return ResponseEntity.ok(mapperService.mapObject(userInfor,customMapper));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, Authentication authentication) throws UserNotFoundException{
        try {
            Authentication _authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authentication.getName(), oldPassword));
            UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();
            Users userInfor = userRepository.findByUsername(userDetailsImp.getUsername()).orElseThrow(() -> new UserNotFoundException(userDetailsImp.getUsername()));
            userInfor.setPassword(encoder.encode(newPassword));
            userRepository.save(userInfor);
            return new ResponseEntity<>(new MessageResponse("Password has changed"), OK);
        }catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("Wrong password"), HttpStatus.BAD_REQUEST);
        }
    }




}
