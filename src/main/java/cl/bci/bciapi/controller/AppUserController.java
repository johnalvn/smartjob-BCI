package cl.bci.bciapi.controller;

import cl.bci.bciapi.entity.AppUser;
import cl.bci.bciapi.mapper.AppUserMapper;
import cl.bci.bciapi.repository.AppUserRepository;
import cl.bci.bciapi.service.AppUserService;

import cl.bci.bciapi.validator.UserValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class AppUserController {

    private final AppUserRepository userRepository;
    private final AppUserService appUserService;
    private final UserValidator userValidator;
    ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);


    public AppUserController(AppUserRepository userRepository, AppUserService appUserService, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.appUserService = appUserService;
        this.userValidator = userValidator;
    }

    @PostMapping("/users")
    public ResponseEntity<?> registerUser( @RequestBody AppUser user) throws JsonProcessingException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>(Collections.singletonMap("mensaje", "El correo ya registrado"), HttpStatus.BAD_REQUEST);
        }

        List<String> error = userValidator.validate(user);
        if(!error.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("mensaje", String.join(",", error)), HttpStatus.BAD_REQUEST);
        }

        AppUser  savedUser = appUserService.save(user);
        return new ResponseEntity<>(AppUserMapper.INSTANCE.toAppUserDTO(savedUser), HttpStatus.CREATED);
    }


}