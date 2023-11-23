package net.codejava.inventory;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;
	
	@GetMapping("/products")
	public ResponseEntity<?> list() {
		return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<?> get(@PathVariable Integer id) {
		try {
			Product product = service.get(id);
			return new ResponseEntity<>(product, HttpStatus.OK);
		} catch (NoSuchElementException e){
			return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/products")
	public ResponseEntity<?> add(@RequestBody Product product) {
		try {
			service.save(product);
			return new ResponseEntity<>("Product added", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("There was a problem with the server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<?> update(@RequestBody Product product, @PathVariable Integer id) {
	    try {
	        Product existProduct = service.get(id);
	        service.save(product);
	        return new ResponseEntity<>("Product updated", HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
	    }      
	}
	
	@DeleteMapping("products/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		try {
			Product existProduct = service.get(id);
			service.delete(id);
			return new ResponseEntity<>("Product deleted", HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
		}
	}
}
