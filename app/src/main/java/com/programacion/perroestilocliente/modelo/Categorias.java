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
public class Categorias {

    private String idCategorias;
    private String nombreCategoria;
    private String descripcion;
    private String estadoLogico;
    private String tipoPublico;


    public Categorias() {
    }

    public Categorias(String idCategorias, String nombreCategoria, String descripcion, String estadoLogico, String tipoPublico) {
        this.idCategorias = idCategorias;
        this.nombreCategoria = nombreCategoria;
        this.descripcion = descripcion;
        this.estadoLogico = estadoLogico;
        this.tipoPublico = tipoPublico;
    }

    public String getTipoPublico() {
        return tipoPublico;
    }

    public void setTipoPublico(String tipoPublico) {
        this.tipoPublico = tipoPublico;
    }

    public String getIdCategorias() {
        return idCategorias;
    }

    public void setIdCategorias(String idCategorias) {
        this.idCategorias = idCategorias;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(String estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

}
