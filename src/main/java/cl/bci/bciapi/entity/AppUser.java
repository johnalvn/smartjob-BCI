package cl.bci.bciapi.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    private UUID id;

    private String name;

    @Email(message = "Formato de correo inválido")
    @Column(unique = true)
    private String email;

    //@Pattern(regexp = "${password.regex}", message = "Formato de contraseña inválido")
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Phone> phones;

    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;

}
