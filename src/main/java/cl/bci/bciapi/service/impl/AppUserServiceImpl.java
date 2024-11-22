package cl.bci.bciapi.service.impl;

import cl.bci.bciapi.entity.AppUser;
import cl.bci.bciapi.repository.AppUserRepository;
import cl.bci.bciapi.service.AppUserService;
import cl.bci.bciapi.service.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class AppUserServiceImpl implements AppUserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AppUserRepository userRepository;
    private final TokenService tokenService;

    ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(AppUserServiceImpl.class);

    public AppUserServiceImpl(AppUserRepository userRepository, TokenService tokenService){
        this.tokenService = tokenService;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
    }

    @Override
    public AppUser save(AppUser appUser) throws JsonProcessingException {

        logger.debug(objectMapper.writeValueAsString(appUser));
        logger.info("Trying to register a new user");

        appUser.setId(UUID.randomUUID());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        LocalDateTime now = LocalDateTime.now();
        appUser.setCreated(now);
        appUser.setModified(now);
        appUser.setLastLogin(now);
        appUser.setToken(tokenService.generateToken(appUser));
        appUser.setActive(true);

        return userRepository.save(appUser);
    }



}
