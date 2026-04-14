/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.negocio;

import com.mycompany.persistencia.IAccesoDatos;
import com.mycompany.persistencia.entidades.Cita;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del validador de citas.
 * Verifica datos completos y disponibilidad de horarios.
 *
 * @author devor
 */
public class ValidadorCitas implements IValidadorCitas {

    private final IAccesoDatos accesoDatos;

    /**
     * Constructor con inyeccion de dependencia.
     *
     * @param accesoDatos acceso a datos para consultar disponibilidad
     */
    public ValidadorCitas(IAccesoDatos accesoDatos) {
        this.accesoDatos = accesoDatos;
    }

    @Override
    public boolean validarDatosCompletos(DTO_Negocios dto) {
        if (dto == null) {
            return false;
        }
        if (dto.getIdEspecialista() <= 0) {
            return false;
        }
        if (dto.getIdPaciente() <= 0) {
            return false;
        }
        if (dto.getMotivo() == null || dto.getMotivo().trim().isEmpty()) {
            return false;
        }
        if (dto.getFechaHora() == null) {
            return false;
        }
        if (dto.getCostoConsulta() <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validarDisponibilidad(int idEspecialista, LocalDateTime fechaHora) {
        if (accesoDatos.obtenerEspecialistaPorId(idEspecialista) == null) {
            return false;
        }
        List<Cita> citasExistentes = accesoDatos.obtenerCitasPorEspecialista(idEspecialista);
        for (Cita cita : citasExistentes) {
            if (cita.getFechaHora().equals(fechaHora)) {
                return false;
            }
        }
        return true;
    }
}
