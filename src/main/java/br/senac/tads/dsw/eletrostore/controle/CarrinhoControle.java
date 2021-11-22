package br.senac.tads.dsw.eletrostore.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.tads.dsw.eletrostore.modelos.Cliente;
import br.senac.tads.dsw.eletrostore.modelos.Compra;
import br.senac.tads.dsw.eletrostore.modelos.ItensCompra;
import br.senac.tads.dsw.eletrostore.modelos.Produto;
import br.senac.tads.dsw.eletrostore.repositorios.ClienteRepositorio;
import br.senac.tads.dsw.eletrostore.repositorios.CompraRepositorio;
import br.senac.tads.dsw.eletrostore.repositorios.ItensCompraRepositorio;
import br.senac.tads.dsw.eletrostore.repositorios.ProdutoRepositorio;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class CarrinhoControle {

    private List<ItensCompra> itensCompra = new ArrayList<ItensCompra>();
    private Compra compra = new Compra();
    private Cliente cliente;

    @Autowired
    private ProdutoRepositorio repositorioProduto;

    @Autowired
    private ClienteRepositorio repositorioCliente;

    @Autowired
    private CompraRepositorio repositorioCompra;

    @Autowired
    private ItensCompraRepositorio repositorioItensCompra;

    private void calcularTotal() {
        compra.setValorTotal(0.);
        itensCompra.forEach(it -> {
            compra.setValorTotal(compra.getValorTotal() + it.getValorTotal() );
        });
        
        compra.setValorTotal(compra.getValorTotal() + compra.getFrete());
    }

    @GetMapping("/carrinho")
    public ModelAndView chamarCarrinho() {
        ModelAndView mv = new ModelAndView("cliente/carrinho");
        calcularTotal();
        // System.out.println(compra.getValorTotal());
        mv.addObject("compra", compra);
        mv.addObject("listaItens", itensCompra);
        return mv;
    }

    private void buscarUsuarioLogado() {
        Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
        if (!(autenticado instanceof AnonymousAuthenticationToken)) {
            String email = autenticado.getName();
            cliente = repositorioCliente.buscarClienteEmail(email).get(0);
        }
    }

    @GetMapping("/finalizar")
    public ModelAndView finalizarCompra() {
        buscarUsuarioLogado();
        ModelAndView mv = new ModelAndView("cliente/finalizar");
        calcularTotal();
        mv.addObject("compra", compra);
        mv.addObject("listaItens", itensCompra);
        mv.addObject("cliente", cliente);
        return mv;
    }

    @PostMapping("/finalizar/confirmar")
    public ModelAndView confirmarCompra(String formaPagamento) {
        ModelAndView mv = new ModelAndView("cliente/mensagemFinalizou");
        compra.setCliente(cliente);
        compra.setFormaPagamento(formaPagamento);
        repositorioCompra.saveAndFlush(compra);

        itensCompra.stream().map(c -> {
            c.setCompra(compra);
            return c;
        }).forEachOrdered(c -> {
            repositorioItensCompra.saveAndFlush(c);
        });
        itensCompra = new ArrayList<>();
        compra = new Compra();
        return mv;
    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
        ModelAndView mv = new ModelAndView("cliente/carrinho");

        for (ItensCompra it : itensCompra) {
            if (it.getProduto().getId().equals(id)) {
                // System.out.println(it.getValorTotal());
                if (acao.equals(1)) {
                    it.setQuantidade(it.getQuantidade() + 1);
                    it.setValorTotal(0.);
                    it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                } else if (acao == 0) {
                    it.setQuantidade(it.getQuantidade() - 1);
                    it.setValorTotal(0.);
                    it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                }
                break;
            }
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/removerProduto/{id}")
    public String removerProdutoCarrinho(@PathVariable Long id) {

        for (ItensCompra it : itensCompra) {
            if (it.getProduto().getId().equals(id)) {
                itensCompra.remove(it);
                break;
            }
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public String adicionarCarrinho(@PathVariable Long id) {

        Optional<Produto> prod = repositorioProduto.findById(id);
        Produto produto = prod.get();

        int controle = 0;
        for (ItensCompra it : itensCompra) {
            if (it.getProduto().getId().equals(produto.getId())) {
                it.setQuantidade(it.getQuantidade() + 1);
                it.setValorTotal(0.);
                it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                controle = 1;
                break;
            }
        }
        if (controle == 0) {
            ItensCompra item = new ItensCompra();
            item.setProduto(produto);
            item.setValorUnitario(produto.getValorVenda());
            item.setQuantidade(item.getQuantidade() + 1);
            item.setValorTotal(item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario()));

            itensCompra.add(item);
        }

        return "redirect:/carrinho";
    }
}
