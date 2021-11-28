package br.senac.tads.dsw.eletrostore.controle;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import br.senac.tads.dsw.eletrostore.modelos.Cliente;
import br.senac.tads.dsw.eletrostore.repositorios.ClienteRepositorio;
import br.senac.tads.dsw.eletrostore.repositorios.EnderecoRepositorio;

@Controller
public class ClienteControle {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private EnderecoRepositorio enderecoRepositorio;
    

    @GetMapping("/cliente/cadastrar")
    public ModelAndView cadastrar(Cliente cliente) {
        ModelAndView mv = new ModelAndView("cliente/cadastrar");
        mv.addObject("cliente", cliente);
        return mv;
    }

    @GetMapping("/cliente/loginCliente")
    public ModelAndView login(Cliente cliente) {
        ModelAndView mv = new ModelAndView("cliente/loginCliente");
        mv.addObject("cliente", cliente);
        return mv;
    }

    @GetMapping("/cliente/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        return cadastrar(cliente.get());
    }

    @PostMapping("/cliente/salvar")
    public ModelAndView salvar(@Valid Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(cliente);
        }
        cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
        
        clienteRepositorio.saveAndFlush(cliente);
        
        return cadastrar(new Cliente());
    }

//    @GetMapping("cliente/cadastroEndereco/{id}")
//    public ModelAndView cadastrarEndereco(Cliente cliente, Endereco endereco) {
//        ModelAndView mv = new ModelAndView("/cliente/cadastroEndereco");
//        mv.addObject("cliente",cliente);
//        mv.addObject("endereco", endereco);
//        return mv;
//    }
//
////    @PostMapping("/cliente/endereco/salvar")
////    public ModelAndView salvarEndereco(Cliente cliente, @Valid Endereco endereco, BindingResult result) {
////        System.out.println(cliente);
////        ModelAndView mv = new ModelAndView("/");
////        if (result.hasErrors()) {
////            return cadastrarEndereco(cliente, endereco);
////        } 
////        
////        endereco.setCliente(cliente);
////        enderecoRepositorio.saveAndFlush(endereco);
////        return cadastrar(cliente);
////    }

}
