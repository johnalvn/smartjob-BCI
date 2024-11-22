package cl.bci.bciapi.service;

import cl.bci.bciapi.entity.AppUser;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AppUserService {

    public AppUser save(cl.bci.bciapi.entity.AppUser appUser) throws JsonProcessingException;
}
