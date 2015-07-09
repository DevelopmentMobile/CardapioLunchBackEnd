package br.edu.ifpe.pdm.cardapiolanches.dao;

import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;

/**
 * Created by Richardson on 05/07/2015.
 */
public interface PedidoListenerProdutos {

    public void  loadProdutos(List<Produto> produto);
}
