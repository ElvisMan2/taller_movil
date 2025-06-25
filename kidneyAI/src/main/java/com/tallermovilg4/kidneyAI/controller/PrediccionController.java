
package com.tallermovilg4.kidneyAI.controller;

import com.tallermovilg4.kidneyAI.model.Prediccion;
import com.tallermovilg4.kidneyAI.repository.IPrediccionRepository;
import com.tallermovilg4.kidneyAI.repository.IUsuarioRepository;
import com.tallermovilg4.kidneyAI.service.PrediccionService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para manejar operaciones relacionadas con predicciones médicas.
 */
@RestController
@RequestMapping("/api/predicciones")
public class PrediccionController {

    @Autowired
    private IPrediccionRepository prediccionRepo;

    @Autowired
    private IUsuarioRepository usuarioRepo;

    @Autowired
    private PrediccionService servicio;

    @PostMapping("/{usuarioId}")
    public ResponseEntity<Prediccion> crearPrediccion(@PathVariable Long usuarioId, @RequestBody Prediccion pred) {
        return usuarioRepo.findById(usuarioId).map(usuario -> {
            pred.setUsuario(usuario);
            pred.setResultado(servicio.calcularPrediccion());
            pred.setFechaRegistro(LocalDate.now());
            return ResponseEntity.ok(prediccionRepo.save(pred));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public List<Prediccion> listar() {
        return prediccionRepo.findAll();
    }

    /**
     * Devuelve todas las predicciones asociadas a un usuario específico.
     *
     * @param idUsuario ID del usuario.
     * @return Lista de predicciones del usuario.
     */
    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<Prediccion>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        List<Prediccion> predicciones = prediccionRepo.findByUsuarioId(idUsuario);
        if (predicciones.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si no hay predicciones
        }
        return ResponseEntity.ok(predicciones); // 200 OK con la lista
    }

}

