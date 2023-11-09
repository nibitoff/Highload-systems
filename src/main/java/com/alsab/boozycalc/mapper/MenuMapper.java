package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.CocktailDto;
import com.alsab.boozycalc.dto.MenuDto;
import com.alsab.boozycalc.dto.PartyDto;
import com.alsab.boozycalc.entity.CocktailEntity;
import com.alsab.boozycalc.entity.MenuEntity;
import com.alsab.boozycalc.entity.MenuId;
import com.alsab.boozycalc.entity.PartyEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {PartyMapper.class, CocktailMapper.class})
public interface MenuMapper {
    @Mapping(target = "id", ignore = true)
    MenuEntity dtoToMenu(MenuDto menuDto);

    @Mapping(target = "party", ignore = true)
    @Mapping(target = "cocktail", ignore = true)
    MenuDto menuToDto(MenuEntity menu);

    @AfterMapping
    default void mapId(MenuDto dto, @MappingTarget MenuEntity menu){
        PartyMapper party_mapper = Mappers.getMapper(PartyMapper.class);
        CocktailMapper cock_mapper = Mappers.getMapper(CocktailMapper.class);

        PartyEntity party = party_mapper.dtoToParty(dto.getParty());
        CocktailEntity cocktail = cock_mapper.dtoToCocktail(dto.getCocktail());

        menu.setId(new MenuId(party, cocktail));
    }

    @AfterMapping
    default void mapId(MenuEntity menu, @MappingTarget MenuDto dto){
        CocktailMapper cock_mapper = Mappers.getMapper(CocktailMapper.class);
        PartyMapper party_mapper = Mappers.getMapper(PartyMapper.class);

        CocktailDto cock_dto = cock_mapper.cocktailToDto(menu.getId().getCocktail());
        PartyDto party_dto = party_mapper.partyToDto(menu.getId().getParty());

        dto.setCocktail(cock_dto);
        dto.setParty(party_dto);
    }
}
