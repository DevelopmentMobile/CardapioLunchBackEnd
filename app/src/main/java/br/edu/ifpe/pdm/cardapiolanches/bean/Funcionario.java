package br.edu.ifpe.pdm.cardapiolanches.bean;

/**
 * Created by Richardson on 28/05/2015.
 */
public class Funcionario {

    private Integer _ID ;
    private String LOGIN ;
    private String SENHA ;
    private Integer TIPO_FUNCIONARIO ;

    public Integer get_ID() {
        return _ID;
    }

    public void set_ID(Integer _ID) {
        this._ID = _ID;
    }

    public String getLOGIN() {
        return LOGIN;
    }

    public void setLOGIN(String LOGIN) {
        this.LOGIN = LOGIN;
    }

    public String getSENHA() {
        return SENHA;
    }

    public void setSENHA(String SENHA) {
        this.SENHA = SENHA;
    }

    public Integer getTIPO_FUNCIONARIO() {
        return TIPO_FUNCIONARIO;
    }

    public void setTIPO_FUNCIONARIO(Integer TIPO_FUNCIONARIO) {
        this.TIPO_FUNCIONARIO = TIPO_FUNCIONARIO;
    }
}
