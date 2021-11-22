
package br.senac.tads.dsw.eletrostore.controle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Micael Santos
 */

@Controller
public class PrincipalControle {
    
    @GetMapping("/administrativo")
    public String acessarPrincipal(){
        return "administrativo/home";
    }
}
