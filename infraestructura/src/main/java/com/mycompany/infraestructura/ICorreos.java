package com.mycompany.infraestructura;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 /**
 * Interfaz para el servicio de envio de correos electronicos.
 * Define el contrato para notificaciones por correo.
 *
 * @author Adrian Mendoza
 */
public interface ICorreos {

    /**
     * Envia un correo de confirmacion al paciente.
     *
     * @param correo direccion de correo del destinatario
     * @param mensaje contenido del mensaje a enviar
     */
    void enviarConfirmacion(String correo, String mensaje);
}