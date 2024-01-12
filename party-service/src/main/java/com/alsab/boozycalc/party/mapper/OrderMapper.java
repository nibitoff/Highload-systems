package com.alsab.boozycalc.party.mapper;

import com.alsab.boozycalc.party.dto.OrderDto;
import com.alsab.boozycalc.party.dto.UserDto;
import com.alsab.boozycalc.party.entity.OrderEntity;
import com.alsab.boozycalc.party.service.AuthFeignService;
import com.alsab.boozycalc.party.service.CocktailFeignService;
import feign.FeignException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {AuthFeignService.class})
public abstract class OrderMapper {
    @Autowired
    AuthFeignService authFeignService;

    @Mapping(target = "person", ignore = true)
    public abstract OrderDto orderToDto(OrderEntity order);

    @Mapping(target = "person", ignore = true)
    public abstract OrderEntity dtoToOrder(OrderDto orderDto);

    @AfterMapping
    public void mapPerson(OrderDto dto, @MappingTarget OrderEntity order){
        order.setPerson(dto.getPerson().getId());
    }

    @AfterMapping
    public void mapPerson(OrderEntity order, @MappingTarget OrderDto.OrderDtoBuilder dto){
        try {
            dto.person(authFeignService.findById(order.getPerson()));
        } catch (FeignException e){
            dto.person(UserDto.builder().id(order.getPerson()).build());
        }
    }
}
