package ar.edu.ubp.das.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
    private TokenValidationInterceptor tokenValidationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Registrar el filtro de validación de token
    	 registry.addInterceptor( tokenValidationInterceptor)
          .addPathPatterns("/**")
          .excludePathPatterns("/proveedor/datosProveedor");// Aplicar el filtro a todas las rutas
    }
}
