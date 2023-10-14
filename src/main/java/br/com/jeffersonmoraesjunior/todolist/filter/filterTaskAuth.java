package br.com.jeffersonmoraesjunior.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.jeffersonmoraesjunior.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Classe criada para a autenticação

@Component //classe mais generica de gerenciamento
public class filterTaskAuth extends OncePerRequestFilter{

    // validar se o usuario exist
    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if(servletPath.startsWith("/tasks/")) {
            //pegar a autenticacao (usuario e senha)
            var authorization = request.getHeader("Authorization");
            
            //remove a palavra Basic e os espaços
            var authEncoded = authorization.substring("Basic".length()).trim();

            //Decodificacao
            var authDecode = Base64.getDecoder().decode(authEncoded);
            var authString = new String(authDecode);

            // ["username", "password"]
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // validar usuario
            var user = this.userRepository.findByUserName(username);
            if (user == null) {
                response.sendError(401);
            }
            else {
                //validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if(passwordVerify.verified){  
                    request.setAttribute("idUser",user.getId());
                    // segue viagem
                    filterChain.doFilter(request, response);
                } 
                else {
                    response.sendError(401);
                }
            }

        }
        else {
            filterChain.doFilter(request, response);
        }

    }
    
}
