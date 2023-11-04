package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.UserDto;
import com.alsab.boozycalc.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToDto(UserEntity user);
    UserEntity dtoToUser(UserDto user);
}
