package org.java.app.db.pojo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Deal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	@NotNull
	private LocalDate startDate;
	
	@Column(nullable = false)
	@NotNull
	private LocalDate endDate;
	
	@Column(length = 100)
	@Length(min = 3, max = 100)
	@NotBlank
	private String title;
	
	@Column(nullable = false)
	@Positive
	private float discount;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Pizza pizza;
	
	public Deal() {}
	
	public Deal(LocalDate startDate, LocalDate endDate, String title, float discount, Pizza pizza) {
		setStartDate(startDate);
		setEndDate(endDate);
		setTitle(title);
		setDiscount(discount);
		setPizza(pizza);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}
	
	public Boolean isAfterOrEqual(LocalDate date) {
		return date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now());
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}
	
	public String getHtmlStartDate() {
		return getStartDate() == null ? null : getStartDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
	}
	
	public void setHtmlStartDate(String date) {
		setStartDate(LocalDate.parse(date));
	}
	
	public String getHtmlEndDate() {
		return getEndDate() == null ? null : getEndDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
	}
	
	public void setHtmlEndDate(String date) {
		setEndDate(LocalDate.parse(date));
	}
	
	@Override
	public String toString() {
		return "[" + getId() + "] " + getTitle();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (!(obj instanceof Deal)) return false;
		
		Deal extObj = (Deal) obj;
		
		return getId() == extObj.getId();
	}
}
