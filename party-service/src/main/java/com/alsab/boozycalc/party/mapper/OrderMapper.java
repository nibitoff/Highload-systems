package com.alsab.boozycalc.party.mapper;

import com.alsab.boozycalc.party.dto.OrderDto;
import com.alsab.boozycalc.party.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto orderToDto(OrderEntity order);
    OrderEntity dtoToOrder(OrderDto orderDto);
}
