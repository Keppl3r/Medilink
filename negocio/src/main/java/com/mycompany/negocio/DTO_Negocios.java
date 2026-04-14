/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.negocio;

import java.time.LocalDateTime;

/**
 * Objeto de transferencia de datos para la capa de negocio.
 * Transporta la informacion necesaria para agendar una cita.
 *
 * @author devor
 */
public class DTO_Negocios {

    private int idEspecialista;
    private int idPaciente;
    private String motivo;
    private LocalDateTime fechaHora;
    private double costoConsulta;

    public DTO_Negocios() {
    }

    public DTO_Negocios(int idEspecialista, int idPaciente, String motivo,
                        LocalDateTime fechaHora, double costoConsulta) {
        this.idEspecialista = idEspecialista;
        this.idPaciente = idPaciente;
        this.motivo = motivo;
        this.fechaHora = fechaHora;
        this.costoConsulta = costoConsulta;
    }

    public int getIdEspecialista() {
        return idEspecialista;
    }

    public void setIdEspecialista(int idEspecialista) {
        this.idEspecialista = idEspecialista;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getCostoConsulta() {
        return costoConsulta;
    }

    public void setCostoConsulta(double costoConsulta) {
        this.costoConsulta = costoConsulta;
    }
}
