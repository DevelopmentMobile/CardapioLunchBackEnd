package br.edu.ifpe.pdm.cardapiolanches.view.func;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.bean.Pedido;
import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;
import br.edu.ifpe.pdm.cardapiolanches.dao.DatabaseHelper;
import br.edu.ifpe.pdm.cardapiolanches.utils.Constantes;
import br.edu.ifpe.pdm.cardapiolanches.utils.NavDrawerItem;
import br.edu.ifpe.pdm.cardapiolanches.utils.NavDrawerListAdapter;
import br.edu.ifpe.pdm.cardapiolanches.view.admin.DashboardAdmin;

/**
 * Created by Richardson on 01/06/2015.
 */
public class PedidoAtendidoProdutoCozinheiroActivity extends Activity implements AdapterView.OnItemClickListener{


    private ViewBinderProdutos simpleCursorAdapter;
    private ListView listView;
    public  int sumTotalPedidios = 0;
    public Menu menuActivity;
    private Map<String,Produto> hashMapMesa = new HashMap<String,Produto>();
    private int produtoSelecionada;
    private final String[] de = {"nome", "preco","categoria",  "tempo_pronto_produto", "status_pedido", "num_pedido"};
    private final int[] para = {R.id.nome_pedido_func, R.id.valor_pedido_func, R.id.mesa_pedido_func, R.id.tempo_total_pedido_func, R.id.num_pedido_func
    };

    private String[] navMenuTitles;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pedidos_func_listview);


        listView= (ListView) findViewById(R.id.pedidos_func_listview);
        simpleCursorAdapter = new ViewBinderProdutos (this, R.layout.pedidos_func, listPedidoPorProdutos(),
                de, para );


        listView.setAdapter(simpleCursorAdapter);



     /*   final TextView total_tempo = (TextView) findViewById(R.id.sum_tempo_total_pronto_pedido_pago);
        final TextView total_valor = (TextView) findViewById(R.id.sum_valor_total_pedido_pago);

        total_tempo.setText(getString(R.string.tempo_total_pronto_pedido_pago, Integer.toString(        simpleCursorAdapter.sum_tempo_total_pronto_pedido_pago)));
        total_valor.setText(getString(R.string.valor_total_pedido_pago, Float.toString(        simpleCursorAdapter.sum_valor_total_pedido_pago)));
        System.out.println(simpleCursorAdapter.sum_tempo_total_pronto_pedido_pago);*/
        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        //  navMenuIcons = activity.getResources().obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3]));
        adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
        mDrawerList.setAdapter(adapter);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
        }

    }


    //Colocar numero da mesa ou atualiza tabela com o campo com num+id
    public Cursor listPedidoPorProdutos(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // ContentValues values = new ContentValues();
        String[] whereArgs = new String[]{Integer.toString(Constantes.PEDIDO_ATENDIDO)};
        String query = "SELECT * FROM pedido pe INNER JOIN produto pr ON pe.produto_id = pr._id WHERE pe.status_pedido = ? ";

        return db.rawQuery(query, whereArgs);

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
        private final int mNumMesa;
        private final int mNumPedido;
        private final int mQuantidade;
        private final int mStatusPedido;
        private final int mIdIndex;

        private final LayoutInflater mLayoutInflater;
        public  int sum_tempo_total_pronto_pedido_pago;
        public float sum_valor_total_pedido_pago;
        private final List<Pedido> listPedidos = new ArrayList<Pedido>();
        private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();




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
            this.mNumMesa = c.getColumnIndex(DatabaseHelper.Produto.CATEGORIA);
            this.mNumPedido = c.getColumnIndex(DatabaseHelper.Pedido.NUM_PEDIDO);
            this.mStatusPedido = c.getColumnIndex(DatabaseHelper.Pedido.STATUS_PEDIDO);
            this.mQuantidade = c.getColumnIndex(DatabaseHelper.Pedido.QUANTIDADE);
            this.sum_tempo_total_pronto_pedido_pago = 0;
            this.sum_valor_total_pedido_pago = 0;



            for (int i = 0; i < this.getCount(); i++) {
                itemChecked.add(i, false); // initializes all items value with false
            }
        }


        class ViewHolder {

            protected TextView preco;
            protected TextView nome;
            protected TextView numMesa;
            protected TextView numPedido;
            protected TextView tempo_produto_entrega;
            /*  protected TextView total_valor;
              protected TextView total_tempo;
  */
            protected RadioGroup rgStatusPedido;
            protected RadioButton statusAtendido;
            protected RadioButton statusRealizado;
            protected RadioButton statusPronto;
            protected RadioButton statusEntregue;
            protected RadioButton statusPago;
            protected CheckBox pedidoSelecionado;
        }



        @Override
        public View getView(final int pos, View inView, ViewGroup parent) {


            if(c.moveToPosition(pos)) {

                View view = null;

                final ViewHolder viewHolder;
                if (inView == null) {
                    LayoutInflater inflator = mLayoutInflater;
                    view = inflator.inflate(R.layout.pedidos_func, null);
                    viewHolder = new ViewHolder();
                    final Produto data = cursorToProduto(this.c);
                    final Pedido pedido = cursorToPedido(this.c);

                    listPedidos.add(pedido);
                    viewHolder.numMesa = (TextView) view.findViewById(R.id.mesa_pedido_func);
                    viewHolder.numPedido = (TextView) view.findViewById(R.id.num_pedido_func);
                    viewHolder.nome = (TextView) view.findViewById(R.id.nome_pedido_func);
                    viewHolder.tempo_produto_entrega = (TextView) view.findViewById(R.id.tempo_total_pedido_func);
                    if (!hashMapMesa.containsKey(Integer.toString(c.getInt(mNumPedido)))) {
                        viewHolder.numPedido.setText(Integer.toString(c.getInt(mNumPedido)));
                        hashMapMesa.put(Integer.toString(c.getInt(mNumPedido)), data);
                        viewHolder.numPedido.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.numPedido.setVisibility(View.GONE);
                    }
                    viewHolder.preco = (TextView) view.findViewById(R.id.valor_pedido_func);
                    viewHolder.rgStatusPedido = (RadioGroup) view.findViewById((R.id.rg_pedido_func));
                    viewHolder.statusAtendido = (RadioButton) view.findViewById((R.id.atendido_pedido_func));
                    viewHolder.statusPronto = (RadioButton) view.findViewById((R.id.pronto_pedido_func));
                    viewHolder.statusPago = (RadioButton) view.findViewById((R.id.pagamento_pedido_func));
                    viewHolder.statusEntregue = (RadioButton) view.findViewById((R.id.entregue_pedido_func));

                    viewHolder.statusRealizado = (RadioButton) view.findViewById((R.id.realizado_pedido_func));
                    viewHolder.pedidoSelecionado = (CheckBox) view.findViewById((R.id.check_func));
                    viewHolder.pedidoSelecionado.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemChecked.set(pos, ((CheckBox) v.findViewById((R.id.check_func))).isChecked());
                        }
                    });


                    view.setTag(viewHolder);
                } else {
                    view = inView;
                    viewHolder =  (ViewHolder) view.getTag();
                }
                ((RadioButton) viewHolder.rgStatusPedido.getChildAt(c.getInt(mStatusPedido) -1)).setChecked(true);

                viewHolder.nome.setText(c.getString(mNameIndex));
                viewHolder.preco.setText(Float.toString(c.getFloat(mPrecoIndex) * c.getInt(mQuantidade)));


                viewHolder.numMesa.setText(c.getString(mNumMesa));


                viewHolder.tempo_produto_entrega.setText(Integer.toString(c.getInt(mTempoPronto) * c.getInt(mQuantidade)));

                sum_valor_total_pedido_pago = sum_valor_total_pedido_pago + c.getFloat(mPrecoIndex) * c.getInt(mQuantidade) ;
                // System.out.println(  this.sum_valor_total_pedido_pago);
                // viewHolder.total_valor.setText(Float.toString(sum_valor_total_pedido_pago));
                sum_tempo_total_pronto_pedido_pago = sum_tempo_total_pronto_pedido_pago + c.getInt(mTempoPronto) * c.getInt(mQuantidade) ;
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

        getMenuInflater().inflate(R.menu.pedido_produto_func, menu);

        // this.menuActivity = menu;

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
        }else if (id == R.id.atualizar_pedido_func) {
            for(int i=0;i< simpleCursorAdapter.itemChecked.size() ; i++){
                if(simpleCursorAdapter.itemChecked.get(i)){
                    Pedido pedido = simpleCursorAdapter.listPedidos.get(i);
                    //Settar id do funcionario? -qual importancia???
                    atualizaStatusPedidoPeloCliente(pedido.get_ID(),Constantes.PEDIDO_PRONTO);

                }

            }


            listView= (ListView) findViewById(R.id.pedidos_func_listview);
            simpleCursorAdapter = new ViewBinderProdutos (this, R.layout.pedidos_func, listPedidoPorProdutos(),
                    de, para );


            listView.setAdapter(simpleCursorAdapter);


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

    private Pedido cursorToPedido(Cursor cursor) {

        Pedido Produto = new Pedido();
        Produto.set_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido._ID)));
        Produto.setTEMPO_TOTAL_PEDIDO(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.TEMPO_TOTAL_PEDIDO)));
        Produto.setSTATUS_PEDIDO(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.STATUS_PEDIDO)));
        Produto.setQUANTIDADE(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.QUANTIDADE)));
        Produto.setPRODUTO_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.PRODUTO_ID)));
        Produto.setPACOTE_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.PACOTE_ID)));
        Produto.setFUNCIONARIO_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.FUNCIONARIO_ID)));
        Produto.setNUM_MESA(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.NUM_MESA)));


        return Produto;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.filtro_categoria).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}
