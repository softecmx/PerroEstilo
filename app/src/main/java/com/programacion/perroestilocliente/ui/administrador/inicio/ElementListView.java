package com.programacion.perroestilocliente.ui.administrador.inicio;

import android.widget.ImageView;

public class ElementListView {

    private String descripcion;
    private ImageView img;

    public ElementListView(String descripcion, ImageView img) {
        this.descripcion = descripcion;
        this.img = img;
    }

    public ImageView getImg() { return img; }

    public void setImg(ImageView img) { this.img = img; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
