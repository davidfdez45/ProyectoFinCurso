package com.example.proyectofincurso.model;

public class Mascotas {
    private int id;
    private String titulo;
    private String imagen;
    private String tipo;
    private String Raza;
    private String Sexo;
    private String Descripcion;
    private int id_rolMascota;
    private String ciudad;
    private int id_usuario;

    public Mascotas(int id, String titulo, String imagen, String tipo, String raza, String sexo,String descripcion, int id_rolMascota, String ciudad, int id_usuario) {
        this.id = id;
        this.titulo = titulo;
        this.imagen = imagen;
        this.tipo = tipo;
        this.Raza = raza;
        this.Sexo = sexo;
        this.Descripcion = descripcion;
        this.id_rolMascota = id_rolMascota;
        this.ciudad = ciudad;
        this.id_usuario = id_usuario;
    }
    public Mascotas(){

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public String getTipo() {
        return tipo;
    }

    public String getRaza() {
        return Raza;
    }

    public String getSexo() {
        return Sexo;
    }

    public int getId_rolMascota() {
        return id_rolMascota;
    }

    public String getCiudad() {
        return ciudad;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public int getId() { return id; }

    public String getDescripcion() { return Descripcion; }

    public void setDescripcion(String descripcion) { Descripcion = descripcion; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setRaza(String raza) {
        Raza = raza;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public void setId_rolMascota(int id_rolMascota) {
        this.id_rolMascota = id_rolMascota;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
