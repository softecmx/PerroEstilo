package com.programacion.perroestilocliente.ui.administrador.tallas;

public class ElementListView {


    private String talla, medidas,id,status;


    public ElementListView(String talla, String medidas, String id, String status) {
        this.talla = talla;
        this.medidas = medidas;
        this.id = id;
        this.status = status;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String toString(){
        return getTalla();
    }
}
