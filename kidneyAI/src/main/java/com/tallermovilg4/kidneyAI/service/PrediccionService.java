
package com.tallermovilg4.kidneyAI.service;

import org.springframework.stereotype.Service;


@Service
public class PrediccionService {
    public double calcularPrediccion() {
        return Math.random() < 0.5 ? 0 : 1; // se simula  0 o 1
    }
}

