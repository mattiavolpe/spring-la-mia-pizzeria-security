package org.java.app.controller;

import org.java.app.db.pojo.Deal;
import org.java.app.db.service.DealService;
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
public class DealController {
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private DealService dealService;
	
	@GetMapping("/{pizza_id}/new-deal")
	public String create(@PathVariable("pizza_id") int id, Model model) {
		model.addAttribute("deal", new Deal());
		model.addAttribute("pizza", pizzaService.findById(id));
		model.addAttribute("pizzas", pizzaService.findAll());
		
		return "/deal/create-update";
	}
	
	@PostMapping("/{pizza_id}/new-deal")
	public String store(@PathVariable("pizza_id") int id, @Valid @ModelAttribute Deal deal, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("pizza", pizzaService.findById(id));
			
			return "/deal/create-update";
		}
		
		deal.setPizza(pizzaService.findById(id));
		
		dealService.saveDeal(deal);
		
		return "redirect:/" + id;
	}
	
	@GetMapping("/deals/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		Deal deal = dealService.findById(id);
		
		model.addAttribute("deal", deal);
		model.addAttribute("pizza", pizzaService.findById(deal.getPizza().getId()));
		model.addAttribute("pizzas", pizzaService.findAll());
		
		return "/deal/create-update";
	}
	
	@PostMapping("/deals/edit/{id}")
	public String update(@PathVariable int id, @Valid @ModelAttribute Deal deal, BindingResult bindingResult, Model model) {		
		if (bindingResult.hasErrors()) {
			model.addAttribute("pizza", pizzaService.findById(deal.getPizza().getId()));
			model.addAttribute("pizzas", pizzaService.findAll());
			
			return "/deal/create-update";
		}
		
//		CODE TO RETRIEVE pizza_id IF NO SELECT WAS USED, BECAUSE NO pizza.id COMES FROM THE FORM
//		
//		Deal originalDeal = dealService.findById(id);
//		Pizza pizza = originalDeal.getPizza();
//		
//		deal.setPizza(pizza);
		
		dealService.saveDeal(deal);
		
		return "redirect:/" + deal.getPizza().getId();
	}
	
	@PostMapping("/deals/delete/{id}")
	public String delete(@PathVariable int id) {
		int pizzaId = dealService.findById(id).getPizza().getId();
		
		dealService.deleteById(id);
		
		return "redirect:/" + pizzaId;
	}
}
