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
public class Direcciones {

    private String idDireccion;
    private String entidadFederativa;
    private String municipio;
    private String localidad;
    private String calleYNumeroExterno;
    private String calleYNumeroInterno;
    private String referencia;
    private GeoPoint coordenadas;
    private String codigoPostal;

    public Direcciones() {
    }

    public Direcciones(String idDireccion, String entidadFederativa, String municipio, String localidad, String calleYNumeroExterno, String calleYNumeroInterno, String referencia, GeoPoint coordenadas, String codigoPostal) {
        this.idDireccion = idDireccion;
        this.entidadFederativa = entidadFederativa;
        this.municipio = municipio;
        this.localidad = localidad;
        this.calleYNumeroExterno = calleYNumeroExterno;
        this.calleYNumeroInterno = calleYNumeroInterno;
        this.referencia = referencia;
        this.coordenadas = coordenadas;
        this.codigoPostal = codigoPostal;
    }

    public String getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(String idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getEntidadFederativa() {
        return entidadFederativa;
    }

    public void setEntidadFederativa(String entidadFederativa) {
        this.entidadFederativa = entidadFederativa;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalleYNumeroExterno() {
        return calleYNumeroExterno;
    }

    public void setCalleYNumeroExterno(String calleYNumeroExterno) {
        this.calleYNumeroExterno = calleYNumeroExterno;
    }

    public String getCalleYNumeroInterno() {
        return calleYNumeroInterno;
    }

    public void setCalleYNumeroInterno(String calleYNumeroInterno) {
        this.calleYNumeroInterno = calleYNumeroInterno;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public GeoPoint getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(GeoPoint coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

}
