package br.edu.ifpe.pdm.cardapiolanches.bean;

/**
 * Created by Richardson on 04/05/2015.
 */
public class PedidosPorProdutos {

   private String _ID ;
   private int QT_SOLICITADA ;
   private double PRECO;

    private Pedidos pedidos;
    private Produtos produtos;

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public int getQT_SOLICITADA() {
        return QT_SOLICITADA;
    }

    public void setQT_SOLICITADA(int QT_SOLICITADA) {
        this.QT_SOLICITADA = QT_SOLICITADA;
    }

    public double getPRECO() {
        return PRECO;
    }

    public void setPRECO(double PRECO) {
        this.PRECO = PRECO;
    }

    public Pedidos getPedidos() {
        return pedidos;
    }

    public void setPedidos(Pedidos pedidos) {
        this.pedidos = pedidos;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }
}
