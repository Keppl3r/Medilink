/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;

/**
 * Objeto de transferencia de datos para la capa de negocio.
 * Transporta la informacion necesaria del paciente para agendar una cita.
 * @author Dylan
 */
public class DTO_Paciente implements Serializable {
    private int id;

    public DTO_Paciente() {}

    public DTO_Paciente(int id) {
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}