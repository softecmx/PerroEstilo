package com.programacion.perroestilocliente.ui.administrador.inventario;

public class ElementListViewInventario {
    private String codigo,producto,modelo,talla,status,stock,img;

    public ElementListViewInventario() {
    }

    public ElementListViewInventario(String codigo, String producto, String modelo, String talla, String status, String stock,String img) {
        this.codigo = codigo;
        this.producto = producto;
        this.modelo = modelo;
        this.talla = talla;
        this.status = status;
        this.stock = stock;
        this.img=img;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getModelo() {
        return modelo;
    }

    public void setMtodelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
