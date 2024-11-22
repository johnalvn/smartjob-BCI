package cl.bci.bciapi.mapper;

import cl.bci.bciapi.entity.AppUser;
import cl.bci.bciapi.mapper.dto.AppUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppUserMapper {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);
    AppUserDTO toAppUserDTO(AppUser appUser);
}

