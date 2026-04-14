/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.infraestructura;
/**
 * Interfaz para el servicio de procesamiento de pagos.
 * Define el contrato para realizar cobros.
 *
 * @author Adrian Mendoza
 */
public interface IPagos {

    /**
     * Procesa un pago por el monto indicado.
     *
     * @param monto cantidad a cobrar
     * @param referenciaPaciente identificador del paciente
     * @return true si el pago fue exitoso, false en caso contrario
     */
    boolean procesarPago(double monto, String referenciaPaciente);
}