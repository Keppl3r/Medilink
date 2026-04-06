/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.negocio;

/**
 * Interfaz para la validacion y procesamiento de pagos.
 * Define el contrato de la regla de negocio de cobros.
 *
 * @author devor
 */
public interface IValidadorPagos {

    /**
     * Valida y procesa un pago.
     *
     * @param monto cantidad a cobrar
     * @param referenciaPaciente identificador del paciente
     * @return true si el pago fue exitoso
     */
    boolean validarPago(double monto, String referenciaPaciente);
}
