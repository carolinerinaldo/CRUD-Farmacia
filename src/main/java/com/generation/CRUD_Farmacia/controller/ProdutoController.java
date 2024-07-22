package com.generation.CRUD_Farmacia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.generation.CRUD_Farmacia.model.Produto;
import com.generation.CRUD_Farmacia.repository.CategoriaRepository;
import com.generation.CRUD_Farmacia.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Produto>> getAll() {
        return ResponseEntity.ok(produtoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome) {
        return ResponseEntity.ok(produtoRepository.findByNomeContainingIgnoreCase(nome));
    }

    @PostMapping
    public ResponseEntity<Produto> post(@RequestBody Produto produto) {
        return categoriaRepository.findById(produto.getCategoria().getId())
                .map(categoria -> {
                    produto.setCategoria(categoria);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(produtoRepository.save(produto));
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping
    public ResponseEntity<Produto> put(@RequestBody Produto produto) {
        return produtoRepository.findById(produto.getId())
                .map(existingProduto -> categoriaRepository.findById(produto.getCategoria().getId())
                        .map(categoria -> {
                            produto.setCategoria(categoria);
                            return ResponseEntity.status(HttpStatus.OK)
                                    .body(produtoRepository.save(produto));
                        })
                        .orElse(ResponseEntity.badRequest().build()))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        produtoRepository.deleteById(id);
    }
}
