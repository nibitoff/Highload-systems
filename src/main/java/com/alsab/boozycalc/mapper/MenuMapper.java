package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.MenuDto;
import com.alsab.boozycalc.entity.MenuEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    MenuDto menuToDto(MenuEntity menu);
    MenuEntity dtoToMenu(MenuDto menuDto);
}
