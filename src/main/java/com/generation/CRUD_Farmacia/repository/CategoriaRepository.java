package com.generation.CRUD_Farmacia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.CRUD_Farmacia.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
