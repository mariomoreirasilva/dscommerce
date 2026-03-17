package com.devsuperior.descommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.descommerce.dto.ProductDTO;
import com.devsuperior.descommerce.entities.Product;
import com.devsuperior.descommerce.repositories.ProductRepository;



@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		
		Product entity = repository.findById(id).get();
		return new ProductDTO(entity);				
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		
		Page<Product> result = repository.findAll(pageable);
		
		return result.map(x -> new ProductDTO(x));

	}
	
}	
