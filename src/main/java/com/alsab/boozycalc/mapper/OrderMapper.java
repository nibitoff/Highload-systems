package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.OrderDto;
import com.alsab.boozycalc.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto orderToDto(OrderEntity order);
    OrderEntity dtoToOrder(OrderDto orderDto);
}
