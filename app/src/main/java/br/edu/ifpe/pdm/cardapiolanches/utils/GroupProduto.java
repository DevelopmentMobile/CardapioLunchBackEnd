package br.edu.ifpe.pdm.cardapiolanches.utils;

/**
 * Created by Richardson on 19/05/2015.
 */
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;

public class GroupProduto {

    public String string;
    public final List<Produto> children = new ArrayList<Produto>();

    public GroupProduto(String string) {
        this.string = string;
    }

}