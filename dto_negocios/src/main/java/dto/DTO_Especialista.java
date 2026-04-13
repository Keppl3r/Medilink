/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;

/**
 * Objeto de transferencia de datos para la capa de negocio.
 * Transporta la informacion necesaria del Doctor Especialista para agendar una cita.
 * @author Dylan
 */
public class DTO_Especialista implements Serializable {
    private int id;

    public DTO_Especialista() {}

    public DTO_Especialista(int id) {
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}
