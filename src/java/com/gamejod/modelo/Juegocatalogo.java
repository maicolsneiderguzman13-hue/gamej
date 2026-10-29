/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamejod.modelo;

/**
 *
 * @author maico
 */
public class Juegocatalogo {
    private int id;
    private String titulo;
    private String categoria;
    private String plataforma;
    private int precio;
    private String imagen;
    private int stock;
    private boolean proximamente;
    private String banner;
    private String jugadores;
    private String generos;
    private String clasificacion;
    private Double calificacion;
    private int numCalificaciones;
 
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public int getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public boolean isProximamente() { return proximamente; }
    public void setProximamente(boolean proximamente) { this.proximamente = proximamente; }
 
    public String getBanner() { return banner; }
    public void setBanner(String banner) { this.banner = banner; }
    public String getJugadores() { return jugadores; }
    public void setJugadores(String jugadores) { this.jugadores = jugadores; }
    public String getGeneros() { return generos; }
    public void setGeneros(String generos) { this.generos = generos; }
    public String getClasificacion() { return clasificacion; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }
    public Double getCalificacion() { return calificacion; }
    public void setCalificacion(Double calificacion) { this.calificacion = calificacion; }
    public int getNumCalificaciones() { return numCalificaciones; }
    public void setNumCalificaciones(int numCalificaciones) { this.numCalificaciones = numCalificaciones; }
}