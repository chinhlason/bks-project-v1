package com.bksproject.bksproject.Controller;

import com.bksproject.bksproject.Model.*;
import com.bksproject.bksproject.Repository.CourseRepository;
import com.bksproject.bksproject.Repository.PurchaseRepository;
import com.bksproject.bksproject.Repository.UserRepository;
import com.bksproject.bksproject.exception.System.CourseSerialNotFoundException;
import com.bksproject.bksproject.exception.System.UserNotFoundException;
import com.bksproject.bksproject.payload.request.PurchaseUpdateRequest;
import com.bksproject.bksproject.payload.response.MessagesResponse;
import com.bksproject.bksproject.payload.response.PurchaseResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "http://localhost:3000",maxAge = 3600,allowCredentials = "true")
@RestController
@RequestMapping("api/")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")

public class PurchaseController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @PostMapping("admin/purchase/create")
    public ResponseEntity<?> createPurchase(Authentication authentication,
                                          @RequestParam("username") String username, @RequestParam("serial") String serial)
                                            throws UserNotFoundException, CourseSerialNotFoundException {
        String exception = "";
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        Courses course = courseRepository.findCoursesBySerial(serial).orElseThrow(() -> new CourseSerialNotFoundException(exception));

        Purchase purchase = new Purchase(
                Instant.now(),
                user,
                course
        );
        purchaseRepository.save(purchase);
        return new ResponseEntity<>(new MessagesResponse("Purchase created success!"), OK);
    }

    @GetMapping("admin/get-all-purchase")
    public ResponseEntity<?> getAllPurchase() throws UserNotFoundException{
        List<Purchase> purchases = purchaseRepository.findAll();
        return ResponseEntity.ok().body(getPurchaseResponseFromList(purchases));
    }

    @GetMapping("admin/purchase")
    public ResponseEntity<?> getDetailPurchase(@Valid @RequestParam("id") Long id) throws UserNotFoundException{
        Purchase purchase = purchaseRepository.findPurchaseById(id);
        Users user = userRepository.findByUsername(purchase.getUserPurchase().getUsername())
                .orElseThrow(() -> new UserNotFoundException(purchase.getUserPurchase().getUsername()));
        Courses course = courseRepository.findCoursesById(purchase.getCoursePurchase().getId());
        PurchaseResponse purchaseResponse = new PurchaseResponse(
                purchase.getId(),
                purchase.getCreateAt(),
                purchase.getUpdateAt(),
                user.getUsername(),
                user.getFullname(),
                course.getSerial(),
                course.getName(),
                course.getId()
        );
        return ResponseEntity.ok().body(purchaseResponse);
    }

    @PutMapping("admin/purchase/update")
    public ResponseEntity<?> updatePurchase(@Valid @RequestParam("id") Long id,
                                            @RequestBody PurchaseUpdateRequest purchaseUpdateRequest) throws UserNotFoundException, CourseSerialNotFoundException{
        String exception = "";
        Users user = userRepository.findByUsername(purchaseUpdateRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException(purchaseUpdateRequest.getUsername()));
        Courses course = courseRepository.findCoursesBySerial(purchaseUpdateRequest.getSerial())
                .orElseThrow(() -> new CourseSerialNotFoundException(exception));
        Purchase purchase = purchaseRepository.findPurchaseById(id);
        purchase.setUpdateAt(Instant.now());
        purchase.setUserPurchase(user);
        purchase.setCoursePurchase(course);
        purchaseRepository.save(purchase);
        return ResponseEntity.ok().body(new MessagesResponse("Update purchase success!"));
    }

    public List<PurchaseResponse> getPurchaseResponseFromList(List<Purchase> purchases) throws UserNotFoundException{
        List<PurchaseResponse> purchaseResponses = new ArrayList<>();
        for(Purchase purchase : purchases){
            Users user = userRepository.findByUsername(purchase.getUserPurchase().getUsername())
                    .orElseThrow(() -> new UserNotFoundException(purchase.getUserPurchase().getUsername()));
            Courses course = courseRepository.findCoursesById(purchase.getCoursePurchase().getId());
            PurchaseResponse purchaseResponse = new PurchaseResponse(
                    purchase.getId(),
                    purchase.getCreateAt(),
                    purchase.getUpdateAt(),
                    user.getUsername(),
                    user.getFullname(),
                    course.getSerial(),
                    course.getName(),
                    course.getId()
            );
            purchaseResponses.add(purchaseResponse);
        }
        return purchaseResponses;
    }
}
