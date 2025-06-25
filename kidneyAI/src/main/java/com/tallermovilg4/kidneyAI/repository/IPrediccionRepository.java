
package com.tallermovilg4.kidneyAI.repository;

import com.tallermovilg4.kidneyAI.model.Prediccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IPrediccionRepository extends JpaRepository<Prediccion, Long>{
    /**
     * Encuentra todas las predicciones hechas por un usuario específico.
     *
     * @param usuarioId ID del usuario
     * @return Lista de predicciones asociadas al usuario
     */
    List<Prediccion> findByUsuarioId(Long usuarioId);
}
