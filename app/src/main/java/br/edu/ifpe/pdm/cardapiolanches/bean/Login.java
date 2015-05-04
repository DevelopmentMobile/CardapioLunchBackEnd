package br.edu.ifpe.pdm.cardapiolanches.bean;

/**
 * Created by Richardson on 30/04/2015.
 */
public class Login {


    private int _ID ;
    private String LOGIN ;
    private String NOME;
    private String FONE;

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public String getFONE() {
        return FONE;
    }

    public void setFONE(String FONE) {
        this.FONE = FONE;
    }

    public String getLOGIN() {
        return LOGIN;
    }

    public void setLOGIN(String LOGIN) {
        this.LOGIN = LOGIN;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }
}
