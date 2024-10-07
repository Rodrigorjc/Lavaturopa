package com.example.lavaturopa.repositorios;


import com.example.lavaturopa.modelos.Cliente;
import com.example.lavaturopa.modelos.Prendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrendasRepositorio extends JpaRepository<Prendas, Integer> {


}
