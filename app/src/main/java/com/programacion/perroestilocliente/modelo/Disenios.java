/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.programacion.perroestilocliente.modelo;

import java.util.List;

/**
 *
 * @author por_s
 */
public class Disenios {

    private String idModelo;
    private String disenio;
    private String estadoLogico;
    private String imagen;
    private List<Etiquetas> lstEtiquetas;

    public Disenios() {
    }


    public Disenios(String idModelo, String disenio, String estadoLogico, String imagen, List<Etiquetas> lstEtiquetas) {
        this.idModelo = idModelo;
        this.disenio = disenio;
        this.estadoLogico = estadoLogico;
        this.imagen = imagen;
        this.lstEtiquetas = lstEtiquetas;
    }

    public List<Etiquetas> getLstEtiquetas() {
        return lstEtiquetas;
    }

    public void setLstEtiquetas(List<Etiquetas> lstEtiquetas) {
        this.lstEtiquetas = lstEtiquetas;
    }

    public String getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(String idModelo) {
        this.idModelo = idModelo;
    }

    public String getDisenio() {
        return disenio;
    }

    public void setDisenio(String disenio) {
        this.disenio = disenio;
    }

    public String getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(String estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
