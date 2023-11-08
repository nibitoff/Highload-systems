package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.CocktailDto;
import com.alsab.boozycalc.dto.IngredientDto;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.CocktailMapper;
import com.alsab.boozycalc.mapper.IngredientMapper;
import com.alsab.boozycalc.repository.CocktailRepo;
import com.alsab.boozycalc.repository.IngredientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public CocktailDto add(CocktailDto dto) {
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
        cockt.setName(cocktail.getName());
        cockt.setDescription(cocktail.getDescription());
        cockt.setRecipe_description(cocktail.getRecipe_description());
        cockt.setId(cocktail.getId());
        return mapper.cocktailToDto(
                repo.save(mapper.dtoToCocktail(cockt))
        );
    }

    public void deleteById(Long id) {
        if (!repo.existsById(id)) throw new ItemNotFoundException(CocktailDto.class, id);
        repo.deleteById(id);
    }
}
