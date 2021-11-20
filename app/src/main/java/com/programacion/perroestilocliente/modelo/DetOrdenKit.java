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
public class DetOrdenKit {

    private String idDetOrdenKit;
    private String cantidad;
    private String subtotal;
    private String personalizado;
    private List<DetOrdenProductosKit> lstProductosKit;

    public DetOrdenKit() {
    }

    public DetOrdenKit(String idDetOrdenKit, String cantidad, String subtotal, String personalizado, List<DetOrdenProductosKit> lstProductosKit) {
        this.idDetOrdenKit = idDetOrdenKit;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.personalizado = personalizado;
        this.lstProductosKit = lstProductosKit;
    }

    public List<DetOrdenProductosKit> getLstProductosKit() {
        return lstProductosKit;
    }

    public void setLstProductosKit(List<DetOrdenProductosKit> lstProductosKit) {
        this.lstProductosKit = lstProductosKit;
    }

    public DetOrdenKit(String idDetOrdenKit, String cantidad, String subtotal, String personalizado) {
        this.idDetOrdenKit = idDetOrdenKit;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.personalizado = personalizado;
    }

    public String getIdDetOrdenKit() {
        return idDetOrdenKit;
    }

    public void setIdDetOrdenKit(String idDetOrdenKit) {
        this.idDetOrdenKit = idDetOrdenKit;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getPersonalizado() {
        return personalizado;
    }

    public void setPersonalizado(String personalizado) {
        this.personalizado = personalizado;
    }

}
