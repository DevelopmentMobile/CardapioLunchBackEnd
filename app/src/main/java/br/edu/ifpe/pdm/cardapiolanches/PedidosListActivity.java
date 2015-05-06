package br.edu.ifpe.pdm.cardapiolanches;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class PedidosListActivity extends Activity implements AdapterView.OnItemClickListener, SimpleAdapter.ViewBinder {

    private Button buttonAvancarCheckout;
    private Button buttonAdicionarProduto;
    private static final List<Map<String, Object>> produtos = new ArrayList<Map<String, Object>>();

    private static final String MY_LOG = "CURRENT_LOG";

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_pedidos_confirmados);

/*
        String[] de = {"imagem_produto", "nome_produto", "peso","preco"};
        //Bundle extras = getIntent().getExtras();
      //  Map<String, Object> mapProduto = (Map<String, Object>) getIntent().getSerializableExtra("produto");
        //Map<String, Object> mapProduto = (HashMap)extras.getSerializable("produto");
        Map<String, Object> item = new HashMap<String, Object>();
        SimpleAdapter adapter = new SimpleAdapter(this, produtos, R.layout.pedidos_list, de, para);
        adapter.setViewBinder(this);
     //  setListAdapter(adapter);
      //  getListView().setOnItemClickListener(this);
*/
        Map<String, Object> mapProduto = (Map<String, Object>) getIntent().getSerializableExtra("produto");

        if(produtos.contains(mapProduto)){
           //incrementa numberpicker

        }else{


            produtos.add(mapProduto);



        }

        String[] de = {"imagem_produto", "nome_produto", "peso", "preco"};

        int[] para = {R.id.imagem_produto, R.id.nome_produto,
                R.id.peso, R.id.preco};


        listView = (ListView) findViewById(R.id.lv_id);

        SimpleAdapter adapter = new SimpleAdapter(this, produtos, R.layout.listview_test, de, para);

        adapter.setViewBinder(this);


        listView.setAdapter(adapter);

    }


    public void confirmarPedido(View view) {
        Intent intent = new Intent(this, CheckoutPedidosActivity.class);
        startActivity(intent);
    }

    public void adicionarProduto(View view) {

        Intent intent = new Intent(this, ProdutosListActivity.class);

        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pedidos_list_menu, menu);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        int itemPosition = position - 1;

        // ListView Clicked item value
        String itemValue = (String) listView.getItemAtPosition(itemPosition);

        // Show Alert
        Toast.makeText(getApplicationContext(),
                "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                .show();


    }

    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}


