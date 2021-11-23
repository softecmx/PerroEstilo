package com.programacion.perroestilocliente.ui.administrador.tallas;

public class ElementListView {


    private String talla, medidas;

    public ElementListView(String talla, String medidas) {
        this.talla = talla;
        this.medidas = medidas;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }
}
