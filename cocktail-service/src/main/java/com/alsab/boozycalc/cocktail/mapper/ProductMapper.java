package com.alsab.boozycalc.cocktail.mapper;

import com.alsab.boozycalc.cocktail.dto.ProductDto;
import com.alsab.boozycalc.cocktail.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto productToDto(ProductEntity product);
    ProductEntity dtoToProduct(ProductDto productDto);
}
