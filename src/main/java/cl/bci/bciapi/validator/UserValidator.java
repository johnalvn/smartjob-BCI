package cl.bci.bciapi.validator;

import cl.bci.bciapi.entity.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserValidator {

    @Value("${user.validator.email.regexp}")
    public String emailRegex;

    @Value("${user.validator.password.regexp}")
    public String passwdRegex;

    @Value("${user.validator.name.length}")
    public Integer nameLength;

    public List<String> validate(AppUser user) {
        List<String> errors = new ArrayList<>();
        Objects.requireNonNull(user);

        if (!validateEmail(user.getEmail())) {
            errors.add("Email no valido, asegurate que cumple la estructura : name@dominio.cl ");
        }

        if (!validatePassword(user.getPassword())) {
            errors.add("El password no cumple las politicas de seguridad");
        }

        if (!validateName(user.getName())) {
            errors.add("El campo 'name' excede la longitud requerida");
        }

        return errors;
    }

    boolean validateEmail(String email) {
        return email != null && email.matches(emailRegex);
    }

    boolean validatePassword(String password) {
        return password != null && password.matches(passwdRegex);
    }

    boolean validateName(String name) {
        return name != null && name.length() <= nameLength;
    }
}
