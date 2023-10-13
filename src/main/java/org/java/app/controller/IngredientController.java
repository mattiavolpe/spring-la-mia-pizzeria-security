package org.java.app.controller;

import org.java.app.db.pojo.Ingredient;
import org.java.app.db.pojo.Pizza;
import org.java.app.db.service.IngredientService;
import org.java.app.db.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private PizzaService pizzaService;
	
	@GetMapping("/ingredients")
	public String index(Model model) {
		model.addAttribute("ingredients", ingredientService.findAll());
		return "/ingredient/index";
	}
	
	@GetMapping("/ingredients/create")
	public String create(Model model) {
		model.addAttribute("ingredient", new Ingredient());
		model.addAttribute("pizzas", pizzaService.findAll());
		
		return "/ingredient/create-update";
	}
	
	@PostMapping("/ingredients/create")
	public String store(@Valid @ModelAttribute Ingredient ingredient, BindingResult bindingResult, Model model) {
		ingredientService.saveIngredient(ingredient);
		
		System.out.println(ingredient.getPizzas());
		
		if (ingredient.getPizzas() != null) {
			
			for (Pizza pizza : ingredient.getPizzas()) {
				pizza.addIngredient(ingredient);
				
				pizzaService.savePizza(pizza);
			}
		}
		
		return "redirect:/ingredients";
	}
	
	@GetMapping("/ingredients/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("ingredient", ingredientService.findById(id));
		model.addAttribute("pizzas", pizzaService.findAll());
		
		return "/ingredient/create-update";
	}
	
	@PostMapping("/ingredients/edit/{id}")
	public String update(@Valid @ModelAttribute Ingredient ingredient, BindingResult bindingResult, Model model) {		
		ingredientService.saveIngredient(ingredient);
		
		for (Pizza pizza : pizzaService.findAll()) {
			if (ingredient.hasPizza(pizza)) {
				pizza.addIngredient(ingredient);
			} else {
				pizza.removeIngredient(ingredient);
			}
			
			pizzaService.savePizza(pizza);
		}
		
		return "redirect:/ingredients";
	}
	
	@PostMapping("/ingredients/delete/{id}")
	public String delete(@PathVariable int id) {
		Ingredient ingredient = ingredientService.findById(id);
		
		for (Pizza pizza : ingredient.getPizzas()) {
			pizza.removeIngredient(ingredient);
			
			pizzaService.savePizza(pizza);
		}
		
		ingredientService.deleteById(id);
		
		return "redirect:/ingredients";
	}
}
