package org.java.app.db.service;

import org.java.app.db.pojo.Deal;
import org.java.app.db.repo.DealRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DealService {
	
	@Autowired
	private DealRepo dealRepo;
	
	public void saveDeal(Deal deal) {
		dealRepo.save(deal);
	}
	
	public Deal findById(int id) {
		Deal deal = dealRepo.findById(id).get();
		return deal;
	}
	
	public void deleteById(int id) {
		dealRepo.deleteById(id);
	}
}
