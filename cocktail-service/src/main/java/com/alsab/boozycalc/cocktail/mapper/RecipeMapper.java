package com.alsab.boozycalc.cocktail.mapper;

import com.alsab.boozycalc.cocktail.dto.CocktailDto;
import com.alsab.boozycalc.cocktail.dto.IngredientDto;
import com.alsab.boozycalc.cocktail.dto.RecipeDto;
import com.alsab.boozycalc.cocktail.entity.CocktailEntity;
import com.alsab.boozycalc.cocktail.entity.IngredientEntity;
import com.alsab.boozycalc.cocktail.entity.RecipeEntity;
import com.alsab.boozycalc.cocktail.entity.RecipeId;
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
