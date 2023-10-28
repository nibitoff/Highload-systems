package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.ProductDto;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.ProductMapper;
import com.alsab.boozycalc.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDataService {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    public List<ProductDto> findAll() {
        return productRepo.findAll().stream().map(productMapper::productToDto).toList();
    }

    public ProductDto findById(Long id) {
        return productMapper.productToDto(productRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(ProductDto.class, id)));
    }

    public void deleteById(Long id) {
        productRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(ProductDto.class, id));
        productRepo.deleteById(id);
    }

    public ProductDto add(ProductDto dto) {
        return productMapper.productToDto(productRepo.save(productMapper.dtoToProduct(dto)));
    }

    public ProductDto edit(ProductDto dto) {
        productRepo.findById(dto.getId()).orElseThrow(() -> new ItemNotFoundException(ProductDto.class, dto.getId()));
        return productMapper.productToDto(productRepo.save(productMapper.dtoToProduct(dto)));
    }
}
