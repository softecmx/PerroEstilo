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
public class Productos {

    private String idProducto;
    private String claveProducto;
    private String idCategoria;
    private String disenio;
    private String talla;
    private String precioVenta;
    private String costo;
    private String descuento;
    private String fechaCreacion;
    private String raiting;
    private String estatusStock;
    private String stock;
    private String estadoLogico;
    private String imgFoto;
    private List<Galerias> lstGalerias;

    public Productos(String idProducto, String claveProducto, String idCategoria, String disenio, String talla, String precioVenta, String costo, String descuento, String fechaCreacion, String raiting, String estatusStock, String stock, String estadoLogico, String imgFoto, List<Galerias> lstGalerias) {
        this.idProducto = idProducto;
        this.claveProducto = claveProducto;
        this.idCategoria = idCategoria;
        this.disenio = disenio;
        this.talla = talla;
        this.precioVenta = precioVenta;
        this.costo = costo;
        this.descuento = descuento;
        this.fechaCreacion = fechaCreacion;
        this.raiting = raiting;
        this.estatusStock = estatusStock;
        this.stock = stock;
        this.estadoLogico = estadoLogico;
        this.imgFoto = imgFoto;
        this.lstGalerias = lstGalerias;
    }

    public String getRaiting() {
        return raiting;
    }

    public void setRaiting(String raiting) {
        this.raiting = raiting;
    }

    public String getEstatusStock() {
        return estatusStock;
    }

    public void setEstatusStock(String estatusStock) {
        this.estatusStock = estatusStock;
    }

    public String getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(String estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public String getImgFoto() {
        return imgFoto;
    }

    public void setImgFoto(String imgFoto) {
        this.imgFoto = imgFoto;
    }

    public List<Galerias> getLstGalerias() {
        return lstGalerias;
    }

    public void setLstGalerias(List<Galerias> lstGalerias) {
        this.lstGalerias = lstGalerias;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getClaveProducto() {
        return claveProducto;
    }

    public void setClaveProducto(String claveProducto) {
        this.claveProducto = claveProducto;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDisenio() {
        return disenio;
    }

    public void setDisenio(String disenio) {
        this.disenio = disenio;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
