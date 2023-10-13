package org.java.app.controller;

import java.util.List;

import org.java.app.db.pojo.Deal;
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
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class PizzaController {
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@GetMapping
	public String index(@RequestParam(required = false) String filter, Model model) {
		List<Pizza> pizzas = filter == null
								? pizzaService.findAll()
								: pizzaService.filterByNameOrDescription(filter, filter);
		
		for(Pizza pizza : pizzas) {
			float discount = 0f;
			
			List<Deal> activeDeals = pizza.getDeals().stream().filter(deal -> deal.isAfterOrEqual(deal.getEndDate())).toList();
			
			for(Deal deal : activeDeals) {
				discount = deal.getDiscount() > discount ? deal.getDiscount() : discount;
			}
			
			pizza.setDiscount(discount);
		}
		
		model.addAttribute("pizzas", pizzas);
		model.addAttribute("filter", filter);
		
		return "/pizza/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable int id, Model model) {
		Pizza pizza = pizzaService.findById(id);
		
		List<Deal> activeDeals = pizza.getDeals().stream().filter(deal -> deal.isAfterOrEqual(deal.getEndDate())).toList();
		
		float discount = 0f;
		
		for(Deal deal : activeDeals) {
			discount = deal.getDiscount() > discount ? deal.getDiscount() : discount;
		}
		
		pizza.setDiscount(discount);
		
		model.addAttribute("pizza", pizza);
		
		return "/pizza/show";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("ingredients", ingredientService.findAll());
		
		return "/pizza/create-update";
	}
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute Pizza pizza, BindingResult bindingResult, Model model) {
		return storeUpdate(bindingResult, model, pizza);
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("pizza", pizzaService.findById(id));
		model.addAttribute("ingredients", ingredientService.findAll());
		
		return "/pizza/create-update";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute Pizza pizza, BindingResult bindingResult, Model model) {
		return storeUpdate(bindingResult, model, pizza);
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id, Model model) {		
		pizzaService.deleteById(id);
		
		return "redirect:/";
	}
	
	private String storeUpdate(BindingResult bindingResult, Model model, Pizza pizza) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("ingredients", ingredientService.findAll());
			
			return "/pizza/create-update";
		}
		
		Pizza savedPizza = null;
		
		try {
			savedPizza = pizzaService.savePizza(pizza);			
		} catch (Exception e) {
			model.addAttribute("ingredients", ingredientService.findAll());
			
			return "/pizza/create-update";
		}
		
		return "redirect:/" + savedPizza.getId();
	}
}
