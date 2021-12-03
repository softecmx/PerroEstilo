package com.programacion.perroestilocliente.ui.administrador.inicio;

import android.widget.ImageView;

public class ElementListViewInicioAdmin {

    private String producto;
    private String stock;
    private String usuario;
    private String imgUsuario;
    private String imgProducto;
    private String cantidadProductos;
    private String cantidadPedidos;
    private String cantidadVentas;
    private String cantidadClientes;
    private String nuevosPedidos;
    private String pocosProductos;
private  String fecha;
private  String noOrden;
    public ElementListViewInicioAdmin() {
    }

    public ElementListViewInicioAdmin(String producto, String stock, String usuario, String imgUsuario,
                                      String imgProducto, String cantidadProductos, String cantidadPedidos,
                                      String cantidadVentas, String cantidadClientes, String nuevosPedidos, String pocosProductos) {
        this.producto = producto;
        this.stock = stock;
        this.usuario = usuario;
        this.imgUsuario = imgUsuario;
        this.imgProducto = imgProducto;
        this.cantidadProductos = cantidadProductos;
        this.cantidadPedidos = cantidadPedidos;
        this.cantidadVentas = cantidadVentas;
        this.cantidadClientes = cantidadClientes;
        this.nuevosPedidos = nuevosPedidos;
        this.pocosProductos = pocosProductos;
    }

    public String getNoOrden() {
        return noOrden;
    }

    public void setNoOrden(String noOrden) {
        this.noOrden = noOrden;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public ElementListViewInicioAdmin(String usuario, String imgUsuario) {
        this.usuario = usuario;
        this.imgUsuario = imgUsuario;
    }

    public ElementListViewInicioAdmin(String producto, String stock, String imgProducto) {
        this.producto = producto;
        this.stock = stock;
        this.imgProducto = imgProducto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getImgUsuario() {
        return imgUsuario;
    }

    public void setImgUsuario(String imgUsuario) {
        this.imgUsuario = imgUsuario;
    }

    public String getImgProducto() {
        return imgProducto;
    }

    public void setImgProducto(String imgProducto) {
        this.imgProducto = imgProducto;
    }

    public String getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(String cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public String getCantidadPedidos() {
        return cantidadPedidos;
    }

    public void setCantidadPedidos(String cantidadPedidos) {
        this.cantidadPedidos = cantidadPedidos;
    }

    public String getCantidadVentas() {
        return cantidadVentas;
    }

    public void setCantidadVentas(String cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
    }

    public String getCantidadClientes() {
        return cantidadClientes;
    }

    public void setCantidadClientes(String cantidadClientes) {
        this.cantidadClientes = cantidadClientes;
    }

    public String getNuevosPedidos() {
        return nuevosPedidos;
    }

    public void setNuevosPedidos(String nuevosPedidos) {
        this.nuevosPedidos = nuevosPedidos;
    }

    public String getPocosProductos() {
        return pocosProductos;
    }

    public void setPocosProductos(String pocosProductos) {
        this.pocosProductos = pocosProductos;
    }
}
