package com.example2.demo2.models;

import javax.persistence.*;

@Entity
@Table(name="usuario")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private long id;
    private String nombre;
    private String email;
    private Integer prioridad;
    private String telefono;
    private String sexo;
    private Integer edad;

    /**
     * Constructor por defecto de la clase UsuarioModel
     */
    public UsuarioModel(){

    }

    /**
     * Constructor completo de la clase UsuarioModel
     * @param id Id del usuario
     * @param nombre nombre del usuario
     * @param email email del usuario
     * @param prioridad numero de prioridad del usuario
     * @param telefono telefono del usuario
     * @param sexo sexo del usuario
     * @param edad edad del usuario
     */
    public UsuarioModel(long id, String nombre, String email, Integer prioridad, String telefono, String sexo, Integer edad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.prioridad = prioridad;
        this.telefono = telefono;
        this.sexo = sexo;
        this.edad = edad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
}
