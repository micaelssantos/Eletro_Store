package br.senac.tads.dsw.eletrostore.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.tads.dsw.eletrostore.modelos.Compra;

public interface CompraRepositorio extends JpaRepository<Compra, Long> {

}
