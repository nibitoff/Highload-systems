package com.alsab.boozycalc.party.mapper;

import com.alsab.boozycalc.party.dto.PartyDto;
import com.alsab.boozycalc.party.dto.ProductDto;
import com.alsab.boozycalc.party.dto.PurchaseDto;
import com.alsab.boozycalc.party.entity.PartyEntity;
import com.alsab.boozycalc.party.entity.PurchaseEntity;
import com.alsab.boozycalc.party.entity.PurchaseId;
import com.alsab.boozycalc.party.service.ProductFeignService;
import feign.Feign;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {PartyMapper.class, ProductFeignService.class})
public abstract class PurchaseMapper {
    @Autowired
    ProductFeignService productFeignService;

    @Mapping(target = "id", ignore = true)
    public abstract PurchaseEntity dtoToPurchase(PurchaseDto dto);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "party", ignore = true)
    public abstract PurchaseDto purchaseToDto(PurchaseEntity dto);

    @AfterMapping
    public void mapId(PurchaseDto dto, @MappingTarget PurchaseEntity purchase){
//        ProductMapper prod_mapper = Mappers.getMapper(ProductMapper.class);
//        ProductEntity product = prod_mapper.dtoToProduct(dto.getProduct());

        PartyMapper party_mapper = Mappers.getMapper(PartyMapper.class);
        PartyEntity party = party_mapper.dtoToParty(dto.getParty());

        purchase.setId(new PurchaseId(dto.getProduct().getId(), party));

    }

    @AfterMapping
    public void mapId(PurchaseEntity purchase, @MappingTarget PurchaseDto dto){
//        ProductMapper prod_mapper = Mappers.getMapper(ProductMapper.class);
//        ProductDto prod_dto = prod_mapper.productToDto(purchase.getId().getProduct());

        PartyMapper party_mapper = Mappers.getMapper(PartyMapper.class);
        PartyDto party_dto = party_mapper.partyToDto(purchase.getId().getParty());

        dto.setParty(party_dto);
        dto.setProduct(productFeignService.findById(purchase.getId().getProduct()));
    }


}
