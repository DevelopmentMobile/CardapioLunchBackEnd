package br.edu.ifpe.pdm.cardapiolanches.bean;

/**
 * Created by Richardson on 04/05/2015.
 */
public class PedidoItem {
     private Integer _ID;
    private Integer PEDIDO_ID;
    private Integer PRODUTO_ID ;
    private Integer PACOTE_ID;
    private Integer STATUS_PEDIDO;

    public Integer get_ID() {
        return _ID;
    }

    public void set_ID(Integer _ID) {
        this._ID = _ID;
    }

    public Integer getPEDIDO_ID() {
        return PEDIDO_ID;
    }

    public void setPEDIDO_ID(Integer PEDIDO_ID) {
        this.PEDIDO_ID = PEDIDO_ID;
    }

    public Integer getPRODUTO_ID() {
        return PRODUTO_ID;
    }

    public void setPRODUTO_ID(Integer PRODUTO_ID) {
        this.PRODUTO_ID = PRODUTO_ID;
    }

    public Integer getPACOTE_ID() {
        return PACOTE_ID;
    }

    public void setPACOTE_ID(Integer PACOTE_ID) {
        this.PACOTE_ID = PACOTE_ID;
    }

    public Integer getSTATUS_PEDIDO() {
        return STATUS_PEDIDO;
    }

    public void setSTATUS_PEDIDO(Integer STATUS_PEDIDO) {
        this.STATUS_PEDIDO = STATUS_PEDIDO;
    }
}
