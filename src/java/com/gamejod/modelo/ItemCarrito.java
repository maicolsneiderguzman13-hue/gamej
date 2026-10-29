/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamejod.modelo;

/**
 *
 * @author maico
 */
public class ItemCarrito {
    private int juegoId;
    private String titulo;
    private String imagen;
    private int precioUnitario;
    private int cantidad;
    private int stock;
 
    public int getJuegoId() { return juegoId; }
    public void setJuegoId(int juegoId) { this.juegoId = juegoId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public int getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(int precioUnitario) { this.precioUnitario = precioUnitario; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
 
    /** Precio de esta línea = precio por cantidad. */
    public int getTotal() { return precioUnitario * cantidad; }
}