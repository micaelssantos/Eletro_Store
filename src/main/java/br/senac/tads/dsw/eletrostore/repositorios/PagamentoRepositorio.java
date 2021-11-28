package br.senac.tads.dsw.eletrostore.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.senac.tads.dsw.eletrostore.modelos.Pagamento;
import org.springframework.data.jpa.repository.Query;

public interface PagamentoRepositorio extends JpaRepository<Pagamento, Long> {

    	@Query("from Pagamento where cliente_id=?1")
	public List<Pagamento> buscarPagamentos(Long id);
}