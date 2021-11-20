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
public class Etiquetas {

    private String idEtiqueta;
    private String etiqueta;

    public Etiquetas() {
    }

    public Etiquetas(String idEtiqueta, String etiqueta) {
        this.idEtiqueta = idEtiqueta;
        this.etiqueta = etiqueta;
    }

    public String getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(String idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

}
