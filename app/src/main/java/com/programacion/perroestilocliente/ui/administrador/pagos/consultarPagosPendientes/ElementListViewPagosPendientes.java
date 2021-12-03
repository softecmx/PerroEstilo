package com.programacion.perroestilocliente.ui.administrador.pagos.consultarPagosPendientes;

public class ElementListViewPagosPendientes {

    private String ordenPedido;
    private String statusPedido;
    private String totalPedido;
    private String idusuario;
    private String producto;
    private String unitario;
    private String cantidad;
    private String subtotal;
    private String imgProducto;

    public ElementListViewPagosPendientes() {
    }

    public ElementListViewPagosPendientes(String ordenPedido, String statusPedido, String totalPedido, String idusuario,
                                          String producto, String unitario, String cantidad, String subtotal, String imgProducto) {
        this.ordenPedido = ordenPedido;
        this.statusPedido = statusPedido;
        this.totalPedido = totalPedido;
        this.idusuario = idusuario;
        this.producto = producto;
        this.unitario = unitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.imgProducto = imgProducto;
    }

    public ElementListViewPagosPendientes(String ordenPedido, String statusPedido, String totalPedido, String idusuario) {
        this.ordenPedido = ordenPedido;
        this.statusPedido = statusPedido;
        this.totalPedido = totalPedido;
        this.idusuario = idusuario;
    }

    public ElementListViewPagosPendientes(String producto, String unitario, String cantidad, String subtotal, String imgProducto) {
        this.producto = producto;
        this.unitario = unitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.imgProducto = imgProducto;
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
