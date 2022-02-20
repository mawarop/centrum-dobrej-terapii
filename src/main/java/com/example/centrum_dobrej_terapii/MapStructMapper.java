package com.example.centrum_dobrej_terapii;

import com.example.centrum_dobrej_terapii.dtos.AppUserResponse;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    List<AppUserResponse> appUsersToAppUserResponses(List<AppUser> appUser);
}
