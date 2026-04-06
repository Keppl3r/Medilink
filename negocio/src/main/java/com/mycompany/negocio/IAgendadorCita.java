/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.negocio;

import com.mycompany.persistencia.entidades.Cita;

/**
 * Interfaz principal del caso de uso de agendamiento de citas.
 * Orquesta todo el flujo de negocio para agendar una cita.
 *
 * @author devor
 */
public interface IAgendadorCita {

    /**
     * Agenda una cita medica realizando todas las validaciones,
     * procesamiento de pago, persistencia y notificacion.
     *
     * @param dto datos de la cita a agendar
     * @return la cita agendada y confirmada
     * @throws Exception si ocurre algun error en el proceso
     */
    Cita agendarCita(DTO_Negocios dto) throws Exception;
}
