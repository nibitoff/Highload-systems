package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.ProductDto;
import com.alsab.boozycalc.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto productToDto(ProductEntity product);
    ProductEntity dtoToProduct(ProductDto productDto);
}
