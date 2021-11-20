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
public class DetallesKit {

    private String idDetalleKit;
    private String precio;
    private String disenio;
    private Productos producto;
    private List<Tallas> lstTallasDisponibles;

    public DetallesKit() {
    }

    public DetallesKit(String idDetalleKit, String precio, String disenio, Productos producto, List<Tallas> lstTallasDisponibles) {
        this.idDetalleKit = idDetalleKit;
        this.precio = precio;
        this.disenio = disenio;
        this.producto = producto;
        this.lstTallasDisponibles = lstTallasDisponibles;
    }

    public List<Tallas> getLstTallasDisponibles() {
        return lstTallasDisponibles;
    }

    public void setLstTallasDisponibles(List<Tallas> lstTallasDisponibles) {
        this.lstTallasDisponibles = lstTallasDisponibles;
    }

    public String getIdDetalleKit() {
        return idDetalleKit;
    }

    public void setIdDetalleKit(String idDetalleKit) {
        this.idDetalleKit = idDetalleKit;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDisenio() {
        return disenio;
    }

    public void setDisenio(String disenio) {
        this.disenio = disenio;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

}
