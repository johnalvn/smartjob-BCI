package cl.bci.bciapi.controller;

import cl.bci.bciapi.entity.AppUser;
import cl.bci.bciapi.mapper.AppUserMapper;
import cl.bci.bciapi.mapper.dto.AppUserDTO;
import cl.bci.bciapi.repository.AppUserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
public class AppUserController {

    private final AppUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);


    public AppUserController(AppUserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registerUser( @RequestBody AppUser user) throws JsonProcessingException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>(Collections.singletonMap("mensaje", "El correo ya registrado"), HttpStatus.BAD_REQUEST);
        }
        logger.debug(objectMapper.writeValueAsString(user));
        logger.info("Trying to register a new user");

        user.setId(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        LocalDateTime now = LocalDateTime.now();
        user.setCreated(now);
        user.setModified(now);
        user.setLastLogin(now);
        user.setToken(generateToken(user));
        user.setActive(true);

        userRepository.save(user);

        return new ResponseEntity<>(AppUserMapper.INSTANCE.toAppUserDTO(user), HttpStatus.CREATED);
    }

    private String generateToken(AppUser user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setId(user.getId().toString())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
}