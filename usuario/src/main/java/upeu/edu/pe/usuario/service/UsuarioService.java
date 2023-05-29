package upeu.edu.pe.usuario.service;

import java.util.List;

import upeu.edu.pe.usuario.entity.Usuario;

public interface UsuarioService {
    public List<Usuario> findUsuarioAll();
    public Usuario createUsuario(Usuario usuario);
    public Usuario updateUsuario(Usuario usuario);
    public Usuario deleteUsuario(Usuario usuario);
    public  Usuario getUsuario(Long id);
}
