package br.com.todolistberock.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.todolistberock.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Autowired
    private IUserRepository userRepository;

    @Override 
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException{

        var servletPath = request.getServletPath();

        // Authenticar apenas se for na rota de Tasks
        //  startsWith informa que atendera apenas as rotas que começarem com "/tasks/"
        if(servletPath.startsWith("/tasks/")){
            // Pegar a autenticação (usuário e senha)
            var authorization = request.getHeader("Authorization");
            var authEncoded = authorization.substring("Basic".length()).trim();
            

            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
            var authString = new String(authDecoded);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];
        
            
            //--------------------------------------------------------
            // Validação de usuário
            var userExist = this.userRepository.findByUsername(username);
            if(userExist == null){
                response.sendError(401, "Usuário sem autorização");
            }else{
                // Validação da senha do usuário
                var pass_result = BCrypt.verifyer().verify(password.toCharArray(), userExist.getPassword());
                var passVerfify = pass_result.verified;

                if(passVerfify){
                    request.setAttribute("idUser", userExist.getId_user());
                    filterChain.doFilter(request, response);
                }else{
                    response.sendError(401, "Senha incorreta!");
                }  
            }

                 
        }else{            
            filterChain.doFilter(request, response);
        }

        

    }
}
