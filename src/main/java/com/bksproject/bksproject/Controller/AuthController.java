package com.bksproject.bksproject.Controller;


import com.bksproject.bksproject.DTO.LoginDTO;
import com.bksproject.bksproject.DTO.RegisterDTO;
import com.bksproject.bksproject.Enum.User_roles;
import com.bksproject.bksproject.Model.RefreshToken;
import com.bksproject.bksproject.Model.Role;
import com.bksproject.bksproject.Model.Users;
import com.bksproject.bksproject.Repository.RoleRepository;
import com.bksproject.bksproject.Repository.UserRepository;
import com.bksproject.bksproject.Security.Cookie.cookieService;
import com.bksproject.bksproject.Security.RefreshTokenService;
import com.bksproject.bksproject.Security.jwt.jwtUtil;
import com.bksproject.bksproject.Service.UserDetailsImp;
import com.bksproject.bksproject.exception.TokenRefreshException;
import com.bksproject.bksproject.payload.response.LoginResponse;
import com.bksproject.bksproject.payload.response.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000",maxAge = 3600,allowCredentials = "true")
@RestController
@RequestMapping("/api/user/auth/")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private jwtUtil jwtGenerator;
    private cookieService cookie;
    private RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, jwtUtil jwtGenerator,
                          RefreshTokenService refreshTokenService, cookieService cookie) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.refreshTokenService = refreshTokenService;
        this.cookie = cookie;
    }

    @PostMapping("signup")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        Users user = new Users();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setFullname(registerDTO.getFullname());
        user.setPhone(registerDTO.getPhone());

        Role roles = roleRepository.findByName(User_roles.ROLE_USER).orElse(null);
        Set<Role> roleUser = new HashSet<>();
        if (roles == null) {
            // Xử lý trường hợp không tìm thấy Role
        } else {
            user.setRoles(Collections.singleton(roles));
            userRepository.save(user);
        }

        roleUser.add(roles);
        return new ResponseEntity<>("Register Successful", HttpStatus.OK);
    }

    @PostMapping("signin")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication.getName());
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(authentication.getName());
        ResponseCookie jwtCookie = cookie.createCookieWithJwt(token);
        ResponseCookie refreshTokenCookie = cookie.createCookieWithRefreshToken(refreshToken.getToken());
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        return ResponseEntity.ok()
                .headers(headers)
                .body(new LoginResponse(token, userDetails.getId(),
                        userDetails.getUsername(),userDetails.getFullname(),
                        userDetails.getPhone(), userDetails.getEmail(), userDetails.getAuthorities()));
    }

    @PostMapping("refreshtoken")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
        String requestRefreshToken = cookie.getRefreshTokenFromCookies(request);

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtGenerator.generateToken(user.getUsername());
                    ResponseCookie jwtCookie = cookie.createCookieWithJwt(token);
                    ResponseCookie refreshTokenCookie = cookie.createCookieWithRefreshToken(requestRefreshToken);
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());
                    headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
                    return ResponseEntity.ok()
                            .headers(headers)
                            .body(new MessageResponse("Token is refreshed successfully!"));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }


    @PostMapping("/signout")
    public ResponseEntity<?> logout() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principle.toString() != "anonymousUser") {
            refreshTokenService.deleteByUsername(((UserDetailsImp) principle).getUsername());
        }
        ResponseCookie jwtCookieCleared = cookie.clearJwtCookie();
        ResponseCookie jwtRefreshCookieCleared = cookie.clearRefreshTokenCookie();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, jwtCookieCleared.toString());
        headers.add(HttpHeaders.SET_COOKIE, jwtRefreshCookieCleared.toString());
        return ResponseEntity.ok()
                .headers(headers)
                .body(new MessageResponse("You've been signed out!"));
    }
}
