package com.alsab.boozycalc.cocktail.mapper;


import com.alsab.boozycalc.cocktail.dto.UserDto;
import com.alsab.boozycalc.cocktail.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToDto(UserEntity user);
    UserEntity dtoToUser(UserDto user);
}
