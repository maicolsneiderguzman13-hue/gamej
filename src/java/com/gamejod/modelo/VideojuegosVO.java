/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamejod.modelo;

/**
 *
 * @author maico
 */
public class VideojuegosVO {
    private int idjuego;
    private String titulo;
    private String juego; 
    private int precio; 
    private String lanzamiento; 

    public VideojuegosVO() {
    }

    public VideojuegosVO(String titulo, String juego, int precio, String lanzamiento) {
        this.titulo = titulo;
        this.juego = juego;
        this.precio = precio;
        this.lanzamiento = lanzamiento;
    }

    public VideojuegosVO(int idjuego, String titulo, String juego, int precio, String lanzamiento) {
        this.idjuego = idjuego;
        this.titulo = titulo;
        this.juego = juego;
        this.precio = precio;
        this.lanzamiento = lanzamiento;
    }

    public int getIdjuego() {
        return idjuego;
    }

    public void setIdjuego(int idjuego) {
        this.idjuego = idjuego;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getJuego() {
        return juego;
    }

    public void setJuego(String juego) {
        this.juego = juego;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getLanzamiento() {
        return lanzamiento;
    }

    public void setLanzamiento(String lanzamiento) {
        this.lanzamiento = lanzamiento;
    }
 
    
}
