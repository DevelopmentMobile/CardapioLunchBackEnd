package br.edu.ifpe.pdm.cardapiolanches.bean;

/**
 * Created by Richardson on 04/05/2015.
 */
public class Produtos {

    private String _ID ;
    private String NOME_PRODUTO ;
    private double PRECO ;
    private String CATEGORIA ;
    private double MEDIDA;


    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String getNOME_PRODUTO() {
        return NOME_PRODUTO;
    }

    public void setNOME_PRODUTO(String NOME_PRODUTO) {
        this.NOME_PRODUTO = NOME_PRODUTO;
    }

    public String getCATEGORIA() {
        return CATEGORIA;
    }

    public void setCATEGORIA(String CATEGORIA) {
        this.CATEGORIA = CATEGORIA;
    }

    public double getPRECO() {
        return PRECO;
    }

    public void setPRECO(double PRECO) {
        this.PRECO = PRECO;
    }

    public double getMEDIDA() {
        return MEDIDA;
    }

    public void setMEDIDA(double MEDIDA) {
        this.MEDIDA = MEDIDA;
    }
}
