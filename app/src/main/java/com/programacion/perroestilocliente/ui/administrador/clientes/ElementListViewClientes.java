package com.programacion.perroestilocliente.ui.administrador.clientes;

public class ElementListViewClientes {


    private String id;
    private String status;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String foto;

    public ElementListViewClientes() {
    }

    public ElementListViewClientes(String id, String status, String nombre, String apellido, String email, String telefono, String foto) {
        this.id = id;
        this.status = status;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.foto = foto;
    }

    public ElementListViewClientes(String status, String nombre, String foto) {
        this.status = status;
        this.nombre = nombre;
        this.foto = foto;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
