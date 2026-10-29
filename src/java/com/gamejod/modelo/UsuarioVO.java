/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamejod.modelo;

/**
 *
 * @author maicol
 */
public class UsuarioVO {

    private int id;
    private String nombre;
    private String usuario;
    private String password;
    private String fecha_registro;
    private String listarUsuario;

    public UsuarioVO() {
    }

    public UsuarioVO(int id, String nombre, String usuario, String password, String fecha_registro, String listarUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
        this.fecha_registro = fecha_registro;
        this.listarUsuario = listarUsuario;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
    
    public String setlistarUsuario (String listarUsuario) {
        return null;
    }

    public String getListarUsuario() {
        return listarUsuario;
    }

    public void setListarUsuario(String listarUsuario) {
        this.listarUsuario = listarUsuario;
    }

    public String getPasword() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}


