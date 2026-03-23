package com.devsuperior.descommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.descommerce.dto.ProductDTO;
import com.devsuperior.descommerce.entities.Product;
import com.devsuperior.descommerce.repositories.ProductRepository;
import com.devsuperior.descommerce.services.exceptions.DataBaseExeption;
import com.devsuperior.descommerce.services.exceptions.ResourceNotFoundExeption;

import jakarta.persistence.EntityNotFoundException;



@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		
		Product entity = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundExeption("Produto não encontrado"));
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
		try {
		Product entity = repository.getReferenceById(id); //ainda não foi ao banco de dados, mas é monitorado pela jpa
		copyDTOtoEntity(dto, entity);		
		entity = repository.save(entity);		
		return new ProductDTO(entity);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundExeption("Producto para atualizar não existe");
		}
				
	}
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundExeption("Produto para excluir não existe");
		}
		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseExeption("Violação de integridade referencial");
		}
		
	}

	private void copyDTOtoEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
	}	
	
}	
