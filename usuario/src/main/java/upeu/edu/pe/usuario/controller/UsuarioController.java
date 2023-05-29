package upeu.edu.pe.usuario.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import upeu.edu.pe.usuario.entity.Usuario;
import upeu.edu.pe.usuario.service.UsuarioService;

@Slf4j
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    // -------------------Retrieve All Usuarios--------------------------------------------
    
    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.findUsuarioAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable("id") long id) {
        log.info("Fetching Usuario with id {}", id);
        Usuario usuario = usuarioService.getUsuario(id);
        if (  null == usuario) {
            log.error("Usuario with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(usuario);
    }

    // -------------------Create a Usuario-------------------------------------------

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) {
        log.info("Creating Usuario : {}", usuario);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Usuario customerDB = usuarioService.createUsuario (usuario);

        return  ResponseEntity.status( HttpStatus.CREATED).body(customerDB);
    }

    // ------------------- Update a Usuario ------------------------------------------------

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable("id") long id, @RequestBody Usuario usuario) {
        log.info("Updating Usuario with id {}", id);

        Usuario currentCustomer = usuarioService.getUsuario(id);

        if ( null == currentCustomer ) {
            log.error("Unable to update. Usuario with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        usuario.setId(id);
        currentCustomer=usuarioService.updateUsuario(usuario);
        return  ResponseEntity.ok(currentCustomer);
    }

    // ------------------- Delete a Usuario-----------------------------------------

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Usuario> deleteUsuario(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Usuario with id {}", id);

        Usuario usuario = usuarioService.getUsuario(id);
        if ( null == usuario ) {
            log.error("Unable to delete. Usuario with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        usuario = usuarioService.deleteUsuario(usuario);
        return  ResponseEntity.ok(usuario);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
