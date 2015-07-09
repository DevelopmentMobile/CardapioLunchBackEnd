package br.edu.ifpe.pdm.cardapiolanches.view.cliente;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.bean.Pedido;
import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;
import br.edu.ifpe.pdm.cardapiolanches.dao.DatabaseHelper;
import br.edu.ifpe.pdm.cardapiolanches.dao.PedidoListener;
import br.edu.ifpe.pdm.cardapiolanches.utils.Constantes;
import br.edu.ifpe.pdm.cardapiolanches.utils.NavDrawerItem;
import br.edu.ifpe.pdm.cardapiolanches.utils.NavDrawerListAdapter;
import br.edu.ifpe.pdm.cardapiolanches.view.admin.DashboardAdmin;

/**
 * Created by Richardson on 01/06/2015.
 */
public class PedidoProdutoClienteActivity extends Activity implements AdapterView.OnItemClickListener, PedidoListener{


    private ViewBinderProdutos simpleCursorAdapter;
    private ListView listView;
    public  int sumTotalPedidios = 0;
    public Menu menuActivity;
    private Map<String,Produto> listCategoriesProducts = new HashMap<String,Produto>();
    private int produtoSelecionada;

    private String[] navMenuTitles;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;


    String[] de = {"nome", "preco","categoria",  "tempo_pronto_produto", "status_pedido"};
    int[] para = {R.id.nome_pedido_produto, R.id.valor_pedido_produto, R.id.categoria_pedido_produto, R.id.tempo_total_pedido_produto
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pedidos_cliente_listview);



        new PedidoLoadTask(this).execute();

        listView.setAdapter(simpleCursorAdapter);

        final TextView total_tempo = (TextView) findViewById(R.id.sum_tempo_total_pronto_pedido_pago);
        final TextView total_valor = (TextView) findViewById(R.id.sum_valor_total_pedido_pago);

        total_tempo.setText(getString(R.string.tempo_total_pronto_pedido_pago, Integer.toString(        simpleCursorAdapter.sum_tempo_total_pronto_pedido_pago)));
        total_valor.setText(getString(R.string.valor_total_pedido_pago, Float.toString(        simpleCursorAdapter.sum_valor_total_pedido_pago)));
        System.out.println(simpleCursorAdapter.sum_tempo_total_pronto_pedido_pago);

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
            //displayView(0);
        }


    }


    //Colocar numero da mesa ou atualiza tabela com o campo com num+id
    public Cursor listPedidoPorProdutos(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

       // ContentValues values = new ContentValues();
        String[] whereArgs = new String[]{Integer.toString(Constantes.NUM_MESA)};
       String query = "SELECT * FROM pedido pe INNER JOIN produto pr ON pe.produto_id = pr._id";
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

    public void inserirPedidos(Pedido pedido){

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Pedido.QUANTIDADE, pedido.getQUANTIDADE());
        values.put(DatabaseHelper.Pedido.TEMPO_TOTAL_PEDIDO, pedido.getTEMPO_TOTAL_PEDIDO());
        values.put(DatabaseHelper.Pedido.NUM_MESA,pedido.getNUM_MESA() );


            values.put(DatabaseHelper.Pedido.FUNCIONARIO_ID, pedido.getFUNCIONARIO_ID());


            values.put(DatabaseHelper.Pedido.PRODUTO_ID, pedido.getPRODUTO_ID());

            values.put(DatabaseHelper.Pedido.PACOTE_ID, pedido.getPACOTE_ID());

        values.put(DatabaseHelper.Pedido.STATUS_PEDIDO, pedido.getSTATUS_PEDIDO() );

        long newId = db.insert(DatabaseHelper.Pedido.TABELA, null, values);


    }


    @Override
    public void showPedido(List<Pedido> Pedidos) {


        if (Pedidos.size() > 0) {

            for (Pedido produto : Pedidos) {
                inserirPedidos(produto);

            }

            listView = (ListView) findViewById(R.id.pedidos_cliente_listview);
            simpleCursorAdapter = new ViewBinderProdutos(this, R.layout.pedidos_cliente, listPedidoPorProdutos(),
                    de, para);

        }
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


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.pedir_conta).setVisible(!drawerOpen);
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


    public class PedidoLoadTask extends AsyncTask<Pedido, Void, List<Pedido>> {
        private final String LOG_TAG = PedidoLoadTask.class.getSimpleName();
        private String[] forecast = null;
        private List<Pedido> Pedido = null;

//    private List<Map<String,String>> listDetailsEnergyBill =null;


        private PedidoListener listener = null;

        public PedidoLoadTask(PedidoListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<Pedido> doInBackground(Pedido... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String forecastJson = null;

            try {
                //Uri.Builder builder = new Uri.Builder();

                Uri.Builder builder = Uri.parse("http://10.1.1.44:8080").buildUpon();
                //builder.scheme("http");
                //  builder.authority("10.1.1.44");

                builder.appendPath("pedido");

                builder.appendQueryParameter("acao","consultar");
                //  builder.appendQueryParameter("_id", Pedido.get_ID().toString());

                    builder.appendQueryParameter("produto_id", "-1");
                    builder.appendQueryParameter("funcionario_id","-1" );
                    builder.appendQueryParameter("pacote_id", "-1");
                    builder.appendQueryParameter("status_pedido", "-1");
                    builder.appendQueryParameter("num_mesa", "-1");
                    builder.appendQueryParameter("quantidade","-1");
                    builder.appendQueryParameter("num_pedido", "-1");
                    builder.appendQueryParameter("tempo_total", "-1");



                URL url = new URL(builder.build().toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();

                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    forecastJson = null;
                }


                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
//                buffer.append(line.replaceAll("\\n",  "").replaceAll("\\t","") );
                    buffer.append(line);
                }

                if (buffer.length() == 0) {
                    forecastJson = null;
                } else {
                    forecastJson = buffer.toString();
                }
                //     listEnergyBill = EnergyBillParser.printValuesCellTable(forecastJson);
                Pedido = getPedidoFromJson(forecastJson);


            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return Pedido;
        }


        @Override
        protected void onPostExecute(List<Pedido> pedidos) {

            Log.v(LOG_TAG, "Retorno de Pedidos: " + pedidos);

            listener.showPedido(pedidos);

        }


        private List<Pedido> getPedidoFromJson(String forecastJsonStr) {


            List<Pedido> Pedidos = null;


            try {
                if (forecastJsonStr != null) {

                    JSONObject forecastJson = new JSONObject(forecastJsonStr);
                    JSONArray ja = new JSONArray();
                    ja = forecastJson.getJSONArray("pedidos");

                    Pedidos = new ArrayList<Pedido>();
                    for (int i = 0; i < ja.length(); i++) {


                        Pedido Pedido = new Pedido();

                        Pedido.set_ID(ja.getJSONObject(i).getInt("_id"));
                        Pedido.setPRODUTO_ID(ja.getJSONObject(i).getInt("produto_id"));
                        Pedido.setFUNCIONARIO_ID(ja.getJSONObject(i).getInt("funcionario_id"));
                        Pedido.setPACOTE_ID(ja.getJSONObject(i).getInt("pacote_id"));
                        Pedido.setPACOTE_ID(ja.getJSONObject(i).getInt("num_mesa"));
                        Pedido.setSTATUS_PEDIDO(ja.getJSONObject(i).getInt("status_pedido"));
                        Pedido.setQUANTIDADE(ja.getJSONObject(i).getInt("quantidade"));
                        Pedido.setNUM_PEDIDO(ja.getJSONObject(i).getString("num_pedido"));
                        Pedido.setTEMPO_TOTAL_PEDIDO(ja.getJSONObject(i).getInt("tempo_total"));
                        Pedido.setACAO(ja.getJSONObject(i).getString("acao"));
                        Pedidos.add(Pedido);
                    }
                }
            } catch (
                    JSONException e
                    )

            {
                e.printStackTrace();
            }

            return Pedidos;

        }

    }
    }
