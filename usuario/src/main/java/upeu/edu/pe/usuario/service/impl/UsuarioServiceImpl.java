package upeu.edu.pe.usuario.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import lombok.extern.slf4j.Slf4j;
import upeu.edu.pe.usuario.entity.Usuario;
import upeu.edu.pe.usuario.repository.UsuarioRepository;
import upeu.edu.pe.usuario.service.UsuarioService;

//@Slf4j
@Service

public class UsuarioServiceImpl implements UsuarioService{
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findUsuarioAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario createUsuario(Usuario usuario) {

        Usuario usuarioDB = usuarioRepository.findByNumberID( usuario.getNumberID() );
        if (usuarioDB != null){
            return  usuarioDB;
        }

        usuario.setRol("CREATED");
        usuarioDB = usuarioRepository.save ( usuario );
        return usuarioDB;
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
        Usuario usuarioDB = getUsuario(usuario.getId());
        if (usuarioDB == null){
            return  null;
        }
        usuarioDB.setFirstName(usuario.getFirstName());
        usuarioDB.setLastName(usuario.getLastName());
        usuarioDB.setEmail(usuario.getEmail());
        usuarioDB.setPhotoUrl(usuario.getPhotoUrl());

        return  usuarioRepository.save(usuarioDB);
    }

    @Override
    public Usuario deleteUsuario(Usuario usuario) {
        Usuario usuarioDB = getUsuario(usuario.getId());
        if (usuarioDB ==null){
            return  null;
        }
        usuario.setRol("DELETED");
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario getUsuario(Long id) {
        return  usuarioRepository.findById(id).orElse(null);
    }
}
