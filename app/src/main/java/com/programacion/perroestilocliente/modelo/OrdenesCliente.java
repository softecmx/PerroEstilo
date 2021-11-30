/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.programacion.perroestilocliente.modelo;

import java.util.List;

/**
 *
 * @author por_s
 */
public class OrdenesCliente extends Direcciones{

    private String inOrden;
    private Direcciones direccionEnvio;
    private String estatusOrden;
    private String fechaOrden;
    private String noSerie;
    private String noConfirmacion;
    private String fechaEntrega;
    private String nombreContacto;
    private String apPContacto;
    private String apMContacto;
    private String telefonoContacto;
    private List<DetOrdenKit> lstOrdenesKit;
    private List<DetOrdenProductos> lstOrdenesProductos;
    private String idCliente;


    public OrdenesCliente() {
    }

    public OrdenesCliente(String inOrden, Direcciones direccionEnvio, String estatusOrden, String fechaOrden, String noSerie, String noConfirmacion, String fechaEntrega, String nombreContacto, String apPContacto, String apMContacto, String telefonoContacto, List<DetOrdenKit> lstOrdenesKit, List<DetOrdenProductos> lstOrdenesProductos, String idCliente) {
        this.inOrden = inOrden;
        this.direccionEnvio = direccionEnvio;
        this.estatusOrden = estatusOrden;
        this.fechaOrden = fechaOrden;
        this.noSerie = noSerie;
        this.noConfirmacion = noConfirmacion;
        this.fechaEntrega = fechaEntrega;
        this.nombreContacto = nombreContacto;
        this.apPContacto = apPContacto;
        this.apMContacto = apMContacto;
        this.telefonoContacto = telefonoContacto;
        this.lstOrdenesKit = lstOrdenesKit;
        this.lstOrdenesProductos = lstOrdenesProductos;
        this.idCliente = idCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getInOrden() {
        return inOrden;
    }

    public void setInOrden(String inOrden) {
        this.inOrden = inOrden;
    }

    public Direcciones getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(Direcciones direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public String getEstatusOrden() {
        return estatusOrden;
    }

    public void setEstatusOrden(String estatusOrden) {
        this.estatusOrden = estatusOrden;
    }

    public String getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(String fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getNoSerie() {
        return noSerie;
    }

    public void setNoSerie(String noSerie) {
        this.noSerie = noSerie;
    }

    public String getNoConfirmacion() {
        return noConfirmacion;
    }

    public void setNoConfirmacion(String noConfirmacion) {
        this.noConfirmacion = noConfirmacion;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getApPContacto() {
        return apPContacto;
    }

    public void setApPContacto(String apPContacto) {
        this.apPContacto = apPContacto;
    }

    public String getApMContacto() {
        return apMContacto;
    }

    public void setApMContacto(String apMContacto) {
        this.apMContacto = apMContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public List<DetOrdenKit> getLstOrdenesKit() {
        return lstOrdenesKit;
    }

    public void setLstOrdenesKit(List<DetOrdenKit> lstOrdenesKit) {
        this.lstOrdenesKit = lstOrdenesKit;
    }

    public List<DetOrdenProductos> getLstOrdenesProductos() {
        return lstOrdenesProductos;
    }

    public void setLstOrdenesProductos(List<DetOrdenProductos> lstOrdenesProductos) {
        this.lstOrdenesProductos = lstOrdenesProductos;
    }

}
