/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.persistencia.entidades;

import java.time.LocalDateTime;

/**
 * Entidad que representa una cita medica en el sistema.
 *
 * @author dylan
 */
public class Cita {

    private int id;
    private Especialista especialista;
    private Paciente paciente;
    private LocalDateTime fechaHora;
    private String motivo;
    private EstadoCita estado;
    private double costoConsulta;

    public Cita(int id, Especialista especialista, Paciente paciente,
                LocalDateTime fechaHora, String motivo, double costoConsulta) {
        this.id = id;
        this.especialista = especialista;
        this.paciente = paciente;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.costoConsulta = costoConsulta;
        this.estado = EstadoCita.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public double getCostoConsulta() {
        return costoConsulta;
    }

    public void setCostoConsulta(double costoConsulta) {
        this.costoConsulta = costoConsulta;
    }

    @Override
    public String toString() {
        return "Cita #" + id + " | " + especialista + " | " + paciente
                + " | " + fechaHora + " | " + estado;
    }
}
