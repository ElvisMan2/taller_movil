package com.tallermovilg4.kidneyAI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellidos;
    private String password;
    private String correo;
    private String fechaNacimiento; // formato esperado: yyyy-MM-dd
    private int genero;// 0 mujer, 1 hombre

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prediccion> predicciones = new ArrayList<>();

    @Transient
    public int getEdad() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate nacimiento = LocalDate.parse(this.fechaNacimiento, formatter);
            return Period.between(nacimiento, LocalDate.now()).getYears();
        } catch (DateTimeParseException e) {
            return -1;
        }
    }
}
