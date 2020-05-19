package com.micro.stock.dbservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.stock.dbservice.model.Quotes;
import com.micro.stock.dbservice.repository.QuotesRepository;

@Component
@RestController
@RequestMapping("/rest/db")
public class DBServiceController {
	
	private QuotesRepository quotesRepository;
	
	public DBServiceController(QuotesRepository quotesRepository) {
		this.quotesRepository = quotesRepository;
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<List<String>> getQuotes(@PathVariable("username") final String username) {
//		List<Quotes> list = quotesRepository.findAll();
		List<String> list = quotesRepository.findByUsername(username).stream().map(Quotes::getQuote).collect(Collectors.toList());
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Quotes>> getAll() {
		List<Quotes> list = quotesRepository.findAll();
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/add")
	public ResponseEntity<HttpStatus> addData(@RequestBody Quotes quote) {
		HttpStatus status = quotesRepository.save(quote) != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		return ResponseEntity.ok(status);
	}
	
	@PutMapping("/modify")
	public ResponseEntity<HttpStatus> modifyData(@RequestBody Quotes quote) {
		HttpStatus status = null;
		List<Quotes> list = quotesRepository.findByUsername(quote.getUsername());
		for(Quotes obj : list) {
			obj.setQuote(quote.getQuote());
			status = quotesRepository.save(obj) != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.ok(status);
	}
	
	@DeleteMapping("/delete/{username}")
	public ResponseEntity<HttpStatus> deleteData(@PathVariable("username") final String username) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		List<Quotes> list = quotesRepository.findByUsername(username);
		for(Quotes quote : list) {
			quotesRepository.delete(quote);
			status = HttpStatus.OK;
		}
		return ResponseEntity.ok(status);
	}
	
}
