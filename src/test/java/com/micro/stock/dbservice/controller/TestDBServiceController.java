package com.micro.stock.dbservice.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.micro.stock.dbservice.model.Quotes;
import com.micro.stock.dbservice.repository.QuotesRepository;

@RunWith(MockitoJUnitRunner.class)
public class TestDBServiceController {

	@Mock
	QuotesRepository quotesRepository;
	
	@InjectMocks
	DBServiceController dbServiceController;
	
	@Test
	public void testGetQuotes() {
		String name = "VEX";
		when(quotesRepository.findByUsername(name)).thenReturn(stubQuotes());
		ResponseEntity<List<String>> list = dbServiceController.getQuotes(name);
		assertNotNull(list);
	}
	
	@Test
	public void testGetAll() {
		when(quotesRepository.findAll()).thenReturn(stubQuotes());
		ResponseEntity<List<Quotes>> list = dbServiceController.getAll();
		assertNotNull(list);
	}
	
	@Test
	public void testAddData() {
		Quotes quote = populateQuote();
		when(quotesRepository.save(quote)).thenReturn(quote);
		ResponseEntity<org.springframework.http.HttpStatus> status = dbServiceController.addData(quote);
		assertEquals("OK", status.getStatusCode().name());
	}
	
	@Test
	public void testModifyData() {
		String name = "VEX";
		Quotes quote = populateQuote();
		when(quotesRepository.findByUsername(name)).thenReturn(stubQuotes());
		ResponseEntity<org.springframework.http.HttpStatus> status = dbServiceController.modifyData(quote);
		assertEquals("OK", status.getStatusCode().name());
	}
	
	@Test
	public void testDelete() {
		String name = "VEX";
//		Quotes quote = populateQuote();
		when(quotesRepository.findByUsername(name)).thenReturn(stubQuotes());
		dbServiceController.deleteData(name);
		assertEquals(200, HttpStatus.SC_OK);
	}

	private Quotes populateQuote() {
		Quotes quote = new Quotes();
		quote.setId(5);
		quote.setQuote("GOOG");
		quote.setUsername("VEX");
		return quote;
	}

	private List<Quotes> stubQuotes() {
		List<Quotes> list = new ArrayList<Quotes>();
		Quotes quote = new Quotes();
		quote.setId(5);
		quote.setQuote("GOOG");
		quote.setUsername("VEX");
		list.add(quote);;
		return list;
	}
}
