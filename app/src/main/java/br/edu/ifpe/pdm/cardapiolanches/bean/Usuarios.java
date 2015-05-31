package br.edu.ifpe.pdm.cardapiolanches.bean;

/**
 * Created by Richardson on 30/04/2015.
 */
public class Usuarios {


    private int _ID ;
    private String LOGIN ;
    private String NOME;
    private String FONE;
    private String SENHA;
    private int TIPO_USUARIO = 0;

    public int getTIPO_USUARIO() {
        return TIPO_USUARIO;
    }

    public void setTIPO_USUARIO(int TIPO_USUARIO) {
        this.TIPO_USUARIO = TIPO_USUARIO;
    }

    public String getSENHA() {
        return SENHA;
    }

    public void setSENHA(String SENHA) {
        this.SENHA = SENHA;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }



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


}
