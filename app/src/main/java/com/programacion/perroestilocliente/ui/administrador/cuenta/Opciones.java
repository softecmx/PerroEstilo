package com.programacion.perroestilocliente.ui.administrador.cuenta;

public class Opciones {
    String info;
    String ayuda;
    String app;
    String politicas;
    String ajustes;
    String cerrar;

    public Opciones() {
    }

    public Opciones(String info, String ayuda, String app, String politicas, String ajustes, String cerrar) {
        this.info = info;
        this.ayuda = ayuda;
        this.app = app;
        this.politicas = politicas;
        this.ajustes = ajustes;
        this.cerrar = cerrar;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAyuda() {
        return ayuda;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getPoliticas() {
        return politicas;
    }

    public void setPoliticas(String politicas) {
        this.politicas = politicas;
    }

    public String getAjustes() {
        return ajustes;
    }

    public void setAjustes(String ajustes) {
        this.ajustes = ajustes;
    }

    public String getCerrar() {
        return cerrar;
    }

    public void setCerrar(String cerrar) {
        this.cerrar = cerrar;
    }
}
