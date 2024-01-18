package com.alsab.boozycalc.cocktail.service.data;

import com.alsab.boozycalc.cocktail.dto.ProductDto;
import com.alsab.boozycalc.cocktail.exception.ItemNameIsAlreadyTakenException;
import com.alsab.boozycalc.cocktail.exception.ItemNotFoundByNameException;
import com.alsab.boozycalc.cocktail.exception.ItemNotFoundException;
import com.alsab.boozycalc.cocktail.mapper.ProductMapper;
import com.alsab.boozycalc.cocktail.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductDataService {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    public Flux<ProductDto> findAll() {
        return Flux.fromStream(productRepo.findAll().stream().map(productMapper::productToDto));
    }

    public Mono<ProductDto> findById(Long id) throws ItemNotFoundException {
        if(!productRepo.existsById(id)) throw new ItemNotFoundException(ProductDto.class, id);
        return Mono.just(productMapper.productToDto(productRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(ProductDto.class, id))));
    }

    public ProductDto findByName(String name) {
        return productMapper.productToDto(
                productRepo.findByName(name).orElseThrow(() -> new ItemNotFoundByNameException(ProductDto.class, name))
        );
    }

    public Boolean existsByName(String name){
        try {
            findByName(name);
            return true;
        } catch (ItemNotFoundByNameException e) {
            return false;
        }
    }

    public Mono<Long> deleteById(Long id) {
        productRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(ProductDto.class, id));
        productRepo.deleteById(id);
        return Mono.just(id);
    }

    public Mono<ProductDto> add(ProductDto dto) {
        if(existsByName(dto.getName())){
            throw new ItemNameIsAlreadyTakenException(ProductDto.class, dto.getName());
        }
        return Mono.just(productMapper.productToDto(productRepo.save(productMapper.dtoToProduct(dto))));
    }

    public Mono<ProductDto> edit(ProductDto dto) {
        productRepo.findById(dto.getId()).orElseThrow(() -> new ItemNotFoundException(ProductDto.class, dto.getId()));

        if(existsByName(dto.getName())){
            ProductDto other = findByName(dto.getName());
            if(!Objects.equals(other.getId(), dto.getId()))
                throw new ItemNameIsAlreadyTakenException(ProductDto.class, dto.getName());
        }

        return Mono.just(productMapper.productToDto(productRepo.save(productMapper.dtoToProduct(dto))));
    }

    public Flux<ProductDto> findAllWithPagination(Integer page){
        Pageable pageable = PageRequest.of(page, 50);
        return Flux.fromStream(productRepo.findAllWithPagination(pageable).stream().map(productMapper::productToDto));
    }

    public Flux<ProductDto> findAllWithPageAndSize(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return Flux.fromStream(productRepo.findAllWithPagination(pageable).stream().map(productMapper::productToDto));
    }
}
