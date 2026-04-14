/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.infraestructura;


/**
 * Servicio simulado de notificaciones por correo electronico.
 * Implementa ICorreos imprimiendo en consola para simular el envio.
 *
 * @author Adrian Mendoza  
 */
public class SS_Notificaciones implements ICorreos {

    @Override
    public void enviarConfirmacion(String correo, String mensaje) {
        System.out.println("========== NOTIFICACION POR CORREO ==========");
        System.out.println("Para: " + correo);
        System.out.println("Mensaje: " + mensaje);
        System.out.println("Estado: Enviado exitosamente (simulado)");
        System.out.println("==============================================");
    }
}