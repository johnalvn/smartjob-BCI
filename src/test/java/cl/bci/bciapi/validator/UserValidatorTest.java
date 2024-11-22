package cl.bci.bciapi.validator;


import cl.bci.bciapi.entity.AppUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {

    private UserValidator userValidator;

    @BeforeEach
    public void setUp() {
        userValidator = new UserValidator();
        // Manually setting the properties
        userValidator.emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        userValidator.passwdRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        userValidator.nameLength = 100;
    }

    @Test
    public void testValidUser() {
        AppUser user = new AppUser();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("Password1!");

        List<String> errors = userValidator.validate(user);

        assertTrue(errors.isEmpty(), "Expected no validation errors for a valid user");
    }

    @Test
    public void testInvalidEmail() {
        AppUser user = new AppUser();
        user.setName("Jane Doe");
        user.setEmail("jane.doeexample.com"); // Missing '@'
        user.setPassword("Password1!");

        List<String> errors = userValidator.validate(user);

        assertFalse(errors.isEmpty(), "Expected validation errors due to invalid email");
        assertTrue(errors.contains("Email no valido, asegurate que cumple la estructura : name@dominio.cl "));
    }

    @Test
    public void testInvalidPassword() {
        AppUser user = new AppUser();
        user.setName("John Smith");
        user.setEmail("john.smith@example.com");
        user.setPassword("password");

        List<String> errors = userValidator.validate(user);

        assertFalse(errors.isEmpty(), "Expected validation errors due to invalid password");
        assertTrue(errors.contains("El password no cumple las politicas de seguridad"));
    }

    @Test
    public void testNameExceedsMaxLength() {
        AppUser user = new AppUser();
        user.setName("A".repeat(101));
        user.setEmail("long.name@example.com");
        user.setPassword("Password1!");

        List<String> errors = userValidator.validate(user);

        assertFalse(errors.isEmpty(), "Expected validation errors due to name length exceeding maximum");
        assertTrue(errors.contains("El campo 'name' excede la longitud requerida"));
    }

    @Test
    public void testNullFields() {
        AppUser user = new AppUser();
        user.setName(null);
        user.setEmail(null);
        user.setPassword(null);

        List<String> errors = userValidator.validate(user);

        assertFalse(errors.isEmpty(), "Expected validation errors due to null fields");
        assertEquals(3, errors.size(), "Expected three validation errors");
    }

    @Test
    public void testNullUser() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userValidator.validate(null);
        });

        assertNotNull(exception, "Expected NullPointerException when user is null");
    }

    @Test
    public void testAllInvalidFields() {
        AppUser user = new AppUser();
        user.setName("A".repeat(101));
        user.setEmail("invalid-email");
        user.setPassword("pass");

        List<String> errors = userValidator.validate(user);

        assertEquals(3, errors.size(), "Expected three validation errors");
        assertTrue(errors.contains("Email no valido, asegurate que cumple la estructura : name@dominio.cl "));
        assertTrue(errors.contains("El password no cumple las politicas de seguridad"));
        assertTrue(errors.contains("El campo 'name' excede la longitud requerida"));
    }

    @Test
    public void testNameAtMaxLength() {
        AppUser user = new AppUser();
        user.setName("A".repeat(100));
        user.setEmail("max.length@example.com");
        user.setPassword("Password1!");

        List<String> errors = userValidator.validate(user);

        assertTrue(errors.isEmpty(), "Expected no validation errors for name at maximum length");
    }
}
