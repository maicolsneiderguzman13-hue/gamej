/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamejod.modelo;

/**
 *
 * @author maico
 */
public class CarritoVO {

    private int id;
    private String usuarios;
    private String fechaC;
    private String detalle;

    public CarritoVO() {
    }

    public CarritoVO(int id, String usuarios, String fechaC, String detalle) {
        this.id = id;
        this.usuarios = usuarios;
        this.fechaC = fechaC;
        this.detalle = detalle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuarios) {
        this.usuarios = usuarios;
    }

    public String getFechaC() {
        return fechaC;
    }

    public void setFechaC(String fechaC) {
        this.fechaC = fechaC;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

}
