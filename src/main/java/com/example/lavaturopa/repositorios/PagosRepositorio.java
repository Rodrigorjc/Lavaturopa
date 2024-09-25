package com.example.lavaturopa.repositorios;

import com.example.lavaturopa.modelos.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepositorio extends JpaRepository<Pagos, Integer> {
}
