package com.micro.stock.dbservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro.stock.dbservice.model.Quotes;
@Repository
public interface QuotesRepository extends JpaRepository<Quotes, Integer> {

	List<Quotes> findByUsername(String username);

}
