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
public class DetOrdenProductos {

    private String idDetOrdenProducto;
    private String cantidad;
    private String subtotal;
    private String medidas;
    private String calificacion;
    private String modelos;
    private Productos producto;

    public DetOrdenProductos() {
    }

    public DetOrdenProductos(String idDetOrdenProducto, String cantidad, String subtotal, String medidas, String calificacion, String modelos, Productos producto) {
        this.idDetOrdenProducto = idDetOrdenProducto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.medidas = medidas;
        this.calificacion = calificacion;
        this.modelos = modelos;
        this.producto = producto;
    }

    public String getModelos() {
        return modelos;
    }

    public void setModelos(String modelos) {
        this.modelos = modelos;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public String getIdDetOrdenProducto() {
        return idDetOrdenProducto;
    }

    public void setIdDetOrdenProducto(String idDetOrdenProducto) {
        this.idDetOrdenProducto = idDetOrdenProducto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

}
