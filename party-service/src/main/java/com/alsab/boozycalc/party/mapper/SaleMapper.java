package com.alsab.boozycalc.party.mapper;

import com.alsab.boozycalc.party.dto.CocktailTypeDto;
import com.alsab.boozycalc.party.dto.PartyDto;
import com.alsab.boozycalc.party.dto.SaleDto;
import com.alsab.boozycalc.party.entity.PartyEntity;
import com.alsab.boozycalc.party.entity.SaleEntity;
import com.alsab.boozycalc.party.entity.SaleId;
import com.alsab.boozycalc.party.feign.FeignCocktailServiceClient;
import feign.FeignException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {PartyMapper.class})
public abstract class SaleMapper {
    @Autowired
    FeignCocktailServiceClient feignCocktailServiceClient;

    @Mapping(target = "id", ignore = true)
    public abstract SaleEntity dtoToSale(SaleDto saleDto);

    @Mapping(target = "party", ignore = true)
    @Mapping(target = "cocktailType", ignore = true)
    public abstract SaleDto saleToDto(SaleEntity saleEntity);

    @AfterMapping
    public void mapId(SaleDto dto, @MappingTarget SaleEntity entity){
        PartyMapper party_mapper = Mappers.getMapper(PartyMapper.class);
        PartyEntity party = party_mapper.dtoToParty(dto.getParty());

        Long type = dto.getCocktailType().getId();
        entity.setId(new SaleId(party, type));
    }

    @AfterMapping
    public void mapId(SaleEntity entity, @MappingTarget SaleDto dto){
        PartyMapper party_mapper = Mappers.getMapper(PartyMapper.class);
        PartyDto party_dto = party_mapper.partyToDto(entity.getId().getParty());
        dto.setParty(party_dto);

        try {
            dto.setCocktailType(feignCocktailServiceClient.cocktailTypeFindById(entity.getId().getCocktailType()));
        } catch (FeignException e){
            dto.setCocktailType(CocktailTypeDto.builder().id(entity.getId().getCocktailType()).build());
        }
    }
}
