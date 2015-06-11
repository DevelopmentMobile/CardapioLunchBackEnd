package br.edu.ifpe.pdm.cardapiolanches.bean;

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


