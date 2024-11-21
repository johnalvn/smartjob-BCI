package cl.bci.bciapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Phone {

    @Id
    private UUID id = UUID.randomUUID();

    private String number;
    private String cityCode;
    private String countryCode;

}
