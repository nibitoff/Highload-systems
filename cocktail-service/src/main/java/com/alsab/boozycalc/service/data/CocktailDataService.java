package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.CocktailDto;
import com.alsab.boozycalc.exception.ItemNameIsAlreadyTakenException;
import com.alsab.boozycalc.exception.ItemNotFoundByNameException;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.CocktailMapper;
import com.alsab.boozycalc.repository.CocktailRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CocktailDataService {
    private final CocktailRepo repo;
    private final CocktailMapper mapper;

    public Iterable<CocktailDto> findAll() {
        return repo.findAll().stream().map(mapper::cocktailToDto).toList();
    }

    public CocktailDto findById(Long id) {
        return mapper.cocktailToDto(
                repo.findById(id).orElseThrow(() -> new ItemNotFoundException(CocktailDto.class, id))
        );
    }

    public CocktailDto findByName(String name) {
        return mapper.cocktailToDto(
                repo.findByName(name).orElseThrow(() -> new ItemNotFoundByNameException(CocktailDto.class, name))
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

    public CocktailDto add(CocktailDto dto) {
        if(existsByName(dto.getName())){
            throw new ItemNameIsAlreadyTakenException(CocktailDto.class, dto.getName());
        }

        return mapper.cocktailToDto(
                repo.save(
                        mapper.dtoToCocktail(dto)
                )
        );
    }

    public CocktailDto edit(CocktailDto cocktail) throws ItemNotFoundException {
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
        return mapper.cocktailToDto(
                repo.save(mapper.dtoToCocktail(cockt))
        );
    }

    public void deleteById(Long id) {
        if (!repo.existsById(id)) throw new ItemNotFoundException(CocktailDto.class, id);
        repo.deleteById(id);
    }

    public Iterable<CocktailDto> findAllWithPagination(Integer page){
        Pageable pageable = PageRequest.of(page, 50);
        return repo.findAllWithPagination(pageable).stream().map(mapper::cocktailToDto).toList();
    }

    public Iterable<CocktailDto> findAllWithPageAndSize(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAllWithPagination(pageable).stream().map(mapper::cocktailToDto).toList();
    }
}
