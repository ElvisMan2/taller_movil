
package com.tallermovilg4.kidneyAI.service;

import com.tallermovilg4.kidneyAI.model.Prediccion;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class PrediccionService implements IPrediccionService{

    private static final String URL = "http://137.184.120.154:8000/predict";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public double calcularPrediccion(Prediccion prediccion) {
        try {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("serumCreatinine", prediccion.getSerumCreatinine());
            jsonMap.put("gfr", prediccion.getGfr());
            jsonMap.put("proteinInUrine", prediccion.getProteinInUrine());
            jsonMap.put("itching", prediccion.getItching());
            jsonMap.put("muscleCramps", prediccion.getMuscleCramps());
            jsonMap.put("serumElectrolytesSodium", prediccion.getSerumElectrolytesSodium());
            jsonMap.put("gender", prediccion.getGender());
            jsonMap.put("age", prediccion.getAge());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonMap, headers);

            // Llamada al servicio externo
            Map<String, Object> response = restTemplate.postForObject(URL, request, Map.class);

            if (response != null && response.containsKey("riesgo_enfermedad_renal")) {
                boolean riesgo = Boolean.parseBoolean(response.get("riesgo_enfermedad_renal").toString());
                return riesgo ? 1.0 : 0.0;
            }
        } catch (Exception e) {
            System.err.println("Error al consumir la API: " + e.getMessage());
            e.printStackTrace();
        }
        return -1.0;
    }
}

