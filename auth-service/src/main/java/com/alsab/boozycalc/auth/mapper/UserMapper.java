package com.alsab.boozycalc.auth.mapper;

import com.alsab.boozycalc.auth.dto.UserDto;
import com.alsab.boozycalc.auth.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToDto(UserEntity user);
    UserEntity dtoToUser(UserDto user);
}
