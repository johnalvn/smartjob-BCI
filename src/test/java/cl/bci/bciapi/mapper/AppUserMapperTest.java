package cl.bci.bciapi.mapper;

import cl.bci.bciapi.entity.AppUser;
import cl.bci.bciapi.entity.Phone;
import cl.bci.bciapi.mapper.AppUserMapper;
import cl.bci.bciapi.mapper.dto.AppUserDTO;
import cl.bci.bciapi.mapper.dto.AppUserFullDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AppUserMapperTest {

    private final AppUserMapper mapper = AppUserMapper.INSTANCE;

    @Test
    public void testToAppUserDTO() {
        // Arrange
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        AppUser appUser = new AppUser();
        appUser.setId(userId);
        appUser.setCreated(now);
        appUser.setModified(now);
        appUser.setLastLogin(now);
        appUser.setToken("some-token");
        appUser.setActive(true);

        // Act
        AppUserDTO appUserDTO = mapper.toAppUserDTO(appUser);

        // Assert
        assertNotNull(appUserDTO, "Mapped AppUserDTO should not be null");
        assertEquals(userId, appUserDTO.id(), "ID should be mapped correctly");
        assertEquals(now, appUserDTO.created(), "Created date should be mapped correctly");
        assertEquals(now, appUserDTO.modified(), "Modified date should be mapped correctly");
        assertEquals(now, appUserDTO.lastLogin(), "Last login date should be mapped correctly");
        assertEquals("some-token", appUserDTO.token(), "Token should be mapped correctly");
        //assertTrue(appUserDTO.isActive(), "Active status should be mapped correctly");
    }

    @Test
    public void testToAppUserFullDTO() {
        // Arrange
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Phone phone1 = new Phone(UUID.randomUUID(), "1234567890", "1", "57");
        Phone phone2 = new Phone(UUID.randomUUID(), "0987654321", "2", "58");

        AppUser appUser = new AppUser();
        appUser.setId(userId);
        appUser.setName("John Doe");
        appUser.setEmail("john.doe@example.com");
        appUser.setPassword("encoded-password");
        appUser.setPhones(Arrays.asList(phone1, phone2));
        appUser.setCreated(now);
        appUser.setModified(now);
        appUser.setLastLogin(now);
        appUser.setToken("some-token");
        appUser.setActive(true);

        // Act
        AppUserFullDTO appUserFullDTO = mapper.toAppUserFullDTO(appUser);

        // Assert
        assertNotNull(appUserFullDTO, "Mapped AppUserFullDTO should not be null");
        assertEquals(userId, appUserFullDTO.id(), "ID should be mapped correctly");
        assertEquals("John Doe", appUserFullDTO.name(), "Name should be mapped correctly");
        assertEquals("john.doe@example.com", appUserFullDTO.email(), "Email should be mapped correctly");
        assertEquals(now, appUserFullDTO.created(), "Created date should be mapped correctly");
        assertEquals(now, appUserFullDTO.modified(), "Modified date should be mapped correctly");
        assertEquals(now, appUserFullDTO.lastLogin(), "Last login date should be mapped correctly");
        assertEquals("some-token", appUserFullDTO.token(), "Token should be mapped correctly");
        //assertTrue(appUserFullDTO.isActive(), "Active status should be mapped correctly");
        assertNotNull(appUserFullDTO.phones(), "Phones list should not be null");
        assertEquals(2, appUserFullDTO.phones().size(), "Phones list size should be 2");

        // Verify phone mapping
        Phone mappedPhone1 = appUserFullDTO.phones().get(0);
        Phone mappedPhone2 = appUserFullDTO.phones().get(1);

        assertEquals(phone1.getId(), mappedPhone1.getId(), "Phone 1 ID should be mapped correctly");
        assertEquals(phone1.getNumber(), mappedPhone1.getNumber(), "Phone 1 number should be mapped correctly");
        assertEquals(phone1.getCityCode(), mappedPhone1.getCityCode(), "Phone 1 city code should be mapped correctly");
        assertEquals(phone1.getCountryCode(), mappedPhone1.getCountryCode(), "Phone 1 country code should be mapped correctly");

        assertEquals(phone2.getId(), mappedPhone2.getId(), "Phone 2 ID should be mapped correctly");
        assertEquals(phone2.getNumber(), mappedPhone2.getNumber(), "Phone 2 number should be mapped correctly");
        assertEquals(phone2.getCityCode(), mappedPhone2.getCityCode(), "Phone 2 city code should be mapped correctly");
        assertEquals(phone2.getCountryCode(), mappedPhone2.getCountryCode(), "Phone 2 country code should be mapped correctly");
    }

    @Test
    public void testToAppUserFullDTOList() {
        // Arrange
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        AppUser user1 = new AppUser();
        user1.setId(userId1);
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password1");
        user1.setCreated(now);
        user1.setModified(now);
        user1.setLastLogin(now);
        user1.setToken("token1");
        user1.setActive(true);

        AppUser user2 = new AppUser();
        user2.setId(userId2);
        user2.setName("Jane Smith");
        user2.setEmail("jane.smith@example.com");
        user2.setPassword("password2");
        user2.setCreated(now);
        user2.setModified(now);
        user2.setLastLogin(now);
        user2.setToken("token2");
        user2.setActive(false);

        List<AppUser> appUsers = Arrays.asList(user1, user2);

        // Act
        List<AppUserFullDTO> appUserFullDTOList = mapper.toAppUserFullDTOList(appUsers);

        // Assert
        assertNotNull(appUserFullDTOList, "Mapped list should not be null");
        assertEquals(2, appUserFullDTOList.size(), "Mapped list size should be 2");

        // Verify first user mapping
        AppUserFullDTO dto1 = appUserFullDTOList.get(0);
        assertEquals(userId1, dto1.id(), "User 1 ID should be mapped correctly");
        assertEquals("John Doe", dto1.name(), "User 1 name should be mapped correctly");
        assertEquals("john.doe@example.com", dto1.email(), "User 1 email should be mapped correctly");
//        assertEquals("password1", dto1.getPassword(), "User 1 password should be mapped correctly");
        assertEquals(now, dto1.created(), "User 1 created date should be mapped correctly");
        assertEquals(now, dto1.modified(), "User 1 modified date should be mapped correctly");
        assertEquals(now, dto1.lastLogin(), "User 1 last login date should be mapped correctly");
        assertEquals("token1", dto1.token(), "User 1 token should be mapped correctly");
//        assertTrue(dto1.isActive(), "User 1 active status should be mapped correctly");

        // Verify second user mapping
        AppUserFullDTO dto2 = appUserFullDTOList.get(1);
        assertEquals(userId2, dto2.id(), "User 2 ID should be mapped correctly");
        assertEquals("Jane Smith", dto2.name(), "User 2 name should be mapped correctly");
        assertEquals("jane.smith@example.com", dto2.email(), "User 2 email should be mapped correctly");
//        assertEquals("password2", dto2.password(), "User 2 password should be mapped correctly");
        assertEquals(now, dto2.created(), "User 2 created date should be mapped correctly");
        assertEquals(now, dto2.modified(), "User 2 modified date should be mapped correctly");
        assertEquals(now, dto2.lastLogin(), "User 2 last login date should be mapped correctly");
        assertEquals("token2", dto2.token(), "User 2 token should be mapped correctly");
        assertFalse(dto2.isActive(), "User 2 active status should be mapped correctly");
    }
}
