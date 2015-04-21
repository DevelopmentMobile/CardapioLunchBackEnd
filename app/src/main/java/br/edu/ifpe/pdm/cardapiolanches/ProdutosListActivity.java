package br.edu.ifpe.pdm.cardapiolanches;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProdutosListActivity extends ListActivity implements AdapterView.OnItemClickListener,SimpleAdapter.ViewBinder{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.produtos_list);
        String[] de = {"imagem_produto", "nome_produto", "peso","preco"};
        int[] para = {R.id.imagem_produto, R.id.nome_produto,
                R.id.peso, R.id.preco};

        SimpleAdapter adapter = new SimpleAdapter(this, listarProdutos(), R.layout.produtos_list, de, para);
        adapter.setViewBinder(this);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);


    }
    private List<Map<String, Object>> produtos;
    private List<? extends Map<String, ?>> listarProdutos() {

        produtos = new ArrayList<Map<String, Object>>();
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("imagem_produto", R.drawable.batatafritas);
        item.put("nome_produto", "Batatas Fritas");
        item.put("peso", "200g");
        item.put("preco", "R$ 5,00");
        produtos.add(item);


        item = new HashMap<String, Object>();
        item.put("imagem_produto", R.drawable.bolo_caramelo);
        item.put("nome_produto", "Bolo Caramelo");
        item.put("peso", "1000g");
        item.put("preco", "R$ 3,50");


        produtos.add(item);

        item = new HashMap<String, Object>();
        item.put("imagem_produto", R.drawable.cachorroquente);
        item.put("nome_produto", "Cachorro Quente");
        item.put("peso", "250g");
        item.put("preco", "R$ 1,50");


        produtos.add(item);





        return produtos;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.produtoSelecionada = position;
        Intent intent = new Intent(this, PedidosListActivity.class);

        intent.putExtra("produto", (java.io.Serializable) produtos.get(produtoSelecionada ));
        startActivity(intent);
    }


    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {
        return false;
    }
}
