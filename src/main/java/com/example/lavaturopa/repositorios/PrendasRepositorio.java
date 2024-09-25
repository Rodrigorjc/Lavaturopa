package com.example.lavaturopa.repositorios;


import com.example.lavaturopa.modelos.Prendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrendasRepositorio extends JpaRepository<Prendas, Integer> {
}
