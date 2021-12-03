package com.programacion.perroestilocliente.ui.administrador.envios.verEnvios;

public class ElementListViewVerEnvios {


    private String fecha;
    private String ordenPedido;
    private String statusPedido;
    private String totalPedido;
    private String idusuario;
    private String producto;
    private String unitario;
    private String cantidad;
    private String subtotal;
    private String imgProducto;


    public ElementListViewVerEnvios() {
    }

    public ElementListViewVerEnvios(String ordenPedido, String statusPedido, String totalPedido, String fecha) {
        this.ordenPedido = ordenPedido;
        this.statusPedido = statusPedido;
        this.totalPedido = totalPedido;
        this.fecha = fecha;
    }

    public ElementListViewVerEnvios(String producto, String unitario, String cantidad, String subtotal, String imgProducto) {
        this.producto = producto;
        this.unitario = unitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.imgProducto = imgProducto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getOrdenPedido() {
        return ordenPedido;
    }

    public void setOrdenPedido(String ordenPedido) {
        this.ordenPedido = ordenPedido;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public String getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(String totalPedido) {
        this.totalPedido = totalPedido;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getUnitario() {
        return unitario;
    }

    public void setUnitario(String unitario) {
        this.unitario = unitario;
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

    public String getImgProducto() {
        return imgProducto;
    }

    public void setImgProducto(String imgProducto) {
        this.imgProducto = imgProducto;
    }
}
