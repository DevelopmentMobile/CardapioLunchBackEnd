package br.edu.ifpe.pdm.cardapiolanches.bean;

/**
 * Created by Richardson on 30/04/2015.
 */
public class Pedido {

    private Integer _ID ;
    private Integer TEMPO_TOTAL_PEDIDO ;
    private Integer NUM_MESA;
    private Integer FUNCIONARIO_ID;

    public Integer get_ID() {
        return _ID;
    }

    public void set_ID(Integer _ID) {
        this._ID = _ID;
    }

    public Integer getTEMPO_TOTAL_PEDIDO() {
        return TEMPO_TOTAL_PEDIDO;
    }

    public void setTEMPO_TOTAL_PEDIDO(Integer TEMPO_TOTAL_PEDIDO) {
        this.TEMPO_TOTAL_PEDIDO = TEMPO_TOTAL_PEDIDO;
    }

    public Integer getNUM_MESA() {
        return NUM_MESA;
    }

    public void setNUM_MESA(Integer NUM_MESA) {
        this.NUM_MESA = NUM_MESA;
    }

    public Integer getFUNCIONARIO_ID() {
        return FUNCIONARIO_ID;
    }

    public void setFUNCIONARIO_ID(Integer FUNCIONARIO_ID) {
        this.FUNCIONARIO_ID = FUNCIONARIO_ID;
    }
}
