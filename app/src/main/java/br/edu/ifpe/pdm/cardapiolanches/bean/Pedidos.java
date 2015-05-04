package br.edu.ifpe.pdm.cardapiolanches.bean;

import java.util.Date;

/**
 * Created by Richardson on 30/04/2015.
 */
public class Pedidos {


    private int _ID;
    private Date DT_PEDIDO ;
    private Date DT_ENTREGA ;
    private String STATUS_PEDIDO  ;


    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getSTATUS_PEDIDO() {
        return STATUS_PEDIDO;
    }

    public void setSTATUS_PEDIDO(String STATUS_PEDIDO) {
        this.STATUS_PEDIDO = STATUS_PEDIDO;
    }

    public Date getDT_ENTREGA() {
        return DT_ENTREGA;
    }

    public void setDT_ENTREGA(Date DT_ENTREGA) {
        this.DT_ENTREGA = DT_ENTREGA;
    }

    public Date getDT_PEDIDO() {
        return DT_PEDIDO;
    }

    public void setDT_PEDIDO(Date DT_PEDIDO) {
        this.DT_PEDIDO = DT_PEDIDO;
    }


}
