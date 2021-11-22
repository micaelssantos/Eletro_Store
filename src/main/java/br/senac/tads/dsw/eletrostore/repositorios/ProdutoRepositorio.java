package br.senac.tads.dsw.eletrostore.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.tads.dsw.eletrostore.modelos.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

}
