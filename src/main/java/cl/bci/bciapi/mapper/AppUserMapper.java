package cl.bci.bciapi.mapper;

import cl.bci.bciapi.entity.AppUser;
import cl.bci.bciapi.mapper.dto.AppUserDTO;
import cl.bci.bciapi.mapper.dto.AppUserFullDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AppUserMapper {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);
    AppUserDTO toAppUserDTO(AppUser appUser);
    AppUserFullDTO toAppUserFullDTO(AppUser appUser);
    List<AppUserFullDTO> toAppUserFullDTOList(List<AppUser> appUsers);
}

