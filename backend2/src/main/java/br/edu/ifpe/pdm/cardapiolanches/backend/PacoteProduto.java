package br.edu.ifpe.pdm.cardapiolanches.backend;



/**
 * Created by Richardson on 28/05/2015.
 */
public class PacoteProduto {


    private Integer _ID;
    private Integer PRODUTO_ID;
    private Integer PACOTE_ID;

    public PacoteProduto(Integer _ID, Integer PRODUTO_ID, Integer PACOTE_ID) {
        this._ID = _ID;
        this.PRODUTO_ID = PRODUTO_ID;
        this.PACOTE_ID = PACOTE_ID;
    }

    public PacoteProduto(Integer PRODUTO_ID, Integer PACOTE_ID) {
        this.PRODUTO_ID = PRODUTO_ID;
        this.PACOTE_ID = PACOTE_ID;
    }

    public Integer get_ID() {

        return _ID;
    }

    public void set_ID(Integer _ID) {
        this._ID = _ID;
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
}


