/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.programacion.perroestilocliente.modelo;

/**
 *
 * @author por_s
 */
public class Usuarios {

    private String idUsuario;
    private String username;
    private String password;
    private String email;
    private String fotoPerfil;
    private String estatus;
    private String estadoLogico;
    private String intentosFallidosAcceso;
    private String telefono;
    private String tipoUsuario;

    public Usuarios() {
    }

    public Usuarios(String idUsuario, String username, String password, String email, String fotoPerfil, String estatus, String estadoLogico, String intentosFallidosAcceso, String telefono, String tipoUsuario) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fotoPerfil = fotoPerfil;
        this.estatus = estatus;
        this.estadoLogico = estadoLogico;
        this.intentosFallidosAcceso = intentosFallidosAcceso;
        this.telefono = telefono;
        this.tipoUsuario = tipoUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(String estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public String getIntentosFallidosAcceso() {
        return intentosFallidosAcceso;
    }

    public void setIntentosFallidosAcceso(String intentosFallidosAcceso) {
        this.intentosFallidosAcceso = intentosFallidosAcceso;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
