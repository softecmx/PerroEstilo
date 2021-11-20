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
public class Galerias {

    private String idGaleria;
    private String nombreImagen;

    public Galerias() {
    }

    public Galerias(String idGaleria, String nombreImagen) {
        this.idGaleria = idGaleria;
        this.nombreImagen = nombreImagen;
    }

    public String getIdGaleria() {
        return idGaleria;
    }

    public void setIdGaleria(String idGaleria) {
        this.idGaleria = idGaleria;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

}
