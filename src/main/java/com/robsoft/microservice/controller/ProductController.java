package com.robsoft.microservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.robsoft.microservice.model.Product;
import com.robsoft.microservice.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	// Get Entities List from Database with Spring Data JPA in Spring Boot
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> findAll() {
		return new ResponseEntity<Iterable<Product>>(productService.findAll(), HttpStatus.OK);
	}

	// Find Entity by Primary Key with Spring Data JPA in Spring Boot
	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Product>> findById(@PathVariable("id") int id) {
		return new ResponseEntity<Optional<Product>>(productService.findById(id), HttpStatus.OK);
	}

	// Use Custom Query with Spring Data JPA in Spring Boot
	@RequestMapping(value = "/products/search/name/{value}", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> findKeyword(@PathVariable("value") String string) {
		return new ResponseEntity<List<Product>>(productService.search(string), HttpStatus.OK);
	}

	// Create Entity with Spring Data JPA in Spring Boot
	@RequestMapping(value = "/products", method = RequestMethod.POST) 
	public ResponseEntity<Product> create(@RequestBody Product product) {
		return new ResponseEntity<Product>(productService.save(product), HttpStatus.CREATED);
	}

	
	// Update Entity with Spring Data JPA in Spring Boot
	@RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Product> update(@PathVariable("id") int id, @RequestBody Product product) {
			Product temp = productService.findById(id).get();
			temp.setName(product.getName());
			temp.setPhoto(product.getPhoto());
			temp.setDescription(product.getDescription());
			temp.setFeatured(product.getFeatured());
			temp.setPrice(product.getPrice());
			temp.setQuantity(product.getQuantity());
			return new ResponseEntity<Product>(productService.save(temp), HttpStatus.OK);
	}
	
		
	// Delete Entity with Spring Data JPA in Spring Boot
	@RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
	public HttpStatus deleteById(@PathVariable("id") int id) {
		if (productService.existsById(id)) {
			productService.deleteById(id);
			return HttpStatus.OK;
		} else {
			return HttpStatus.NO_CONTENT;
		}
	}
	
	
	// Use Text Plain/HTML in Spring Rest API in Spring Boot
	@RequestMapping(value = "/helloworld", method = RequestMethod.GET)
	public ResponseEntity<String> helloWorld() {
		return new ResponseEntity<String>("<b>HelloWorld</b>", HttpStatus.OK);
	}
	
	
	// Use Parameters in Spring Rest API in Spring Boot
	@RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
	public ResponseEntity<String> greeting(@PathVariable("name") String name) {
		return new ResponseEntity<String>("Hello " + name, HttpStatus.OK);
	}
	
	// Convert Object/List to/from JSON in Spring Rest API in Spring Boot
	@RequestMapping(value = "/createWithoutDB", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> createWithoutDB() {
		List<Product> products = new ArrayList<Product>();
		Product product = new Product();
		product.setName("Test");
		product.setDescription("This is a test");
		product.setPrice(1337);
		products.add(product);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
} 
