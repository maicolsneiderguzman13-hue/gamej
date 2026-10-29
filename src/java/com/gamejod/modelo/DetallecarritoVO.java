/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamejod.modelo;

/**
 *
 * @author maico
 */
public class DetallecarritoVO extends CarritoVO {

    private int id;
    private String videojuego;
    private int cantidad;

    public DetallecarritoVO() {
    }

    public DetallecarritoVO(int id, String usuarios, String fechaC, String detalle) {
        super(id, usuarios, fechaC, detalle);
    }

    public DetallecarritoVO(int id, String videojuego, int cantidad,
                            String usuarios, String fechaC, String detalle) {

        super(id, usuarios, fechaC, detalle);

        this.id = id;
        this.videojuego = videojuego;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideojuego() {
        return videojuego;
    }

    public void setVideojuego(String videojuego) {
        this.videojuego = videojuego;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}