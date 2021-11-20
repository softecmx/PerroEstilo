/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.programacion.perroestilocliente.modelo;

/**
 *
 * @author por_s
 */
public class Tallas {

    private String idTalla;
    private String tallas;
    private String estadoLogico;
    private String medidas;

    public Tallas(String idTalla, String tallas, String estadoLogico, String medidas) {
        this.idTalla = idTalla;
        this.tallas = tallas;
        this.estadoLogico = estadoLogico;
        this.medidas = medidas;
    }

    public Tallas() {
    }

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public String getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(String estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public String getTallas() {
        return tallas;
    }

    public void setTallas(String tallas) {
        this.tallas = tallas;
    }

    public String getIdTalla() {
        return idTalla;
    }

    public void setIdTalla(String idTalla) {
        this.idTalla = idTalla;
    }

}
