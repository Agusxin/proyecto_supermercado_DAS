package ar.edu.ubp.das.token;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import ar.edu.ubp.das.repository.ProveedorRestRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class TokenValidationInterceptor implements HandlerInterceptor {


    @Autowired
    private ProveedorRestRepository proveedorRestRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");
        
       // System.out.println("Authorization Header recibido: " + authorizationHeader);


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.replace("Bearer ", "");

            // Obtén el token almacenado
            String tokenAlmacenado = proveedorRestRepository.getToken();

            // Comparar tokens
            if (!token.equals(tokenAlmacenado)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido");
                return false; // Detener la ejecución del controlador
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token no proporcionado");
            return false;
        }

        return true; // Si el token es válido, continuar la solicitud
    }
	
}
