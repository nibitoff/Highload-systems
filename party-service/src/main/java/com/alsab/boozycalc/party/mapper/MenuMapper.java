package com.alsab.boozycalc.party.mapper;

import com.alsab.boozycalc.party.dto.CocktailDto;
import com.alsab.boozycalc.party.dto.MenuDto;
import com.alsab.boozycalc.party.dto.PartyDto;
import com.alsab.boozycalc.party.entity.MenuEntity;
import com.alsab.boozycalc.party.entity.MenuId;
import com.alsab.boozycalc.party.entity.PartyEntity;
import com.alsab.boozycalc.party.feign.FeignCocktailServiceClient;
import feign.FeignException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {PartyMapper.class})
public abstract class MenuMapper {
    @Autowired
    FeignCocktailServiceClient feignCocktailServiceClient;

    @Mapping(target = "id", ignore = true)
    public abstract MenuEntity dtoToMenu(MenuDto menuDto);

    @Mapping(target = "party", ignore = true)
    @Mapping(target = "cocktail", ignore = true)
    public abstract MenuDto menuToDto(MenuEntity menu);

    @AfterMapping
    public void mapId(MenuDto dto, @MappingTarget MenuEntity menu){
        PartyMapper party_mapper = Mappers.getMapper(PartyMapper.class);
        PartyEntity party = party_mapper.dtoToParty(dto.getParty());

        Long cocktail = dto.getCocktail().getId();
        menu.setId(new MenuId(party, cocktail));
    }

    @AfterMapping
    public void mapId(MenuEntity menu, @MappingTarget MenuDto dto){
        PartyMapper party_mapper = Mappers.getMapper(PartyMapper.class);
        PartyDto party_dto = party_mapper.partyToDto(menu.getId().getParty());
        dto.setParty(party_dto);

        try {
            dto.setCocktail(feignCocktailServiceClient.findById(menu.getId().getCocktail()));
        } catch (FeignException e){
            dto.setCocktail(CocktailDto.builder().id(menu.getId().getCocktail()).build());
        }
    }
}
