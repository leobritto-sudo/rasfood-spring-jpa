package com.rasmoo.api.rasfood.repository;

import com.rasmoo.api.rasfood.dto.CardapioDTO;
import com.rasmoo.api.rasfood.entity.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardapioRepository extends JpaRepository<Cardapio, Integer> {
    @Query("SELECT new com.rasmoo.api.rasfood.dto.CardapioDTO(c.nome, c.descricao, c.valor, c.categoria.nome) " +
            "FROM Cardapio c WHERE c.nome LIKE %:nome% AND c.disponivel = TRUE")
    List<CardapioDTO> findAllByName(String nome);

    @Query(value = "SELECT * FROM cardapio c WHERE c.categoria_id = ?1 AND c.disponivel = TRUE", nativeQuery = true)
    List<Cardapio> findAllByCategoria(Integer categoria);
}
