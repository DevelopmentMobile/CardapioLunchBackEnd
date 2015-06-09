package br.edu.ifpe.pdm.cardapiolanches.view.cliente;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifpe.pdm.cardapiolanches.PedidosListActivity;
import br.edu.ifpe.pdm.cardapiolanches.R;


public class ProdutosListActivity extends ListActivity implements  AdapterView.OnItemClickListener, SimpleAdapter.ViewBinder{


    private String tipoProduto = "";
    private Map<String,String> listCategoriesProducts = new HashMap<String,String>();



    private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.produtos_list);
        String[] de = {"imagem_produto", "nome_produto", "peso","preco", "tipo_produto"};
        int[] para = {R.id.imagem_produto, R.id.nome_produto,
                R.id.peso, R.id.preco, R.id.tipo_produto};

        adapter = new SimpleAdapter(this, listarProdutos(), R.layout.produtos_list, de, para);
        adapter.setViewBinder(this);
      //  ProdutosListActivityIntern produtosListActivityIntern = new ProdutosListActivityIntern(this);

       setListAdapter(adapter);
        //produtosListActivityIntern.setListAdapter(adapter);
        //produtosListActivityIntern.getListView().setOnItemClickListener((AdapterView.OnItemClickListener) this);
        getListView().setOnItemClickListener((AdapterView.OnItemClickListener) this);

        Log.v(MY_LOG, "onCreate()");

    }
    public List<Map<String, Object>> produtos;

    private class ProdutosListActivityIntern extends ListActivity implements AdapterView.OnItemClickListener {

        private Context context;


        private ProdutosListActivityIntern(Context context) {
            this.context = context;

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            Intent intent = new Intent(this, PedidosListActivity.class);
            String info =  Integer.toString(position);
            Log.v(MY_LOG, info);

            Log.v(MY_LOG,  produtos.get(position).toString());
            Map<String,Object> map = produtos.get(position);
            intent.putExtra("produto", (java.io.Serializable) map );
            startActivity(intent);
        }
    }


    private List<? extends Map<String, ?>> listarProdutos() {

        produtos = new ArrayList<Map<String, Object>>();
        Map<String, Object> item = new HashMap<String, Object>();

        //Falta ordena pelo categoria a lista

        item.put("imagem_produto", R.drawable.batatafritas);
        item.put("nome_produto", "Batatas Fritas");
        item.put("peso", "200g");
        item.put("preco", "R$ 5,00");
        item.put("tipo_produto", "Petiscos");
        item.put("precoInt", 5.00 );
        item.put("pesoFloat", 200);
        produtos.add(item);


        item = new HashMap<String, Object>();
        item.put("imagem_produto", R.drawable.cachorroquente);
        item.put("nome_produto", "Cachorro Quente");
        item.put("peso", "250g");
        item.put("preco", "R$ 1,50");
        item.put("tipo_produto", "Petiscos");
        item.put("precoInt", 1.50 );
        item.put("pesoFloat", 250);


        produtos.add(item);


        item = new HashMap<String, Object>();
        item.put("imagem_produto", R.drawable.bolo_caramelo);
        item.put("nome_produto", "Bolo Caramelo");
        item.put("peso", "1000g");
        item.put("preco", "R$ 3,50");
        item.put("tipo_produto", "Doce");
        item.put("precoInt", 3.50 );
        item.put("pesoFloat", 100);



        produtos.add(item);














        return  produtos;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.produtos_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int produtoSelecionada;

    private static final String MY_LOG = "CURRENT_LOG";
   /* @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.produtoSelecionada = position;
        Intent intent = new Intent(this, PedidosListActivity.class);
        String info =  Integer.toString(position);
        Log.v(MY_LOG, info);

        Log.v(MY_LOG,  produtos.get(position).toString());
        Map<String,Object> map = produtos.get(position);
        intent.putExtra("produto", (java.io.Serializable) map );
        startActivity(intent);
    }*/



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);



    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();




    }

    @Override
    protected void onStop() {
        super.onStop();



    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, br.edu.ifpe.pdm.cardapiolanches.PedidosListActivity.class);
        String info =  Integer.toString(position);
        Log.v(MY_LOG, info);

        Log.v(MY_LOG,  produtos.get(position).toString());
        Map<String,Object> map = produtos.get(position);
        intent.putExtra("produto", (java.io.Serializable) map );
        startActivity(intent);
    }

    private class ProductListViewBinder implements SimpleAdapter.ViewBinder{

        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append( "textPresentation: "+ textRepresentation)
                    .append("\ndata: " +data.toString())
                    .append("\nview: " +  Integer.toString(view.getId()));


            if(view.getId() ==  R.id.tipo_produto){
                //if(!tipoProduto.equals(data)){
                if(!listCategoriesProducts.containsKey(data)){
                    Log.v(MY_LOG, stringBuilder.toString());
                    TextView textView = (TextView) view;
                    textView.setText(textRepresentation);
                    //tipoProduto = textRepresentation;
                    listCategoriesProducts.put(textRepresentation,data.toString() );
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
                return true;
            }

            if(view.getId() == R.id.tipo_produto){
                Integer id = (Integer) data;
                view.setBackgroundColor(getResources().getColor(id));
                return true;
            }

            return false;
        }


    }

    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "textPresentation: "+ textRepresentation)
                .append("\ndata: " +data.toString())
                .append("\nview: " +  Integer.toString(view.getId()));


        if(view.getId() ==  R.id.tipo_produto){
            //if(!tipoProduto.equals(data)){
            if(!listCategoriesProducts.containsKey(data)){
                Log.v(MY_LOG, stringBuilder.toString());
                TextView textView = (TextView) view;
                textView.setText(textRepresentation);
                //tipoProduto = textRepresentation;
                listCategoriesProducts.put(textRepresentation,data.toString() );
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
            return true;
        }

        if(view.getId() == R.id.tipo_produto){
            Integer id = (Integer) data;
            view.setBackgroundColor(getResources().getColor(id));
            return true;
        }

        return false;
    }


}
