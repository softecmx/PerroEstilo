package com.programacion.perroestilocliente.ui.administrador.clientes;

public class ElementListView {


    private String id,status,nombre,foto;


    public ElementListView(String id, String status, String nombre, String foto) {
        this.id = id;
        this.status = status;
        this.nombre= nombre;
        this.foto= foto;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
