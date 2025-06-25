package com.tallermovilg4.kidneyAI.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.persistence.Transient;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellidos;
    private String contraseña;
    private String correo;
    private String fechaNacimiento; // formato esperado: yyyy-MM-dd
    private int genero;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prediccion> predicciones = new ArrayList<>();

    // Constructor vacío
    public Usuario() {
    }

    // Getters y setters (puedes generar con IDE o usar Lombok)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public List<Prediccion> getPredicciones() {
        return predicciones;
    }

    public void setPredicciones(List<Prediccion> predicciones) {
        this.predicciones = predicciones;
    }

    // Getter calculado para la edad
    @Transient
    public int getEdad() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate nacimiento = LocalDate.parse(this.fechaNacimiento, formatter);
            return Period.between(nacimiento, LocalDate.now()).getYears();
        } catch (DateTimeParseException e) {
            return -1; // Devuelve -1 si el formato es inválido
        }
    }

}
