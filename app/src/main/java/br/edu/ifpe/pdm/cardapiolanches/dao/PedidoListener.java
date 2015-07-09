package br.edu.ifpe.pdm.cardapiolanches.dao;

import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.bean.Pedido;

/**
 * Created by Richardson on 05/07/2015.
 */
public interface PedidoListener {

    public void showPedido(List<Pedido> Pedidos);
}
