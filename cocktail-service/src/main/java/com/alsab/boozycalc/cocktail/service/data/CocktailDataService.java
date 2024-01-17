package com.alsab.boozycalc.cocktail.service.data;

import com.alsab.boozycalc.cocktail.exception.ItemNotFoundByNameException;
import com.alsab.boozycalc.cocktail.mapper.CocktailMapper;
import com.alsab.boozycalc.cocktail.repository.CocktailRepo;
import com.alsab.boozycalc.cocktail.dto.CocktailDto;
import com.alsab.boozycalc.cocktail.exception.ItemNameIsAlreadyTakenException;
import com.alsab.boozycalc.cocktail.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CocktailDataService {
    private final CocktailRepo repo;
    private final CocktailMapper mapper;

    public Flux<CocktailDto> findAll() {
        return Flux.fromStream(repo.findAll().stream().map(mapper::cocktailToDto));
    }

    public Mono<CocktailDto> findById(Long id) {
        return Mono.just(mapper.cocktailToDto(
                repo.findById(id).orElseThrow(() -> new ItemNotFoundException(CocktailDto.class, id)))
        );
    }

    public CocktailDto findByName(String name) {
        return mapper.cocktailToDto(
                repo.findByName(name).orElseThrow(() -> new ItemNotFoundByNameException(CocktailDto.class, name)))
        ;
    }

    public Boolean existsByName(String name){
        try {
            findByName(name);
            return true;
        } catch (ItemNotFoundByNameException e) {
            return false;
        }
    }

    public Mono<CocktailDto> add(CocktailDto dto) {
        if(existsByName(dto.getName())){
            throw new ItemNameIsAlreadyTakenException(CocktailDto.class, dto.getName());
        }
        return Mono.just(mapper.cocktailToDto(
                repo.save(mapper.dtoToCocktail(dto))
        ));
    }

    public Mono<CocktailDto> edit(CocktailDto cocktail) throws ItemNotFoundException {
        CocktailDto cockt = mapper.cocktailToDto(
                repo.findById(cocktail.getId()).orElseThrow(() -> new ItemNotFoundException(CocktailDto.class, cocktail.getId()))
        );

        if(existsByName(cocktail.getName())){
            CocktailDto other = findByName(cocktail.getName());
            if(!Objects.equals(other.getId(), cocktail.getId()))
                throw new ItemNameIsAlreadyTakenException(CocktailDto.class, cocktail.getName());
        }

        cockt.setName(cocktail.getName());
        cockt.setDescription(cocktail.getDescription());
        cockt.setSteps(cocktail.getSteps());
        cockt.setType(cocktail.getType());
        cockt.setId(cocktail.getId());
        return Mono.just(mapper.cocktailToDto(
                repo.save(mapper.dtoToCocktail(cockt))
        ));
    }

    public Mono<Long> deleteById(Long id) {
        if (!repo.existsById(id)) throw new ItemNotFoundException(CocktailDto.class, id);
        repo.deleteById(id);
        return Mono.just(id);
    }

    public Flux<CocktailDto> findAllWithPagination(Integer page){
        Pageable pageable = PageRequest.of(page, 50);
        return Flux.fromStream(repo.findAllWithPagination(pageable).stream().map(mapper::cocktailToDto));
    }

    public Flux<CocktailDto> findAllWithPageAndSize(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return Flux.fromStream(repo.findAllWithPagination(pageable).stream().map(mapper::cocktailToDto));
    }
}
