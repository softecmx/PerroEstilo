package com.programacion.perroestilocliente.ui.cliente.productos.todosLosProductos;

public class Producto {

    private int img;
    private String nombreProducto;
    private boolean oferta;
    private  boolean nuevo;
    private String precio;
    private String descuento;
    private String raiting;

    public Producto() {
    }

    public Producto(int img, String nombreProducto, boolean oferta, boolean nuevo, String precio, String descuento, String raiting) {
        this.img = img;
        this.nombreProducto = nombreProducto;
        this.oferta = oferta;
        this.nuevo = nuevo;
        this.precio = precio;
        this.descuento = descuento;
        this.raiting = raiting;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public boolean isOferta() {
        return oferta;
    }

    public void setOferta(boolean oferta) {
        this.oferta = oferta;
    }

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getRaiting() {
        return raiting;
    }

    public void setRaiting(String raiting) {
        this.raiting = raiting;
    }
}
