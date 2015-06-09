package br.edu.ifpe.pdm.cardapiolanches.view.cliente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;
import br.edu.ifpe.pdm.cardapiolanches.dao.DatabaseHelper;
import br.edu.ifpe.pdm.cardapiolanches.utils.NavDrawerItem;
import br.edu.ifpe.pdm.cardapiolanches.utils.NavDrawerListAdapter;
import br.edu.ifpe.pdm.cardapiolanches.view.admin.DashboardAdmin;


public class OfertasProdutosListActivity extends Activity implements AdapterView.OnItemClickListener{


    private ViewBinderProdutos simpleCursorAdapter;
    private ListView listView;
    public  int sumTotalPedidios = 0;
    public  Menu menuActivity;
    private Map<String,Produto>  listCategoriesProducts = new HashMap<String,Produto>();
    private int produtoSelecionada;
   private final  String[] de = {"nome", "preco", "descricao", "tempo_pronto_produto", "categoria"};
   private final int[] para = {R.id.nome_produto, R.id.preco_produto, R.id.descricao_produto, R.id.tempo_pronto_produto, R.id.categoria_produto};

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
        setContentView(R.layout.listview_generic);
        /*
        String[] de = {"nome", "preco", "descricao", "tempo_pronto_produto", "categoria"};
        int[] para = {R.id.nome_produto, R.id.preco_produto,R.id.descricao_produto, R.id.tempo_pronto_produto, R.id.categoria_produto
               };
               */
        //ProdutoDAO produtoDAO = new ProdutoDAO(this);
        //produtoDAO.read();
          //List<Produto> productList = produtoDAO.consultarTodosProduto();
          //produtoDAO.close();
     listView= (ListView) findViewById(R.id.listview);

       simpleCursorAdapter = new ViewBinderProdutos (this, R.layout.produtos_list, listProdutos(),
                de, para );

        simpleCursorAdapter.setViewBinder( new ViewBinderProdutos(this, R.layout.produtos_list, listProdutos(),
                de, para));
                listView.setAdapter(simpleCursorAdapter);


      //          DatabaseHelper.Produto.COLUNAS, para );

      //  simpleCursorAdapter.setViewBinder(new ViewBinderProdutos(this, R.layout.produtos_list, listPedidoPorProdutos(),
         //       de, para));



/*
        SimpleAdapter adapter = new SimpleAdapter(this, productList., R.layout.produtos_list, DatabaseHelper.Produto.COLUNAS, para);
        adapter.setViewBinder(this);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
*/

      //  c = listCategoriaProdutos();

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

    public void filtrarProdutoCategoria(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opções dos Produtos por Categoria:");

        builder.setSingleChoiceItems(listCategoriaProdutos(), 0, DatabaseHelper.Produto.CATEGORIA, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                  Cursor c = listCategoriaProdutos();

                if(    c.moveToPosition(which)){
                    String categoria =   c.getString(c.getColumnIndex(DatabaseHelper.Produto.CATEGORIA));

                    simpleCursorAdapter = new ViewBinderProdutos (OfertasProdutosListActivity.this, R.layout.produtos_list,  atualizaListaProdutosPorCategoria(categoria),de, para );

                    simpleCursorAdapter.setViewBinder( new ViewBinderProdutos(OfertasProdutosListActivity.this, R.layout.produtos_list,  atualizaListaProdutosPorCategoria(categoria),de, para));

                    listView.setAdapter(simpleCursorAdapter);

                }

            }
        });
        builder.create();
        builder.show();


    }

    public Cursor listProdutos(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + DatabaseHelper.Produto.TABELA + " ORDER BY " + DatabaseHelper.Produto.CATEGORIA + " ASC , " + DatabaseHelper.Produto.NOME + " DESC ", null);
    }

    public Cursor listCategoriaProdutos(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = " SELECT * FROM produto ";
        return db.rawQuery(" SELECT * FROM produto GROUP BY categoria", null);

    }

    public Cursor atualizaListaProdutosPorCategoria(String categoria){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] whereArgs = new String[]{categoria};
        String query = " SELECT * FROM produto WHERE categoria = ? ";
        return db.rawQuery(query, whereArgs);
    }



    private class ViewBinderProdutos  extends SimpleCursorAdapter implements SimpleCursorAdapter.ViewBinder {



        private RadioButton rbProdutoSelecionado;

        private final Cursor c;
        private final Context context;
        private final int mNameIndex;
        private final int mPrecoIndex;
        private final int mTempoPronto;
        private final int mImagem;
        private final int mDescricaoIndex;
        private final int mIdIndex;

        private final LayoutInflater mLayoutInflater;
        private ArrayList<String> list = new ArrayList<String>();
        private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();


        public ViewBinderProdutos(Context context, int layout, Cursor c, String[] from,
                             int[] to) {
            super(context, layout, c, from, to);
            this.c = c;
            this.context = context;
            mLayoutInflater = LayoutInflater.from(this.context);
            this.mNameIndex = c.getColumnIndex(DatabaseHelper.Produto.NOME);
            this.mPrecoIndex = c.getColumnIndex(DatabaseHelper.Produto.PRECO);
            this.mDescricaoIndex = c.getColumnIndex(DatabaseHelper.Produto.DESCRICAO);
            this.mIdIndex = c.getColumnIndex(DatabaseHelper.Produto._ID);
            this.mTempoPronto = c.getColumnIndex(DatabaseHelper.Produto.TEMPO_PRONTO_PRODUTO);
            this.mImagem = c.getColumnIndex(DatabaseHelper.Produto.NOME_IMAGEM);
            /*
            for (int i = 0; i < this.getCount(); i++) {
                itemChecked.add(i, false); // initializes all items value with false
            }*/
        }


         class ViewHolder {
            //protected TextView text;
            protected TextView preco;
            protected TextView nome;
            protected TextView descricao;
            protected TextView categoria;
            protected TextView tempo_produto_entrega;
            protected ImageView image;
            protected TableRow trInfoExpandable;
            protected TableRow trLabelExpandable;



            protected CheckBox checkbox;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater= mLayoutInflater;
            final ViewHolder viewHolder = new ViewHolder();
            View row=inflater.inflate(R.layout.produtos_list, null);
            viewHolder.categoria = (TextView) row.findViewById(R.id.categoria_produto);
            viewHolder.nome = (TextView) row.findViewById(R.id.nome_produto);
            viewHolder.descricao = (TextView) row.findViewById(R.id.descricao_produto);


            viewHolder.preco = (TextView) row.findViewById(R.id.preco_produto);
            viewHolder.image = (ImageView) row.findViewById(R.id.imagem_produto);
            viewHolder.checkbox = (CheckBox) row.findViewById(R.id.check);
            row.setTag(viewHolder);
            return(row);
        }

/*
        @Override
        public void bindView(View view, Context context, Cursor cursor) {

        }
*/


        @Override
        public View getView(final int pos, View inView, ViewGroup parent) {


 if(c.moveToPosition(pos)) {

                View view = null;

     final ViewHolder viewHolder;
                if (inView == null) {
                    LayoutInflater inflator = mLayoutInflater;
                    view = inflator.inflate(R.layout.produtos_list, null);
                   // final ViewHolder        // final ViewHolder viewHolder = new ViewHolder();




                  viewHolder = new ViewHolder();




                    Produto data = cursorToProduto(this.c);

                    viewHolder.categoria = (TextView) view.findViewById(R.id.categoria_produto);
                    viewHolder.nome = (TextView) view.findViewById(R.id.nome_produto);
                    viewHolder.tempo_produto_entrega = (TextView) view.findViewById(R.id.tempo_pronto_produto);
                    viewHolder.descricao = (TextView) view.findViewById(R.id.descricao_produto);
                    if (!listCategoriesProducts.containsKey(data.getCATEGORIA())) {

                        viewHolder.categoria.setText(data.getCATEGORIA());
                        //tipoProduto = textRepresentation;
                        listCategoriesProducts.put(data.getCATEGORIA(), data);
                        viewHolder.categoria.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.categoria.setVisibility(View.GONE);
                    }


                    viewHolder.preco = (TextView) view.findViewById(R.id.preco_produto);

                    viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
                                 viewHolder.image = (ImageView) view.findViewById(R.id.imagem_produto);
                            viewHolder.descricao = (TextView) view.findViewById(R.id.descricao_produto);
                            viewHolder.tempo_produto_entrega = (TextView) view.findViewById(R.id.tempo_pronto_produto);
                            viewHolder.trLabelExpandable = (TableRow) view.findViewById(R.id.row_label_expandable);
                            viewHolder.trInfoExpandable = (TableRow) view.findViewById(R.id.row_info_expandable);

                /*
                viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Toast.makeText(context, "Checked", Toast.LENGTH_SHORT);                            //Case 1
                            // do some operations here
                    }
                });
                */

                    viewHolder.checkbox
                            .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                @Override
                                public void onCheckedChanged(CompoundButton buttonView,
                                                             boolean isChecked) {

                                    if (isChecked) {
                                        Toast.makeText(context, "Produto Adicionado", Toast.LENGTH_SHORT).show();

                                     //   incrementarTotalPedidosMenu(menuActivity);

                                    } else if (!isChecked) {
                                         Toast.makeText(context, "Produto Retirado", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            // ViewHolder viewHolder  = new ViewHolder();

        /*                    viewHolder.image = (ImageView) v.findViewById(R.id.imagem_produto);
                            viewHolder.descricao = (TextView) v.findViewById(R.id.descricao_produto);
                            viewHolder.tempo_produto_entrega = (TextView) v.findViewById(R.id.tempo_pronto_produto);*/


                            if (viewHolder.trLabelExpandable.isShown() && viewHolder.trInfoExpandable.isShown()) {

                                viewHolder.trLabelExpandable.setVisibility(View.INVISIBLE);
                                viewHolder.trInfoExpandable.setVisibility(View.INVISIBLE);
                             //    viewHolder.image.setVisibility(View.INVISIBLE);
                                return true;
                            } else {
                                viewHolder.trLabelExpandable.setVisibility(View.VISIBLE);
                                viewHolder.trInfoExpandable.setVisibility(View.VISIBLE);
                               // viewHolder.image.setVisibility(View.VISIBLE);
                                return true;
                            }
                        }
                    });

                    view.setTag(viewHolder);
                    //viewHolder.checkbox.setTag(list.get(position));
                } else {


                    view = inView;
                    viewHolder =  (ViewHolder) view.getTag();
                    //((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
                }

                String name=c.getString(mNameIndex);
                //String id = c.getString(mIdIndex);
                String preco = c.getString(mPrecoIndex);

                viewHolder.nome.setText(name);
                viewHolder.preco.setText(preco);
                viewHolder.descricao.setText(c.getString(mDescricaoIndex));
                viewHolder.tempo_produto_entrega.setText(c.getString(mTempoPronto));
               // viewHolder.image.setImageDrawable(drawable);







                if (view.getId() == R.id.categoria_produto) {
                    view.setBackgroundColor(getResources().getColor(this.c.getPosition()));

                }
     return view;
            }

                //ViewHolder holder = (ViewHolder) view.getTag();
                //holder.text.setText(list.get(position).getName());
                //holder.checkbox.setChecked(list.get(position).isSelected());

            return inView;

        }
        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {


            View inView = null;
            if (view == null) {
/*                LayoutInflater inflator = context.getLayoutInflater();
                inView = inflator.inflate(R.layout.produtos_list, null);*/
                final ViewHolder viewHolder = new ViewHolder();

                viewHolder.categoria = (TextView) inView.findViewById(R.id.categoria_produto);
                viewHolder.nome = (TextView) inView.findViewById(R.id.nome_produto);
                viewHolder.descricao = (TextView) inView.findViewById(R.id.descricao_produto);
                viewHolder.preco = (TextView) inView.findViewById(R.id.preco_produto);
                viewHolder.image = (ImageView) inView.findViewById(R.id.imagem_produto);
                viewHolder.checkbox = (CheckBox) inView.findViewById(R.id.check);
                viewHolder.checkbox
                        .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         boolean isChecked) {

                                if (isChecked) {
                                    Toast.makeText(context, "Checked", Toast.LENGTH_LONG);
                                    incrementarTotalPedidosMenu(menuActivity);
                                    // do some operations here
                                } else if (!isChecked) {
                                    decrementarTotalPedidosMenu(menuActivity);
                                    Toast.makeText(context, "Unchecked", Toast.LENGTH_LONG);
                                    // do some operations here
                                }


                            }
                        });
                /*
                viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Toast.makeText(context, "Checked", Toast.LENGTH_SHORT);                            //Case 1
                            // do some operations here
                    }
                });
                */


                inView.setTag(viewHolder);
                //viewHolder.checkbox.setTag(list.get(position));
            } else {

                //((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
            }



            Produto data  = cursorToProduto(cursor);

            cursorToProduto(cursor);



            if(view.getId() ==  R.id.categoria_produto){
                //if(!tipoProduto.equals(data)){


                if(!listCategoriesProducts.containsKey(data.getCATEGORIA())){
                    TextView textView = (TextView) view;
                    textView.setText(data.getCATEGORIA());
                    //tipoProduto = textRepresentation;
                    listCategoriesProducts.put(data.getCATEGORIA(),data );
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
                return true;
            }

            if(view.getId() == R.id.categoria_produto){
                view.setBackgroundColor(getResources().getColor( cursor.getPosition()));
                return true;
            }

            return false;
        }








    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menuActivity; this adds items to the action bar if it is present.

        //super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.ofertas_list_menu, menu);

          //  this.menuActivity = menu;

        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();


        if (id == R.id.filtro_categoria) {
            filtrarProdutoCategoria();
            return true;
        }else if (id == R.id.dashboard_admin) {

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
    public void incrementarTotalPedidosMenu(Menu menu){

        sumTotalPedidios++;


        //MenuItem menuItem = menuActivity.findItem(R.id.total_pedido_pronto);

    }

    public void decrementarTotalPedidosMenu(Menu menu){

        if(sumTotalPedidios> 0) {

            sumTotalPedidios--;
            //MenuItem menuItem = menuActivity.findItem(R.id.total_pedido_pronto);
             }
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
