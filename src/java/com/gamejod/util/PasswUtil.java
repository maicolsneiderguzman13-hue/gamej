/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamejod.util;
 
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
 
/**
 * Cifrado de contraseñas con PBKDF2 (viene incluido en Java, no necesita librerías).
 * Formato guardado en la base de datos:  pbkdf2$iteraciones$sal$hash
 */
public final class PasswUtil {
 
    private static final String PREFIJO = "pbkdf2";
    private static final int ITERACIONES = 120000;
    private static final int TAM_SAL = 16;
    private static final int TAM_CLAVE_BITS = 256;
 
    private PasswUtil() {
    }
 
    /** Devuelve la contraseña cifrada lista para guardar en la base de datos. */
    public static String hash(String password) {
        byte[] sal = new byte[TAM_SAL];
        new SecureRandom().nextBytes(sal);
        byte[] h = derivar(password, sal, ITERACIONES);
        return PREFIJO + "$" + ITERACIONES + "$"
                + Base64.getEncoder().encodeToString(sal) + "$"
                + Base64.getEncoder().encodeToString(h);
    }
 
    /** true si el valor guardado ya está cifrado. */
    public static boolean estaCifrada(String guardada) {
        return guardada != null && guardada.startsWith(PREFIJO + "$");
    }
 
    /**
     * Compara la contraseña escrita con la guardada.
     * Acepta también contraseñas antiguas en texto plano (para no dejar a nadie afuera);
     * el sistema las cifra cuando el usuario inicia sesión.
     */
    public static boolean verificar(String password, String guardada) {
        if (password == null || guardada == null) {
            return false;
        }
        if (!estaCifrada(guardada)) {
            return MessageDigest.isEqual(
                    password.getBytes(StandardCharsets.UTF_8),
                    guardada.getBytes(StandardCharsets.UTF_8));
        }
        String[] partes = guardada.split("\\$");
        if (partes.length != 4) {
            return false;
        }
        try {
            int iteraciones = Integer.parseInt(partes[1]);
            byte[] sal = Base64.getDecoder().decode(partes[2]);
            byte[] esperado = Base64.getDecoder().decode(partes[3]);
            byte[] calculado = derivar(password, sal, iteraciones);
            return MessageDigest.isEqual(esperado, calculado);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
 
    private static byte[] derivar(String password, byte[] sal, int iteraciones) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), sal, iteraciones, TAM_CLAVE_BITS);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("No se pudo cifrar la contraseña", e);
        }
    }
}
