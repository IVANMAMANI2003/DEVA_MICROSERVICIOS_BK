package upeu.edu.pe.venta.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import upeu.edu.pe.venta.dto.Usuario;

@FeignClient(name = "usuario-service",path = "/usuario",fallback = UsuarioHystrixFallbackFactory.class)
public interface UsuarioFeing {

    @GetMapping(value = "/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable("id") long id);
}
