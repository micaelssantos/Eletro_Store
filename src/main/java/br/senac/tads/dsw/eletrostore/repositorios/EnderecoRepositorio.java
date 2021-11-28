package br.senac.tads.dsw.eletrostore.repositorios;

import br.senac.tads.dsw.eletrostore.modelos.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.senac.tads.dsw.eletrostore.modelos.Endereco;
import org.springframework.data.jpa.repository.Query;

public interface EnderecoRepositorio extends JpaRepository<Endereco, Long> {

    	@Query("from Endereco where cliente_id=?1")
	public List<Endereco> buscarEnderecos(Long id);
}