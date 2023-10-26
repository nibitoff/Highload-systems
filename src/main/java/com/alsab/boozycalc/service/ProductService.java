package com.alsab.boozycalc.service;

import com.alsab.boozycalc.dto.ProductDto;
import com.alsab.boozycalc.entity.CocktailEntity;
import com.alsab.boozycalc.entity.IngredientEntity;
import com.alsab.boozycalc.entity.ProductEntity;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.repository.IngredientRepo;
import com.alsab.boozycalc.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    IngredientRepo ingredientRepo;

    public Iterable<ProductEntity> findAll() {
        return productRepo.findAll();
    }

    public void deleteProduct(Long productId) throws ItemNotFoundException {
        productRepo.deleteById(productId);
    }

    public ProductEntity addProduct(ProductEntity product) {
        return productRepo.save(product);
    }

    public ProductEntity addProduct(ProductDto product) throws ItemNotFoundException {
        return addProduct(new ProductEntity(
                product.name(),
                product.description(),
                ingredientRepo.findById(product.ingredient_id()).orElseThrow(() -> new ItemNotFoundException("no ingredient with id " + product.ingredient_id())),
                product.price()
        ));
    }
    public ProductEntity editProduct(ProductEntity product) throws ItemNotFoundException, IllegalArgumentException {
        ProductEntity prod = productRepo.findById(product.getId()).orElseThrow(() -> new ItemNotFoundException("product with id " + product.getId() + " was not found"));
        prod.setName(product.getName());
        prod.setDescription(product.getDescription());
        prod.setPrice(product.getPrice());
        prod.setIngredientId(product.getIngredientId());
        return productRepo.save(prod);
    }
}
