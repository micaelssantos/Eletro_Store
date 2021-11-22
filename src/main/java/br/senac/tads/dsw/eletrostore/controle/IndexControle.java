package br.senac.tads.dsw.eletrostore.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.tads.dsw.eletrostore.repositorios.ProdutoRepositorio;

@Controller
public class IndexControle {
	
	@Autowired
	private ProdutoRepositorio produtoRepositorio;

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mv =  new ModelAndView("/index");	
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		return mv;
	}
	
	

}
