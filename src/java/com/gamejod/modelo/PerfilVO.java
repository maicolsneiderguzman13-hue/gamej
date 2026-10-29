/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamejod.modelo;

/**
 *
 * @author maico
 */
public class PerfilVO {
    private int id; 
    private String nombre; 
    private String foto; 
    private String descripcion;
    private int usuario;    

    public PerfilVO() {
    }

    public PerfilVO(int id, String nombre, String foto, String descripcion, int usuario) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    public PerfilVO(String nombre, String foto, String descripcion, int usuario) {
        this.nombre = nombre;
        this.foto = foto;
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }
    
}
