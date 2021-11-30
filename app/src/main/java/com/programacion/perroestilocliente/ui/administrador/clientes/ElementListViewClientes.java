package com.programacion.perroestilocliente.ui.administrador.clientes;

public class ElementListViewClientes {


    private String id,status,nombre,apellido;


    public ElementListViewClientes(String id, String status, String nombre, String apellido) {
        this.id = id;
        this.status = status;
        this.nombre= nombre;
        this.apellido= apellido;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
