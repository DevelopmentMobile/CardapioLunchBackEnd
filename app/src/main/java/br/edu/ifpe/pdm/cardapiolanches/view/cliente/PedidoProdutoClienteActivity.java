package br.edu.ifpe.pdm.cardapiolanches.view.cliente;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;
import br.edu.ifpe.pdm.cardapiolanches.dao.DatabaseHelper;
import br.edu.ifpe.pdm.cardapiolanches.utils.Constantes;
import br.edu.ifpe.pdm.cardapiolanches.view.admin.DashboardAdmin;

/**
 * Created by Richardson on 01/06/2015.
 */
public class PedidoProdutoClienteActivity extends Activity implements AdapterView.OnItemClickListener{


    private ViewBinderProdutos simpleCursorAdapter;
    private ListView listView;
    public  int sumTotalPedidios = 0;
    public Menu menuActivity;
    private Map<String,Produto> listCategoriesProducts = new HashMap<String,Produto>();
    private int produtoSelecionada;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pedidos_cliente_listview);
        String[] de = {"nome", "preco","categoria",  "tempo_pronto_produto", "status_pedido"};
        int[] para = {R.id.nome_pedido_produto, R.id.valor_pedido_produto, R.id.categoria_pedido_produto, R.id.tempo_total_pedido_produto
        };

        listView= (ListView) findViewById(R.id.pedidos_cliente_listview);
        simpleCursorAdapter = new ViewBinderProdutos (this, R.layout.pedidos_cliente, listPedidoPorProdutos(),
                de, para );


        listView.setAdapter(simpleCursorAdapter);

        final TextView total_tempo = (TextView) findViewById(R.id.sum_tempo_total_pronto_pedido_pago);
        final TextView total_valor = (TextView) findViewById(R.id.sum_valor_total_pedido_pago);

        total_tempo.setText(getString(R.string.tempo_total_pronto_pedido_pago, Integer.toString(        simpleCursorAdapter.sum_tempo_total_pronto_pedido_pago)));
        total_valor.setText(getString(R.string.valor_total_pedido_pago, Float.toString(        simpleCursorAdapter.sum_valor_total_pedido_pago)));
        System.out.println(simpleCursorAdapter.sum_tempo_total_pronto_pedido_pago);

    }


    //Colocar numero da mesa ou atualiza tabela com o campo com num+id
    public Cursor listPedidoPorProdutos(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

       // ContentValues values = new ContentValues();
        String[] whereArgs = new String[]{Integer.toString(Constantes.NUM_MESA)};
       String query = "SELECT * FROM pedido pe INNER JOIN produto pr ON pe._id = pr._id";
       //String query = "SELECT * FROM pedido pe INNER JOIN produto pr ON pe._id = pr._id INNER JOIN pacote pr ON pe._id = pa._id WHERE pe.num_mesa = ?";

        //return db.rawQuery(query, null);
        return db.rawQuery(query, null);
     /*   return db.rawQuery("SELECT * FROM " +  DatabaseHelper.Pedido.TABELA + " INNER JOIN " +
                DatabaseHelper.Produto.TABELA + " ON " + DatabaseHelper.Pedido._ID +
                " = " + DatabaseHelper.Produto._ID + " WHERE " +   DatabaseHelper.Pedido.NUM_MESA + " = 5 " + " ORDER BY " + DatabaseHelper.Produto.CATEGORIA + " ASC , " + DatabaseHelper.Produto.NOME + " DESC ", null);
                */
    }



    public int atualizaStatusPedidoPeloCliente(int id, int status){
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    SQLiteDatabase db = dbHelper.getReadableDatabase();

    ContentValues values = new ContentValues();
    values.put(DatabaseHelper.Pedido.STATUS_PEDIDO,status );

    String selection = DatabaseHelper.Pedido._ID + " LIKE ?";
    String[] selectionArgs = {id+ "" };
    int count = db.update(DatabaseHelper.Pedido.TABELA, values, selection, selectionArgs);
    Toast toast = Toast.makeText(this,
            "Registros atualizados: " + count, Toast.LENGTH_SHORT);
    toast.show();

        return count;
}



    private class ViewBinderProdutos  extends SimpleCursorAdapter {



        private RadioButton rbProdutoSelecionado;

        private final Cursor c;
        private final Context context;
        private final int mNameIndex;
        private final int mPrecoIndex;
        private final int mTempoPronto;
        private final int mCategoria;
        private final int mQuantidade;
        private final int mStatusPedido;
        private final int mIdIndex;

        private final LayoutInflater mLayoutInflater;
        public  int sum_tempo_total_pronto_pedido_pago;
        public float sum_valor_total_pedido_pago;



        public ViewBinderProdutos(Context context, int layout, Cursor c, String[] from,
                                  int[] to) {
            super(context, layout, c, from, to);
            this.c = c;
            this.context = context;
            mLayoutInflater = LayoutInflater.from(this.context);
            this.mNameIndex = c.getColumnIndex(DatabaseHelper.Produto.NOME);
            this.mPrecoIndex = c.getColumnIndex(DatabaseHelper.Produto.PRECO);
            this.mIdIndex = c.getColumnIndex(DatabaseHelper.Pedido._ID);
            this.mTempoPronto = c.getColumnIndex(DatabaseHelper.Produto.TEMPO_PRONTO_PRODUTO);
            this.mCategoria = c.getColumnIndex(DatabaseHelper.Produto.CATEGORIA);
            this.mStatusPedido = c.getColumnIndex(DatabaseHelper.Pedido.STATUS_PEDIDO);
            this.mQuantidade = c.getColumnIndex(DatabaseHelper.Pedido.QUANTIDADE);
            this.sum_tempo_total_pronto_pedido_pago = 0;
            this.sum_valor_total_pedido_pago = 0;

        }


        class ViewHolder {

            protected TextView preco;
            protected TextView nome;
            protected TextView categoria;
            protected TextView tempo_produto_entrega;
          /*  protected TextView total_valor;
            protected TextView total_tempo;
*/
            protected RadioGroup rgStatusPedido;
            protected RadioButton statusAtendido;
            protected RadioButton statusRealizado;
            protected RadioButton statusPronto;
            protected RadioButton statusRealizarPagamento;
        }



        @Override
        public View getView(final int pos, View inView, ViewGroup parent) {


            if(c.moveToPosition(pos)) {

                View view = null;

                final ViewHolder viewHolder;
                if (inView == null) {
                    LayoutInflater inflator = mLayoutInflater;
                    view = inflator.inflate(R.layout.pedidos_cliente, null);
                    viewHolder = new ViewHolder();
                    Produto data = cursorToProduto(this.c);
                    viewHolder.categoria = (TextView) view.findViewById(R.id.categoria_pedido_produto);
                    viewHolder.nome = (TextView) view.findViewById(R.id.nome_pedido_produto);
                    viewHolder.tempo_produto_entrega = (TextView) view.findViewById(R.id.tempo_total_pedido_produto);
                    if (!listCategoriesProducts.containsKey(data.getCATEGORIA())) {
                        viewHolder.categoria.setText(data.getCATEGORIA());
                        listCategoriesProducts.put(data.getCATEGORIA(), data);
                        viewHolder.categoria.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.categoria.setVisibility(View.GONE);
                    }
                    viewHolder.preco = (TextView) view.findViewById(R.id.valor_pedido_produto);
                    viewHolder.rgStatusPedido = (RadioGroup) view.findViewById((R.id.rg_pedido_produto));
                    viewHolder.statusAtendido = (RadioButton) view.findViewById((R.id.atendido_pedido_produto));
                    viewHolder.statusPronto = (RadioButton) view.findViewById((R.id.pronto_pedido_produto));
                    viewHolder.statusRealizado = (RadioButton) view.findViewById((R.id.realizado_pedido_produto));
                    //viewHolder.statusRealizarPagamento = (RadioButton) view.findViewById((R.id.realizar_pagamento_pedido_produto));
             /*       viewHolder.total_tempo = (TextView) view.findViewById(R.id.sum_tempo_total_pronto_pedido_pago);
                    viewHolder.total_valor = (TextView) view.findViewById(R.id.sum_valor_total_pedido_pago);
*/

                    view.setTag(viewHolder);
                } else {
                    view = inView;
                    viewHolder =  (ViewHolder) view.getTag();
                }
                ((RadioButton) viewHolder.rgStatusPedido.getChildAt(c.getInt(mStatusPedido) -1)).setChecked(true);
                int pronto = c.getInt(mStatusPedido) -1;
              /*  if(pronto == 2){
                    ((RadioButton) viewHolder.rgStatusPedido.getChildAt(c.getInt(mStatusPedido) -1)).setClickable(true);
                    ((RadioButton) viewHolder.rgStatusPedido.getChildAt(c.getInt(mStatusPedido))).setClickable(true);
                }*/
                viewHolder.nome.setText(c.getString(mNameIndex));
                viewHolder.preco.setText(Float.toString(c.getFloat(mPrecoIndex) * c.getInt(mQuantidade)));


                viewHolder.categoria.setText(c.getString(mCategoria));


                viewHolder.tempo_produto_entrega.setText(Integer.toString(c.getInt(mTempoPronto) * c.getInt(mQuantidade)));

                this.sum_valor_total_pedido_pago = sum_valor_total_pedido_pago + c.getFloat(mPrecoIndex) * c.getInt(mQuantidade) ;
                System.out.println(  this.sum_valor_total_pedido_pago);
               // viewHolder.total_valor.setText(Float.toString(sum_valor_total_pedido_pago));
                this.sum_tempo_total_pronto_pedido_pago = sum_tempo_total_pronto_pedido_pago + c.getInt(mTempoPronto) * c.getInt(mQuantidade) ;
               // viewHolder.total_tempo.setText(Integer.toString(sum_tempo_total_pronto_pedido_pago));

                return view;
            }

            return inView;

        }









    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menuActivity; this adds items to the action bar if it is present.

        //super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.pedido_produto_cliente, menu);

        this.menuActivity = menu;

        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }else*/
        if (id == R.id.dashboard_admin) {
            startActivity(new Intent(this, DashboardAdmin.class));
            return true;
        }





        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.produtoSelecionada = position;
        Intent intent = new Intent(this, br.edu.ifpe.pdm.cardapiolanches.PedidosListActivity.class);

        // intent.putExtra("produto", (java.io.Serializable) produtos.get(produtoSelecionada ));
        startActivity(intent);
    }

    private Produto cursorToProduto(Cursor cursor) {

        Produto Produto = new br.edu.ifpe.pdm.cardapiolanches.bean.Produto();
        Produto.set_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Produto._ID)));
        Produto.setNOME_IMAGEM(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.NOME_IMAGEM)));
        Produto.setNOME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.NOME)));
        Produto.setCATEGORIA(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.CATEGORIA)));
        Produto.setDESCRICAO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.DESCRICAO)));
        Produto.setPRECO(cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.Produto.PRECO)));
        Produto.setTEMPO_PRONTO_PRODUTO(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Produto.TEMPO_PRONTO_PRODUTO)));
        Produto.setUNIDADE_ESTOQUE(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Produto.UNIDADE_ESTOQUE)));


        return Produto;
    }

}
