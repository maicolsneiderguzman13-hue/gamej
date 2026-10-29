/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamejod.util;

/**
 *
 * @author maico
 */
public final class Moneda {
 
    private Moneda() {
    }
 
    public static String cop(long valor) {
        String digitos = Long.toString(Math.abs(valor));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digitos.length(); i++) {
            if (i > 0 && (digitos.length() - i) % 3 == 0) {
                sb.append('.');
            }
            sb.append(digitos.charAt(i));
        }
        return (valor < 0 ? "-$" : "$") + sb + " COL";
    }
}
