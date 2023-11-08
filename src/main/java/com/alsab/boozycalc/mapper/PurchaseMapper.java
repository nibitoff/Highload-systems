package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.PartyDto;
import com.alsab.boozycalc.dto.ProductDto;
import com.alsab.boozycalc.dto.PurchaseDto;
import com.alsab.boozycalc.entity.PartyEntity;
import com.alsab.boozycalc.entity.ProductEntity;
import com.alsab.boozycalc.entity.PurchaseEntity;
import com.alsab.boozycalc.entity.PurchaseId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, PartyMapper.class})
public interface PurchaseMapper {
    @Mapping(target = "id", ignore = true)
    PurchaseEntity dtoToPurchase(PurchaseDto dto);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "party", ignore = true)
    PurchaseDto purchaseToDto(PurchaseEntity dto);

    @AfterMapping
    default void mapId(PurchaseDto dto, @MappingTarget PurchaseEntity purchase){
        ProductMapper prod_mapper = Mappers.getMapper(ProductMapper.class);
        PartyMapper party_mapper = Mappers.getMapper(PartyMapper.class);

        ProductEntity product = prod_mapper.dtoToProduct(dto.getProduct());
        PartyEntity party = party_mapper.dtoToParty(dto.getParty());

        purchase.setId(new PurchaseId(product, party));

    }

    @AfterMapping
    default void mapId(PurchaseEntity purchase, @MappingTarget PurchaseDto dto){
        ProductMapper prod_mapper = Mappers.getMapper(ProductMapper.class);
        PartyMapper party_mapper = Mappers.getMapper(PartyMapper.class);

        ProductDto prod_dto = prod_mapper.productToDto(purchase.getId().getProduct());
        PartyDto party_dto = party_mapper.partyToDto(purchase.getId().getParty());

        dto.setParty(party_dto);
        dto.setProduct(prod_dto);

    }


}
