package com.bksproject.bksproject.Controller;

import com.bksproject.bksproject.Model.*;
import com.bksproject.bksproject.Repository.*;
import com.bksproject.bksproject.exception.System.CourseSerialNotFoundException;
import com.bksproject.bksproject.exception.System.SerialExistException;
import com.bksproject.bksproject.exception.System.UserNotFoundException;
import com.bksproject.bksproject.payload.request.CourseRequest;
import com.bksproject.bksproject.payload.request.LessonRequest;
import com.bksproject.bksproject.payload.response.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000",maxAge = 3600,allowCredentials = "true")
@RestController
@RequestMapping("api/")
public class CourseController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserProcessRepository processRepository;

    @PostMapping("admin/course/create")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    ResponseEntity<?> createPost(@Valid @RequestBody CourseRequest courseRequest, Authentication authentication) throws UserNotFoundException , SerialExistException {
        String username = authentication.getName();
        String exception = "";
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        if(courseRequest.getName().isEmpty()){
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","Name can not be empty"));
        } else if (courseRequest.getAbtrac().isEmpty()) {
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","Abtrac can not be empty"));
        } else if (courseRequest.getAuthor().isEmpty()) {
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","Author can not be empty"));
        } else if (courseRequest.getImgUrl().isEmpty()) {
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "", "Img URL can not be empty"));
        } else if (courseRequest.getSerial().isEmpty()) {
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","Serial can not be empty"));
        } else if (courseRepository.existsBySerial(courseRequest.getSerial())) {
            throw new SerialExistException(exception);
        }
        Courses courses = new Courses(
                courseRequest.getName(),
                courseRequest.getAbtrac(),
                courseRequest.getAuthor(),
                courseRequest.getImgUrl(),
                courseRequest.getSerial(),
                courseRequest.getPrice()
                );
        courseRepository.save(courses);
        return ResponseEntity.ok().body(new MessageResponse("Create course success!"));
    }

    @PostMapping("admin/lesson/create")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createLesson(@Valid @RequestParam("serial") String serial,
                                          Authentication authentication, @RequestBody LessonRequest lessonRequest) throws UserNotFoundException, CourseSerialNotFoundException{
        String username = authentication.getName();
        String exception = "";
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        Courses course = courseRepository.findCoursesBySerial(serial).orElseThrow(() -> new CourseSerialNotFoundException(exception));

        Lessons lesson = new Lessons(
                lessonRequest.getTitle(),
                lessonRequest.getDescription(),
                course
        );

        Media media = new Media(
                lessonRequest.getMediaUrl(),
                lesson
        );

        lessonRepository.save(lesson);
        mediaRepository.save(media);
        return ResponseEntity.ok().body(new MessageResponse("Create lesson success!"));
    }

    @GetMapping("course")
    public ResponseEntity<?> getLessonByMember(@Valid @RequestParam("serial") String serial,
                                          Authentication authentication)
                                            throws UserNotFoundException, CourseSerialNotFoundException{
        String username = authentication.getName();
        String exception = "";
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        Courses course = courseRepository.findCoursesBySerial(serial).orElseThrow(() -> new CourseSerialNotFoundException(exception));

        Purchase purchase = purchaseRepository.findPurchaseByUserIdAndCourseId(user.getId(), course.getId());

        if(purchase == null){
            return ResponseEntity.ok().body(new CourseResponse(
                    course.getId(),
                    course.getName(),
                    course.getAbtrac(),
                    course.getAuthor(),
                    course.getImgUrl(),
                    course.getSerial(),
                    course.getPrice(),
                    false
            ));
        }
        return ResponseEntity.ok().body(new CourseResponse(
                course.getId(),
                course.getName(),
                course.getAbtrac(),
                course.getAuthor(),
                course.getImgUrl(),
                course.getSerial(),
                course.getPrice(),
                true
        ));
    }

    @GetMapping("general/course")
    public ResponseEntity<?> getLessonByGeneral(@Valid @RequestParam("serial") String serial) throws CourseSerialNotFoundException{
        String exception = "";

        Courses course = courseRepository.findCoursesBySerial(serial).orElseThrow(() -> new CourseSerialNotFoundException(exception));

        return ResponseEntity.ok().body(new CourseResponse(
                    course.getId(),
                    course.getName(),
                    course.getAbtrac(),
                    course.getAuthor(),
                    course.getImgUrl(),
                    course.getSerial(),
                    course.getPrice(),
                    false
        ));
    }

    @GetMapping("general/get-all-course")
    public List<Courses> getAllCourse() throws CourseSerialNotFoundException{

        List<Courses> courses = courseRepository.findAll();

        return courses;
    }

    @GetMapping("/get-own-course")
    public ResponseEntity<?> getAllCourse(Authentication authentication) throws UserNotFoundException{
        String username = authentication.getName();
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        List<Purchase> purchases = purchaseRepository.findPurchaseByUserId(user.getId());
        if(getOwnCourseFromPurchase(purchases).isEmpty()){
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","The user does not own any courses yet!"));
        }
        return ResponseEntity.ok().body(getOwnCourseFromPurchase(purchases));
    }

    @PutMapping("/course/update")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    ResponseEntity<?> updateCourse(@Valid @RequestParam("serial") String serial,
                                   @RequestBody CourseRequest courseRequest, Authentication authentication)
            throws UserNotFoundException, CourseSerialNotFoundException, SerialExistException{
        String username = authentication.getName();
        String exception = "";
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        Courses course = courseRepository.findCoursesBySerial(serial).orElseThrow(() -> new CourseSerialNotFoundException(exception));

        if(courseRequest.getName().isEmpty()){
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","Name can not be empty"));
        } else if (courseRequest.getAbtrac().isEmpty()) {
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","Abtrac can not be empty"));
        } else if (courseRequest.getAuthor().isEmpty()) {
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","Author can not be empty"));
        } else if (courseRequest.getImgUrl().isEmpty()) {
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "", "Img URL can not be empty"));
        } else if (courseRequest.getSerial().isEmpty()) {
            return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","Serial can not be empty"));
        } else if (courseRepository.existsBySerial(courseRequest.getSerial())) {
            throw new SerialExistException(exception);
        }
        course.setAbtrac(courseRequest.getAbtrac());
        course.setName(courseRequest.getName());
        course.setAuthor(courseRequest.getAuthor());
        course.setImgUrl(courseRequest.getImgUrl());
        course.setPrice(courseRequest.getPrice());
        course.setSerial(courseRequest.getSerial());
        courseRepository.save(course);

        return ResponseEntity.ok().body(new MessageResponse("Update course success!"));
    }

    @PutMapping("/lesson/update")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    ResponseEntity<?> updateLesson(@Valid @RequestParam("id") Long id,
                                   @RequestBody LessonRequest lessonRequest, Authentication authentication)
            throws UserNotFoundException, CourseSerialNotFoundException, SerialExistException{
        String username = authentication.getName();
        String exception = "";
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        Lessons lesson = lessonRepository.findLessonsById(id);
        Media media = mediaRepository.findMediaByLessonId(id);

        lesson.setDescription(lessonRequest.getDescription());
        lesson.setTitle(lessonRequest.getTitle());
        media.setVideoUrl(lessonRequest.getMediaUrl());

        lessonRepository.save(lesson);
        mediaRepository.save(media);

        return ResponseEntity.ok().body(new MessageResponse("Update lesson success!"));
    }

    @GetMapping("/course/get-all-lesson")
    ResponseEntity<?> getAllLessonInCourse(@RequestParam("id") Long id, Authentication authentication) throws UserNotFoundException, CourseSerialNotFoundException{
        List<Lessons> lessons = lessonRepository.findAllByCourseId(id);
        return ResponseEntity.ok().body(getLessonResponse(lessons));
    }

    @GetMapping("/lesson")
    ResponseEntity<?> getLessonDetail(@RequestParam("id") Long id, Authentication authentication){
        Lessons lesson = lessonRepository.findLessonsById(id);
        Media media = mediaRepository.findMediaByLessonId(id);
        return ResponseEntity.ok().body(
                new LessonResponse(
                        lesson.getId(),
                        lesson.getCreateAt(),
                        lesson.getTitle(),
                        lesson.getDescription(),
                        media.getVideoUrl()
                )
        );
    }

    @PostMapping ("/create-user-process")
    ResponseEntity<?> createMark(@RequestParam("id") Long lessonId, Authentication authentication) throws UserNotFoundException{
        String username = authentication.getName();
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Lessons lesson = lessonRepository.findLessonsById(lessonId);
        UserProcess _userProcess = processRepository.findUserProcessByUserIdAndLessonId(user.getId(), lessonId);
        if(_userProcess == null){
            UserProcess userProcess = new UserProcess(
                    lesson,
                    user
            );
            userProcess.setIsComplete();
            processRepository.save(userProcess);
            return ResponseEntity.ok().body(new MessageResponse("Create Mark/Unmark success!"));
        }
        return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","User process in this lesson already exist"));
    }

    @PutMapping("/change-user-process")
    ResponseEntity<?> changeMark(@RequestParam("id") Long lessonId, Authentication authentication) throws UserNotFoundException{
        String username = authentication.getName();
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        UserProcess userProcess = processRepository.findUserProcessByUserIdAndLessonId(user.getId(), lessonId);
        userProcess.setIsComplete();
        processRepository.save(userProcess);
        return ResponseEntity.ok().body(new MessageResponse("Change Mark/Unmark success!"));
    }

    @GetMapping("/get-user-process")
    ResponseEntity<?> getMark(@RequestParam("id") Long lessonId, Authentication authentication) throws UserNotFoundException{
        String username = authentication.getName();
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Lessons lesson = lessonRepository.findLessonsById(lessonId);
        UserProcess userProcess = processRepository.findUserProcessByUserIdAndLessonId(user.getId(), lessonId);
        if(userProcess != null) {
            return ResponseEntity.ok().body(
                    new UserProcessResponse(
                            userProcess.getId(),
                            lesson.getId(),
                            lesson.getTitle(),
                            userProcess.getIsComplete()
                    )
            );
        }
        return ResponseEntity.badRequest().body(new HttpResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"","User process didn't exist"));
    }

    public List<Courses> getOwnCourseFromPurchase(List<Purchase> purchases) {
        List<Courses> courses = new ArrayList<>();

        for (Purchase purchase : purchases) {
            Courses course = courseRepository.findCoursesById(purchase.getCoursePurchase().getId());
            if (course != null) {
                courses.add(course);
            }
        }
        return courses;
    }

    public List<LessonResponse> getLessonResponse(List<Lessons> lessons) {
        List<LessonResponse> lessonResponses = new ArrayList<>();

        for (Lessons lesson : lessons) {
            Media media = mediaRepository.findMediaByLessonId(lesson.getId());
            LessonResponse lessonResponse = new LessonResponse(
                    lesson.getId(),
                    lesson.getCreateAt(),
                    lesson.getTitle(),
                    lesson.getDescription(),
                    media.getVideoUrl()
            );
            lessonResponses.add(lessonResponse);
        }
        return lessonResponses;
    }
}
