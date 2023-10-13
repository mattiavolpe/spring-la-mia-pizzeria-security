package org.java.app.db.pojo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 100, nullable = false)
	@Length(min = 3, max = 100)
	@NotBlank
	private String name;
	
	@ManyToMany(mappedBy = "ingredients")
	private Set<Pizza> pizzas;
	
	public Ingredient() {}

	public Ingredient(String name, Pizza...pizzas) {
		setName(name);
		setPizzas(new HashSet<>(Arrays.asList(pizzas)));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(Set<Pizza> pizzas) {
		this.pizzas = pizzas;
	};
	
	public boolean hasPizza(Pizza pizza) {
		if (getPizzas() == null) return false;
		
		for (Pizza p : getPizzas()) {
			if (p.getId() == pizza.getId()) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return getName() + " ";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (!(obj instanceof Ingredient)) return false;
		
		Ingredient extObj = (Ingredient) obj;
		
		return getId() == extObj.getId();
	}
}
