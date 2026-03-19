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
	
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		
		Product entity = new Product();
		copyDTOtoEntity(dto, entity);		
		entity = repository.save(entity);		
		return new ProductDTO(entity);				
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		
		Product entity = repository.getReferenceById(id); //ainda não foi ao banco de dados, mas é monitorado pela jpa
		copyDTOtoEntity(dto, entity);		
		entity = repository.save(entity);		
		return new ProductDTO(entity);
				
	}
	
	
	@Transactional
	public void delete(Long id) {
		
		repository.deleteById(id);
		
	}

	private void copyDTOtoEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
	}	
	
}	
