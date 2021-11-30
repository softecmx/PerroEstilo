package com.programacion.perroestilocliente.ui.administrador.catalogo;

public class ElementListView {
    String id,nombre,tipo,estado,desc;

    public ElementListView(String id, String nombre, String tipo, String estado, String desc) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String toString(){
        return getNombre();
    }
}
