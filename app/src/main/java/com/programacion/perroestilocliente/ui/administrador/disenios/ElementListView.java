package com.programacion.perroestilocliente.ui.administrador.disenios;


public class ElementListView {


    private String idModelo;
    private String disenio;
    private String estadoLogico;
    private String imagen;

    public ElementListView(String idModelo, String disenio, String estadoLogico, String imagen) {
        this.idModelo = idModelo;
        this.disenio = disenio;
        this.estadoLogico = estadoLogico;
        this.imagen = imagen;
    }

    public String getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(String idModelo) {
        this.idModelo = idModelo;
    }

    public String getDisenio() {
        return disenio;
    }

    public void setDisenio(String disenio) {
        this.disenio = disenio;
    }

    public String getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(String estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String toString(){
        return getIdModelo();
    }
}
