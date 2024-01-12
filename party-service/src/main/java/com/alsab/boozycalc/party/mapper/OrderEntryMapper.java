package com.alsab.boozycalc.party.mapper;

import com.alsab.boozycalc.party.dto.CocktailDto;
import com.alsab.boozycalc.party.dto.OrderDto;
import com.alsab.boozycalc.party.dto.OrderEntryDto;
import com.alsab.boozycalc.party.entity.OrderEntity;
import com.alsab.boozycalc.party.entity.OrderEntryEntity;
import com.alsab.boozycalc.party.entity.OrderEntryId;
import com.alsab.boozycalc.party.feign.FeignCocktailServiceClient;
import com.alsab.boozycalc.party.service.CocktailFeignService;
import feign.FeignException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {CocktailFeignService.class})
public abstract class OrderEntryMapper {
    @Autowired
    FeignCocktailServiceClient feignCocktailServiceClient;
    @Autowired
    OrderMapper orderMapper;

    @Mapping(target = "id", ignore = true)
    public abstract OrderEntryEntity dtoToOrderEntry(OrderEntryDto dto);

    @Mapping(target = "cocktail", ignore = true)
    @Mapping(target = "order", ignore = true)
    public abstract  OrderEntryDto orderEntryToDto(OrderEntryEntity dto);

    @AfterMapping
    public void mapId(OrderEntryDto dto, @MappingTarget OrderEntryEntity orderEntry){
        OrderMapper order_mapper = Mappers.getMapper(OrderMapper.class);
        OrderEntity order = order_mapper.dtoToOrder(dto.getOrder());

        Long cocktail = dto.getCocktail().getId();
        orderEntry.setId(new OrderEntryId(order, cocktail));
    }

    @AfterMapping
    public void mapId(OrderEntryEntity orderEntry, @MappingTarget OrderEntryDto dto){
//        OrderMapper order_mapper = Mappers.getMapper(OrderMapper.class);
        OrderDto order_dto = orderMapper.orderToDto(orderEntry.getId().getOrder());
        dto.setOrder(order_dto);

        try {
            dto.setCocktail(feignCocktailServiceClient.findById(orderEntry.getId().getCocktail()));
        } catch (FeignException e){
            dto.setCocktail(CocktailDto.builder().id(orderEntry.getId().getCocktail()).build());
        }
    }
}
