package br.edu.ifpe.pdm.cardapiolanches;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class PedidosListActivity extends ListActivity implements AdapterView.OnItemClickListener,SimpleAdapter.ViewBinder {

    private Button buttonAvancarCheckout;
    private Button buttonAdicionarProduto;
    private List<Map<String, Object>> produtos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pedidos_list);

        String[] de = {"imagem_produto", "nome_produto", "peso","preco"};
        int[] para = {R.id.imagem_produto, R.id.nome_produto,
                R.id.peso, R.id.preco};

        //Bundle extras = getIntent().getExtras();
        Map<String, Object> mapProduto = (HashMap<String, Object>) getIntent().getSerializableExtra("produto");
        //Map<String, Object> mapProduto = (HashMap)extras.getSerializable("produto");
        produtos.add(mapProduto);

        SimpleAdapter adapter = new SimpleAdapter(this, produtos, R.layout.pedidos_list, de, para);
        adapter.setViewBinder(this);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);


        buttonAdicionarProduto = (Button) findViewById(R.id.adicionar_pedidos_list);
        buttonAdicionarProduto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                confirmarPedido();

                                  } });

        buttonAvancarCheckout= (Button) findViewById(R.id.avancar_pedidos_list);
        buttonAvancarCheckout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adicionarProduto();

            } });


    }

    public void confirmarPedido(){
        Intent intent = new Intent(this, CheckoutPedidosActivity.class);
        startActivity(intent);
    }

    public void adicionarProduto(){

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

    }

    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {
        return false;
    }
}
