package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.CocktailDto;
import com.alsab.boozycalc.dto.IngredientDto;
import com.alsab.boozycalc.dto.RecipeDto;
import com.alsab.boozycalc.entity.CocktailEntity;
import com.alsab.boozycalc.entity.IngredientEntity;
import com.alsab.boozycalc.entity.RecipeEntity;
import com.alsab.boozycalc.entity.RecipeId;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {CocktailMapper.class, IngredientMapper.class})
public interface RecipeMapper {
    @Mapping(target = "id", ignore = true)
    RecipeEntity dtoToRecipe(RecipeDto dto);

    @Mapping(target = "ingredient", ignore = true)
    @Mapping(target = "cocktail", ignore = true)
    RecipeDto recipeToDto(RecipeEntity dto);

    @AfterMapping
    default void mapId(RecipeDto dto, @MappingTarget RecipeEntity recipe){
        CocktailMapper cock_mapper = Mappers.getMapper(CocktailMapper.class);
        IngredientMapper ingr_mapper = Mappers.getMapper(IngredientMapper.class);

        CocktailEntity cocktail = cock_mapper.dtoToCocktail(dto.getCocktail());
        IngredientEntity ingredient = ingr_mapper.dtoToIngredient(dto.getIngredient());

        recipe.setId(new RecipeId(ingredient, cocktail));
    }


    @AfterMapping
    default void mapId(RecipeEntity recipe, @MappingTarget RecipeDto dto){
        CocktailMapper cock_mapper = Mappers.getMapper(CocktailMapper.class);
        IngredientMapper ingr_mapper = Mappers.getMapper(IngredientMapper.class);

        CocktailDto cock_dto = cock_mapper.cocktailToDto(recipe.getId().getCocktail());
        IngredientDto ingr_dto = ingr_mapper.ingredientToDto(recipe.getId().getIngredient());

        dto.setCocktail(cock_dto);
        dto.setIngredient(ingr_dto);
    }

}
