package com.box.files.service;

import com.box.files.dao.UsuarioRepository;
import com.box.files.domain.Usuario;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public @Nullable Usuario findById(Long id) {
        Usuario usuario = null;
        Optional<Usuario> optional = this.usuarioRepository.findById(id);
        if (optional.isPresent()) {
            usuario = optional.get();
        }
        return usuario;
    }
}
