package com.rasmoo.api.rasfood.repository;

import com.rasmoo.api.rasfood.entity.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardapioRepository extends JpaRepository<Cardapio, Integer> {
    @Query(value = "SELECT * FROM cardapio c WHERE c.categoria_id = ?1", nativeQuery = true)
    List<Cardapio> findAllByCategoria(Integer categoria);
}
