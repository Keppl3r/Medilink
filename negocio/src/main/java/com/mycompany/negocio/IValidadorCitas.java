/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.negocio;

import java.time.LocalDateTime;

/**
 * Interfaz para validaciones relacionadas con citas medicas.
 * Define las reglas de validacion de datos y disponibilidad.
 *
 * @author devor
 */
public interface IValidadorCitas {

    /**
     * Valida que los datos del DTO esten completos.
     *
     * @param dto datos de la cita a validar
     * @return true si los datos son validos
     */
    boolean validarDatosCompletos(DTO_Negocios dto);

    /**
     * Valida que el especialista tenga disponibilidad en la fecha indicada.
     *
     * @param idEspecialista identificador del especialista
     * @param fechaHora fecha y hora solicitada
     * @return true si el horario esta disponible
     */
    boolean validarDisponibilidad(int idEspecialista, LocalDateTime fechaHora);
}
