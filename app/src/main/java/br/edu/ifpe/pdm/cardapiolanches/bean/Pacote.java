package br.edu.ifpe.pdm.cardapiolanches.bean;

import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.utils.Constantes;

/**
 * Created by Richardson on 28/05/2015.
 */
public class Pacote {


    private Integer _ID;
    private String NOME_PACOTE;
    private Integer TIPO_PACOTE;
    private String DESCRICAO_PACOTE;
    private Float PRECO;
    private Integer UNIDADE;
    private String NOME_IMAGE;
    private Integer PRODUTO_ID;
    private List<Produto> listProduto;

    public List<Produto> getListProduto() {
        return listProduto;
    }

    public void setListProduto(List<Produto> listProduto) {
        this.listProduto = listProduto;
    }

    public Integer getPRODUTO_ID() {
        return PRODUTO_ID;
    }

    public void setPRODUTO_ID(Integer PRODUTO_ID) {
        this.PRODUTO_ID = PRODUTO_ID;
    }

    private String ACAO ;

    public Integer getUNIDADE() {
        return UNIDADE;
    }

    public void setUNIDADE(Integer UNIDADE) {
        this.UNIDADE = UNIDADE;
    }

    public String getNOME_IMAGE() {
        return NOME_IMAGE;
    }

    public void setNOME_IMAGE(String NOME_IMAGE) {
        this.NOME_IMAGE = NOME_IMAGE;
    }

    public String getACAO() {
        return ACAO;
    }

    public void setACAO(String ACAO) {
        this.ACAO = ACAO;
    }


    public Pacote(Integer _ID, String NOME_PACOTE, Integer TIPO_PACOTE, String DESCRICAO_PACOTE, Float PRECO, Integer UNIDADE, String NOME_IMAGE) {
        this._ID = _ID;
        this.NOME_PACOTE = NOME_PACOTE;
        this.TIPO_PACOTE = TIPO_PACOTE;
        this.DESCRICAO_PACOTE = DESCRICAO_PACOTE;
        this.PRECO = PRECO;
        this.UNIDADE = UNIDADE;
        this.NOME_IMAGE = NOME_IMAGE;
    }

    public Pacote(String NOME_PACOTE, Integer TIPO_PACOTE, String DESCRICAO_PACOTE, Float PRECO, Integer UNIDADE, String NOME_IMAGE) {
        this.NOME_PACOTE = NOME_PACOTE;
        this.TIPO_PACOTE = TIPO_PACOTE;
        this.DESCRICAO_PACOTE = DESCRICAO_PACOTE;
        this.PRECO = PRECO;
        this.UNIDADE = UNIDADE;
        this.NOME_IMAGE = NOME_IMAGE;
    }

    public Pacote() {
    }

    public Float getPRECO() {
        return PRECO;
    }

    public void setPRECO(Float PRECO) {
        this.PRECO = PRECO;
    }

    public Integer get_ID() {
        return _ID;
    }

    public void set_ID(Integer _ID) {
        this._ID = _ID;
    }

    public String getNOME_PACOTE() {
        return NOME_PACOTE;
    }

    public void setNOME_PACOTE(String NOME_PACOTE) {
        this.NOME_PACOTE = NOME_PACOTE;
    }

    public Integer getTIPO_PACOTE() {
        return TIPO_PACOTE;
    }

    public void setTIPO_PACOTE(Integer TIPO_PACOTE) {
        this.TIPO_PACOTE = TIPO_PACOTE;
    }

    public String getDESCRICAO_PACOTE() {
        return DESCRICAO_PACOTE;
    }

    public void setDESCRICAO_PACOTE(String DESCRICAO_PACOTE) {
        this.DESCRICAO_PACOTE = DESCRICAO_PACOTE;
    }





    public String toString()
    {
        return(" Nome: " + getNOME_PACOTE()  + " | Tipo: " + tipPacote(getTIPO_PACOTE()) );
    }

    public String tipPacote(Integer tipo){
        String tipoTexto= "";

            if (Constantes.PACOTE_PADRAO == tipo){
                tipoTexto = "PADRÃO";
            }else  if (Constantes.PACOTE_PROMOCIONAL == tipo){
                tipoTexto = "PROMOCIONAL";
            }

        return tipoTexto;
    }

}


