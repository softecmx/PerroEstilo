package com.programacion.perroestilocliente.ui.administrador.cuenta;

public class ElementListViewOpciones {
    private int Imagen;
    String opcion;


    public ElementListViewOpciones() {
    }

    public ElementListViewOpciones(int imagen, String opcion) {
        Imagen = imagen;
        this.opcion = opcion;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }
}
