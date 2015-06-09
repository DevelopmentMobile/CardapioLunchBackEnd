package br.edu.ifpe.pdm.cardapiolanches.view.cliente;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;

public class PedidosListActivity extends Activity implements AdapterView.OnItemClickListener, SimpleAdapter.ViewBinder {

    private Button buttonAvancarCheckout;
    private Button buttonAdicionarProduto;

    private SimpleAdapter adapter;

    private String[] de = {"imagem_produto", "nome_produto", "peso", "preco","quantidade"};

    private  int[] para = {R.id.imagem_produto, R.id.nome_produto,
            R.id.peso, R.id.preco, R.id.npQuantidade};


    private static final List<Map<String, Object>> produtos = new ArrayList<Map<String, Object>>();

    private static final String MY_LOG = "CURRENT_LOG";

    ListView listView;
    private NumberPicker _picker;

    private List<Produto> listProduto;

    long _tsLastChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_generic);
        //Map<String, Produto> mapProduto = (Map<String, Produto>) getIntent().getSerializableExtra("produto");
        //listProduto.add(mapProduto.get(1));
    //    listView = (ListView) findViewById(R.id.listview);
        ArrayAdapter<Produto> arrayAdapterProduto = new ArrayAdapter(this, android.R.layout.simple_list_item_1 , listProduto);
        listView.setAdapter(arrayAdapterProduto);



    }




    public void confirmarPedido(View view) {
       // Intent intent = new Intent(this, CheckoutPedidosActivity.class);
     //   startActivity(intent);
    }

    public void adicionarProduto(View view) {

       // Intent intent = new Intent(this, ProdutosListActivity.class);

      //  startActivity(intent);
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

    public static int incNumberPicker = -1;
    public static List<HashMap<Integer,Boolean>> listMapQuantidade = new ArrayList<HashMap<Integer,Boolean>>();

    public static boolean firstTime = false;
    @Override
    public boolean setViewValue(View view, Object data, final String textRepresentation) {




        HashMap<Integer, Boolean> hmTempAux;
        if(view.getId() ==  R.id.npQuantidade){

            _picker = (NumberPicker) view;
            HashMap<Integer, Boolean> hmTemp;


            if (firstTime){
                int position = Integer.parseInt(textRepresentation);

                hmTempAux = listMapQuantidade.get(position);

                if(hmTempAux.get(_picker.getId())) {

                }else {

                    if(produtos.size() > 0) {
                        //Mudar a lista para outra estrutura, ao excluir primeiro elemento levanta excecao
                        produtos.remove(Integer.parseInt(textRepresentation));

                        listMapQuantidade.remove(hmTempAux);


                        if(produtos.size() == 0){
                  //          startActivity(new Intent(this, ProdutosListActivity.class));
                        }
                        adapter = new SimpleAdapter(this, produtos, R.layout.listview_test, de, para);

                       /*Exclusao do item incorreta, exclui intem indice anterior e listview nao
                       *e corresponde depois do item excluido
                       */
                        listView.setAdapter(adapter);



                    }


                }
            }else{
                hmTemp = new  HashMap<Integer, Boolean>();
                hmTemp.put(_picker.getId(),true);
                listMapQuantidade.add(hmTemp);

            }

            //  startActivity(new Intent(this, ProdutosListActivity.class));
                /*    ViewParent viewParent = view.getParent();
                 int maxScroolAmount =   listView.getMaxScrollAmount();
                 int typeCount =   listView.getAdapter().getViewTypeCount();
                    int itemViewType  =  listView.getAdapter().getItemViewType(view.getId());
                                       int count = listView.getAdapter().getCount();
                    long itemId = listView.getAdapter().getItemId(view.getId());*/

            _picker.setMinValue(0);
            _picker.setMaxValue(100);
            _picker.setValue(1);




            _picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    ///Log.d("NumberPickerActivity", "value changed detected");

                    if(newVal == 0){
                        picker.setMinValue(0);
                        picker.setMaxValue(100);
                        picker.setValue(1);

                        HashMap<Integer, Boolean> hmTempAux = new HashMap<Integer, Boolean>();
                        hmTempAux.put(picker.getId(),true);

                        listMapQuantidade.remove(hmTempAux);
                        hmTempAux.put(_picker.getId(),false);
                        listMapQuantidade.add(hmTempAux);
                        firstTime =true;
                        listView.setAdapter(adapter);

                    }
/*                long tsChange = System.currentTimeMillis();




                if (tsChange - _tsLastChange < 200)
                {
                    if (newVal > oldVal)
                        _picker.setValue(newVal + 100);
                    else
                        _picker.setValue(newVal - 100);
                }
                _tsLastChange = tsChange;*/

                }
            });
            return true;
        }

        return false;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}


