package org.java.app;

import java.time.LocalDate;

import org.java.app.db.pojo.Deal;
import org.java.app.db.pojo.Ingredient;
import org.java.app.db.pojo.Pizza;
import org.java.app.db.service.DealService;
import org.java.app.db.service.IngredientService;
import org.java.app.db.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLaMiaPizzeriaCrudApplication implements CommandLineRunner {

	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private DealService dealService;
	
	@Autowired
	private IngredientService ingredientService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringLaMiaPizzeriaCrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Ingredient ingredient1 = new Ingredient("Tomato sauce");
		Ingredient ingredient2 = new Ingredient("Mozzarella");
		Ingredient ingredient3 = new Ingredient("Natural tuna");
		Ingredient ingredient4 = new Ingredient("Red onions");
		Ingredient ingredient5 = new Ingredient("Artichokes");
		Ingredient ingredient6 = new Ingredient("Mushrooms");
		Ingredient ingredient7 = new Ingredient("Anchovies");
		Ingredient ingredient8 = new Ingredient("Black olives");
		Ingredient ingredient9 = new Ingredient("Baked ham");
		
		ingredientService.saveIngredient(ingredient1);
		ingredientService.saveIngredient(ingredient2);
		ingredientService.saveIngredient(ingredient3);
		ingredientService.saveIngredient(ingredient4);
		ingredientService.saveIngredient(ingredient5);
		ingredientService.saveIngredient(ingredient6);
		ingredientService.saveIngredient(ingredient7);
		ingredientService.saveIngredient(ingredient8);
		ingredientService.saveIngredient(ingredient9);
		
		System.out.println("\n--------------------\nIngredients seeded\n--------------------");
		
		Pizza pizza1 = new Pizza("Margherita", "Tomato sauce and mozzarella", "https://images.pexels.com/photos/6605214/pexels-photo-6605214.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", 650, ingredient1, ingredient2);
		Pizza pizza2 = new Pizza("Tuna and onion", "Natural tuna, red onions and mozzarella", "https://www.galbani.it/sites/default/files/styles/width_1920/public/se_tonno_buona_da_star_bene_980x357.jpg", 900, ingredient3, ingredient4, ingredient2);
		Pizza pizza3 = new Pizza("Capricious", "Tomato sauce, artichokes, mushrooms, anchovies, black olives, baked ham and mozzarella", "https://www.pizza.it/wp-content/uploads/import/body/1/8/9/4/pizza_capricciosa_-_by_pizza.it_.jpg", 900, ingredient1, ingredient5, ingredient6, ingredient7, ingredient8, ingredient9, ingredient2);
		
		pizzaService.savePizza(pizza1);
		pizzaService.savePizza(pizza2);
		pizzaService.savePizza(pizza3);
		
		System.out.println("\n--------------------\nPizzas seeded\n--------------------");
		
		Deal deal1 = new Deal(LocalDate.now(), LocalDate.parse("2023-12-31"), pizza2.getName() + " deal", 10f, pizza2);
		Deal deal2 = new Deal(LocalDate.now(), LocalDate.parse("2023-11-12"), pizza1.getName() + " deal", 20.5f, pizza1);
		Deal deal3 = new Deal(LocalDate.parse("2023-10-09"), LocalDate.parse("2023-10-10"), pizza3.getName() + " expired deal", 30f, pizza3);
		Deal deal4 = new Deal(LocalDate.parse("2023-09-01"), LocalDate.parse("2023-10-01"), pizza1.getName() + " expired deal", 50f, pizza1);
		Deal deal5 = new Deal(LocalDate.now(), LocalDate.parse("2024-12-31"), pizza2.getName() + " deal with greater percentage", 50f, pizza2);
		
		dealService.saveDeal(deal1);
		dealService.saveDeal(deal2);
		dealService.saveDeal(deal3);
		dealService.saveDeal(deal4);
		dealService.saveDeal(deal5);
		
		System.out.println("\n--------------------\nDeals seeded\n--------------------");
	}
}
