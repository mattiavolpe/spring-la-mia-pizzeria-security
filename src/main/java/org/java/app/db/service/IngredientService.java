package org.java.app.db.service;

import java.util.List;

import org.java.app.db.pojo.Ingredient;
import org.java.app.db.repo.IngredientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
	@Autowired
	private IngredientRepo ingredientRepo;
	
	public void saveIngredient(Ingredient ingredient) {
		ingredientRepo.save(ingredient);
	}
	
	public List<Ingredient> findAll() {
		return ingredientRepo.findAll();
	}
	
	public Ingredient findById(int id) {
		return ingredientRepo.findById(id).get();
	}
	
	public void deleteById(int id) {
		ingredientRepo.deleteById(id);
	}
}
