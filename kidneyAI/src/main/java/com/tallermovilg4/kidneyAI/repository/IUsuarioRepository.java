package com.tallermovilg4.kidneyAI.repository;

import com.tallermovilg4.kidneyAI.model.Usuario;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByCorreoAndContraseña(String correo, String contraseña);
}
