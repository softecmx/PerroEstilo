package com.programacion.perroestilocliente.ui.cliente.comprar;

import com.programacion.perroestilocliente.modelo.GeoPoint;

public class ElementListView {

    private String idDireccion;
    private String entidadFederativa;
    private String municipio;
    private String localidad;
    private String calleYNumeroExterno;
    private String calleYNumeroInterno;
    private String referencia;
    private String codigoPostal;

    public ElementListView(String idDireccion, String entidadFederativa, String municipio, String localidad, String calleYNumeroExterno, String calleYNumeroInterno, String referencia, String codigoPostal) {
        this.idDireccion = idDireccion;
        this.entidadFederativa = entidadFederativa;
        this.municipio = municipio;
        this.localidad = localidad;
        this.calleYNumeroExterno = calleYNumeroExterno;
        this.calleYNumeroInterno = calleYNumeroInterno;
        this.referencia = referencia;
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

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String toString(){
        return getEntidadFederativa()+", "+getMunicipio()+", "+getLocalidad()+", "+getCalleYNumeroExterno();
    }
}
