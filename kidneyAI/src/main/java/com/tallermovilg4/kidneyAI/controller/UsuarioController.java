
package com.tallermovilg4.kidneyAI.controller;

import com.tallermovilg4.kidneyAI.model.Usuario;
import com.tallermovilg4.kidneyAI.repository.IUsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioRepository usuarioRepo;

    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepo.findById(id);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        return usuarioRepo.save(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioRepo.deleteById(id);
    }

    // 🔐 Login por correo y contraseña
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestParam String correo, @RequestParam String contraseña) {
        Optional<Usuario> usuario = usuarioRepo.findByCorreoAndContraseña(correo, contraseña);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(401).build()); // 401 Unauthorized
    }
}
