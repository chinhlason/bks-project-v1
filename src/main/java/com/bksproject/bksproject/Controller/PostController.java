package com.bksproject.bksproject.Controller;

import com.bksproject.bksproject.DTO.PostDTO;
import com.bksproject.bksproject.Model.Posts;
import com.bksproject.bksproject.Model.Users;
import com.bksproject.bksproject.Repository.PostRepository;
import com.bksproject.bksproject.Repository.UserRepository;
import com.bksproject.bksproject.Service.ModelMapperService;
import com.bksproject.bksproject.Service.UserDetailsImp;
import com.bksproject.bksproject.advice.CustomMapper;
import com.bksproject.bksproject.exception.System.PostNotFoundException;
import com.bksproject.bksproject.exception.System.UserNotFoundException;
import com.bksproject.bksproject.payload.request.UpdatePostRequest;
import com.bksproject.bksproject.payload.request.UpdateUserRequest;
import com.bksproject.bksproject.payload.response.HttpResponse;

import com.bksproject.bksproject.payload.response.MessageResponse;
import com.bksproject.bksproject.payload.response.PostResponse;
import com.bksproject.bksproject.payload.response.UpdatePostResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.security.PermitAll;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000",maxAge = 3600,allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    ModelMapperService modelMapperService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Posts posts;

    @PostMapping("/post/create")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    ResponseEntity<?> create(@Valid @RequestBody PostDTO postDTO, Authentication authentication) throws UserNotFoundException{
        String username = authentication.getName();
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        if(postDTO.getCategory().isEmpty()){
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","Category can not be empty"));
        } else if (postDTO.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","Title can not be empty"));
        } else if (postDTO.getContent().isEmpty()) {
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","Content can not be empty"));
        }
        Posts post = new Posts(postDTO.getCategory(), postDTO.getTitle(), postDTO.getContent() , user);
        postRepository.save(post);
        return ResponseEntity.ok().body(new MessageResponse("Create post success!"));
    }

    @GetMapping("/general/post/get-all-post")
    public List<Posts> getAll(){
        List<Posts> list = postRepository.findAll();
        return list;
    }

    @GetMapping("/general/post/current-post")
    public ResponseEntity<?> getCurrentPost(@RequestParam("id") Long id) throws PostNotFoundException {
        String exception = "";
        Posts postCurrent = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(exception));
        return ResponseEntity.ok()
                .body(
                        new PostResponse(
                                postCurrent.getId(),
                                postCurrent.getCreateAt(),
                                postCurrent.getCategory(),
                                postCurrent.getTitle(),
                                postCurrent.getContent(),
                                postCurrent.getUser_post()
                        )
                );
    }

    @PutMapping("/post/update")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updatePost(@RequestParam("id") Long id, @Valid @RequestBody UpdatePostRequest infoUpdate,
                                        Authentication authentication) throws PostNotFoundException, UserNotFoundException{
        String exception = "";
        String SUCCESS_MESSAGE = "update success";
        String username = authentication.getName();
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Posts updatePost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(exception));
        updatePost.setCategory(infoUpdate.getCategory());
        updatePost.setTitle(infoUpdate.getTitle());
        updatePost.setContent(infoUpdate.getContent());
        postRepository.save(updatePost);
        return ResponseEntity.ok()
                .body(
                        new UpdatePostResponse(
                                SUCCESS_MESSAGE,
                                user.getUsername()
                        )
                );
    }

    @DeleteMapping("/post/delete")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deletePost(@RequestParam("id") Long id) throws PostNotFoundException{
        String exception = "";
        Posts deletePost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(exception));
        postRepository.deleteById(id);
        return new ResponseEntity(new MessageResponse("Delete succesfully"),HttpStatus.OK);
    }

}

