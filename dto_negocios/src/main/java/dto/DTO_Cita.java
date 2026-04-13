/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Objeto de transferencia de datos para la capa de negocio.
 * Transporta la informacion necesaria para agendar una cita.
 * @author Dylan
 */
public class DTO_Cita implements Serializable {
    private DTO_Paciente paciente;
    private DTO_Especialista especialista;
    private String motivo;
    private LocalDateTime fechaHora;
    private double costoConsulta;

    public DTO_Cita() {}

    public DTO_Cita(DTO_Paciente paciente, DTO_Especialista especialista, String motivo, 
                    LocalDateTime fechaHora, double costoConsulta) {
        this.paciente = paciente;
        this.especialista = especialista;
        this.motivo = motivo;
        this.fechaHora = fechaHora;
        this.costoConsulta = costoConsulta;
    }

    // Getters y Setters
    public DTO_Paciente getPaciente() { return paciente; }
    public void setPaciente(DTO_Paciente paciente) { this.paciente = paciente; }

    public DTO_Especialista getEspecialista() { return especialista; }
    public void setEspecialista(DTO_Especialista especialista) { this.especialista = especialista; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public double getCostoConsulta() { return costoConsulta; }
    public void setCostoConsulta(double costoConsulta) { this.costoConsulta = costoConsulta; }
}
