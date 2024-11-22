package cl.bci.bciapi.service.impl;

import cl.bci.bciapi.entity.AppUser;
import cl.bci.bciapi.repository.AppUserRepository;
import cl.bci.bciapi.service.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceImplTest {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AppUserServiceImpl appUserService;

    @Captor
    ArgumentCaptor<AppUser> userCaptor;

    @Test
    public void testSaveAppUser() throws JsonProcessingException {
        // Arrange
        AppUser appUser = new AppUser();
        appUser.setName("John Doe");
        appUser.setEmail("johndoe@example.com");
        appUser.setPassword("password123");
        appUser.setPhones(new ArrayList<>());

        when(tokenService.generateToken(any(AppUser.class))).thenReturn("mocked-token");
        when(userRepository.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        AppUser savedUser = appUserService.save(appUser);

        // Assert
        assertNotNull(savedUser.getId(), "User ID should not be null");
        assertNotNull(savedUser.getCreated(), "Created date should not be null");
        assertNotNull(savedUser.getModified(), "Modified date should not be null");
        assertNotNull(savedUser.getLastLogin(), "Last login date should not be null");
        assertEquals("mocked-token", savedUser.getToken(), "Token should match the mocked token");
        assertTrue(savedUser.isActive(), "User should be active");
        assertNotEquals("password123", savedUser.getPassword(), "Password should be encoded");

        // Verify that the password is encoded correctly
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches("password123", savedUser.getPassword()), "Password should be encoded with BCrypt");

        // Verify that userRepository.save was called with the correct user
        verify(userRepository).save(userCaptor.capture());
        AppUser capturedUser = userCaptor.getValue();
        assertEquals(savedUser, capturedUser, "Saved user should match the user passed to repository");
    }
}
