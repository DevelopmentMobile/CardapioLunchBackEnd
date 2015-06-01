package br.edu.ifpe.pdm.cardapiolanches.view.cliente;

/**
 * Created by Richardson on 19/05/2015.
 */
import java.util.ArrayList;
import java.util.List;

public class Group {

    public String string;
    public final List<String> children = new ArrayList<String>();

    public Group(String string) {
        this.string = string;
    }

}