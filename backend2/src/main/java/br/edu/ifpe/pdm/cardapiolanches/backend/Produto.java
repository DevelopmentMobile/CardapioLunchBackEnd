package br.edu.ifpe.pdm.cardapiolanches.backend;

/**
 * Created by Richardson on 04/05/2015.
 */
public class Produto {

    public Produto(){

    }
    private Integer  _ID;
   private Integer  UNIDADE_ESTOQUE ;
   private String NOME;
   private Float  PRECO;
   private String DESCRICAO;
   private String NOME_IMAGEM;

   private Integer  TEMPO_PRONTO_PRODUTO;
   private String CATEGORIA;


    public Produto(Integer _ID, Integer UNIDADE_ESTOQUE, String NOME, Float PRECO, String DESCRICAO, String NOME_IMAGEM, Integer TEMPO_PRONTO_PRODUTO, String CATEGORIA) {
        this._ID = _ID;
        this.UNIDADE_ESTOQUE = UNIDADE_ESTOQUE;
        this.NOME = NOME;
        this.PRECO = PRECO;
        this.DESCRICAO = DESCRICAO;
        this.NOME_IMAGEM = NOME_IMAGEM;
        this.TEMPO_PRONTO_PRODUTO = TEMPO_PRONTO_PRODUTO;
        this.CATEGORIA = CATEGORIA;
    }

    public Produto( Integer UNIDADE_ESTOQUE, String NOME, Float PRECO, String DESCRICAO, String NOME_IMAGEM, Integer TEMPO_PRONTO_PRODUTO, String CATEGORIA) {

        this.UNIDADE_ESTOQUE = UNIDADE_ESTOQUE;
        this.NOME = NOME;
        this.PRECO = PRECO;
        this.DESCRICAO = DESCRICAO;
        this.NOME_IMAGEM = NOME_IMAGEM;
        this.TEMPO_PRONTO_PRODUTO = TEMPO_PRONTO_PRODUTO;
        this.CATEGORIA = CATEGORIA;
    }
    public String getCATEGORIA() {
        return CATEGORIA;
    }

    public void setCATEGORIA(String CATEGORIA) {
        this.CATEGORIA = CATEGORIA;
    }

    public Integer get_ID() {
        return _ID;
    }

    public void set_ID(Integer _ID) {
        this._ID = _ID;
    }

    public Integer getUNIDADE_ESTOQUE() {
        return UNIDADE_ESTOQUE;
    }

    public void setUNIDADE_ESTOQUE(Integer UNIDADE_ESTOQUE) {
        this.UNIDADE_ESTOQUE = UNIDADE_ESTOQUE;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public Float getPRECO() {
        return PRECO;
    }

    public void setPRECO(Float PRECO) {
        this.PRECO = PRECO;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public String getNOME_IMAGEM() {
        return NOME_IMAGEM;
    }

    public void setNOME_IMAGEM(String NOME_IMAGEM) {
        this.NOME_IMAGEM = NOME_IMAGEM;
    }

    public Integer getTEMPO_PRONTO_PRODUTO() {
        return TEMPO_PRONTO_PRODUTO;
    }

    public void setTEMPO_PRONTO_PRODUTO(Integer TEMPO_PRONTO_PRODUTO) {
        this.TEMPO_PRONTO_PRODUTO = TEMPO_PRONTO_PRODUTO;
    }

    public String toString()
    {
        return( "Nome: "+ getNOME() + " | Preço: " + getPRECO() + " | Cat: " + getCATEGORIA()  + "| Tempo: "+ getTEMPO_PRONTO_PRODUTO() + " | Unid: " + getUNIDADE_ESTOQUE() );
    }
}
