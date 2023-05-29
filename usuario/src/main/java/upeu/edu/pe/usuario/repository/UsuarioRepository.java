package upeu.edu.pe.usuario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import upeu.edu.pe.usuario.entity.Usuario;



public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
    public Usuario findByNumberID(String numberID);
    public List<Usuario> findByLastName(String lastName);
}