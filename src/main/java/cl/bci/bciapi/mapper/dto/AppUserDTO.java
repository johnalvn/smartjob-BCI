package cl.bci.bciapi.mapper.dto;

import cl.bci.bciapi.entity.Phone;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AppUserDTO
        (
            UUID id,
             LocalDateTime created,
             LocalDateTime modified,
             LocalDateTime lastLogin,
             String token,
             boolean isActive
        ) {
}
