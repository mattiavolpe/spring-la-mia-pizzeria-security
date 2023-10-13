package org.java.app.db.repo;

import org.java.app.db.pojo.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepo extends JpaRepository<Deal, Integer>{

}
