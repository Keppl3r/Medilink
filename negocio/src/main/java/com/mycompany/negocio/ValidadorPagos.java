/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.negocio;

import com.mycompany.infraestructura.IPagos;

/**
 * Implementacion del validador de pagos.
 * Delega el procesamiento al servicio externo de pagos.
 *
 * @author devor
 */
public class ValidadorPagos implements IValidadorPagos {

    private final IPagos servicePagos;

    /**
     * Constructor con inyeccion de dependencia.
     *
     * @param servicePagos servicio externo de pagos
     */
    public ValidadorPagos(IPagos servicePagos) {
        this.servicePagos = servicePagos;
    }

    @Override
    public boolean validarPago(double monto, String referenciaPaciente) {
        if (monto <= 0) {
            return false;
        }
        return servicePagos.procesarPago(monto, referenciaPaciente);
    }
}
