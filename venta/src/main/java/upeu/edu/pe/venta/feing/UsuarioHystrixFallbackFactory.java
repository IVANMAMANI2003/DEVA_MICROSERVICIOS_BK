package upeu.edu.pe.venta.feing;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import upeu.edu.pe.venta.dto.Usuario;

@Component
public class UsuarioHystrixFallbackFactory  implements UsuarioFeing{
    @Override
    public ResponseEntity<Usuario> getUsuario(long id) {
        Usuario usuario = Usuario.builder()
                .firstName("none")
                .lastName("none")
                .email("none")
                .photoUrl("none").build();
        return ResponseEntity.ok(usuario);
    }
}