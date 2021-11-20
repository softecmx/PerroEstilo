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
public class DetOrdenProductosKit {

    private String idDetProductoKit;
    private String precio;
    private String medidas;
    private Productos producto;
    private Disenios disenio;
    private String talla;

    public DetOrdenProductosKit() {
    }

    public DetOrdenProductosKit(String idDetProductoKit, String precio, String medidas, Productos producto, Disenios disenio, String talla) {
        this.idDetProductoKit = idDetProductoKit;
        this.precio = precio;
        this.medidas = medidas;
        this.producto = producto;
        this.disenio = disenio;
        this.talla = talla;
    }

    public String getIdDetProductoKit() {
        return idDetProductoKit;
    }

    public void setIdDetProductoKit(String idDetProductoKit) {
        this.idDetProductoKit = idDetProductoKit;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public Disenios getDisenio() {
        return disenio;
    }

    public void setDisenio(Disenios disenio) {
        this.disenio = disenio;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

}
