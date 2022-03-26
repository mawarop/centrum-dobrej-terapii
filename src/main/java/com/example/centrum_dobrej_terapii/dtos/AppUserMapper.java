package com.example.centrum_dobrej_terapii.dtos;

import com.example.centrum_dobrej_terapii.entities.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppUserMapper {

    List<AppUserResponse> appUsersToAppUserResponses(List<AppUser> appUser);

    void updateAppUserFromAppUserRequest(AppUserRequest appUserRequest, @MappingTarget AppUser appUser);

}
