package cl.bci.bciapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    @Id
    private UUID id = UUID.randomUUID();

    private String number;
    private String cityCode;
    private String countryCode;

}
