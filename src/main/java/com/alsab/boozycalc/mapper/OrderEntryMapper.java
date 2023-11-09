package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.CocktailDto;
import com.alsab.boozycalc.dto.OrderDto;
import com.alsab.boozycalc.dto.OrderEntryDto;
import com.alsab.boozycalc.entity.CocktailEntity;
import com.alsab.boozycalc.entity.OrderEntity;
import com.alsab.boozycalc.entity.OrderEntryEntity;
import com.alsab.boozycalc.entity.OrderEntryId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {CocktailMapper.class, OrderMapper.class})
public interface OrderEntryMapper {
    @Mapping(target = "id", ignore = true)
    OrderEntryEntity dtoToOrderEntry(OrderEntryDto dto);

    @Mapping(target = "cocktail", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderEntryDto orderEntryToDto(OrderEntryEntity dto);

    @AfterMapping
    default void mapId(OrderEntryDto dto, @MappingTarget OrderEntryEntity orderEntry){
        CocktailMapper cock_mapper = Mappers.getMapper(CocktailMapper.class);
        OrderMapper order_mapper = Mappers.getMapper(OrderMapper.class);

        CocktailEntity cocktail = cock_mapper.dtoToCocktail(dto.getCocktail());
        OrderEntity order = order_mapper.dtoToOrder(dto.getOrder());

        orderEntry.setId(new OrderEntryId(order, cocktail));
    }

    @AfterMapping
    default void mapId(OrderEntryEntity orderEntry, @MappingTarget OrderEntryDto dto){
        CocktailMapper cock_mapper = Mappers.getMapper(CocktailMapper.class);
        OrderMapper order_mapper = Mappers.getMapper(OrderMapper.class);

        CocktailDto cock_dto = cock_mapper.cocktailToDto(orderEntry.getId().getCocktail());
        OrderDto order_dto = order_mapper.orderToDto(orderEntry.getId().getOrder());

        dto.setOrder(order_dto);
        dto.setCocktail(cock_dto);
    }
}
