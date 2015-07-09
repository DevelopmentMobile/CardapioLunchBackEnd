package br.edu.ifpe.pdm.cardapiolanches.backend;

/**
 * Created by Richardson on 30/04/2015.
 */
public class Pedido {


   private Integer _ID ;
   private Integer TEMPO_TOTAL_PEDIDO ;
   private Integer QUANTIDADE ;
   private Integer NUM_MESA  = 1;

    public Pedido(Integer TEMPO_TOTAL_PEDIDO, Integer QUANTIDADE, Integer NUM_MESA, Integer FUNCIONARIO_ID, Integer PRODUTO_ID, Integer PACOTE_ID, Integer STATUS_PEDIDO, Integer NUM_PEDIDO) {
        this.TEMPO_TOTAL_PEDIDO = TEMPO_TOTAL_PEDIDO;
        this.QUANTIDADE = QUANTIDADE;
        this.NUM_MESA = NUM_MESA;
        this.FUNCIONARIO_ID = FUNCIONARIO_ID;
        this.PRODUTO_ID = PRODUTO_ID;
        this.PACOTE_ID = PACOTE_ID;
        this.STATUS_PEDIDO = STATUS_PEDIDO;
        this.NUM_PEDIDO = NUM_PEDIDO;
    }

    private Integer FUNCIONARIO_ID;
   private Integer PRODUTO_ID;
   private Integer PACOTE_ID ;
   private Integer STATUS_PEDIDO;
    private Integer NUM_PEDIDO;

    private String ACAO;

    public Pedido() {

    }

    public String getACAO() {

        return ACAO;
    }

    public void setACAO(String ACAO) {
        this.ACAO = ACAO;
    }

    public Integer getNUM_PEDIDO() {
        return NUM_PEDIDO;
    }

    public void setNUM_PEDIDO(Integer NUM_PEDIDO) {
        this.NUM_PEDIDO = NUM_PEDIDO;
    }


    public Pedido(Integer _ID, Integer TEMPO_TOTAL_PEDIDO, Integer QUANTIDADE, Integer NUM_MESA, Integer FUNCIONARIO_ID, Integer PRODUTO_ID, Integer PACOTE_ID, Integer STATUS_PEDIDO, Integer NUM_PEDIDO) {
        this._ID = _ID;
        this.TEMPO_TOTAL_PEDIDO = TEMPO_TOTAL_PEDIDO;
        this.QUANTIDADE = QUANTIDADE;
        this.NUM_MESA = NUM_MESA;
        this.FUNCIONARIO_ID = FUNCIONARIO_ID;
        this.PRODUTO_ID = PRODUTO_ID;
        this.PACOTE_ID = PACOTE_ID;
        this.STATUS_PEDIDO = STATUS_PEDIDO;
        this.NUM_PEDIDO = NUM_PEDIDO;
    }


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

    public Integer getQUANTIDADE() {
        return QUANTIDADE;
    }

    public void setQUANTIDADE(Integer QUANTIDADE) {
        this.QUANTIDADE = QUANTIDADE;
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
