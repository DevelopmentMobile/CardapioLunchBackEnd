package br.edu.ifpe.pdm.cardapiolanches.view.cliente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;
import br.edu.ifpe.pdm.cardapiolanches.dao.ProdutoDAO;
import br.edu.ifpe.pdm.cardapiolanches.utils.GroupProduto;
import br.edu.ifpe.pdm.cardapiolanches.utils.MyExpandableListAdapter;

/**
 * Created by Richardson on 01/06/2015.
 */

public class OfertasListExpadableActivity extends Activity{


    SparseArray<GroupProduto> groups = new SparseArray<GroupProduto>();
    private ExpandableListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandable_list);
        createData();
         listView = (ExpandableListView) findViewById(R.id.listView);
        final MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
                groups,this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT);

            }
        });


    }

    public static  void forwardNextActivity(View v){
        Intent intent = new Intent(v.getContext(), PedidosListActivity.class);
        //       intent.putExtra("produto", (java.io.Serializable) map);

        v.getContext().startActivity(intent);
    }

    public void createData() {

        ProdutoDAO produtoDAO = new ProdutoDAO(OfertasListExpadableActivity.this);
        produtoDAO.open();
        List<Produto> produtos  = produtoDAO.consultarTodosProduto();
        produtoDAO.close();

        int j = 0;
        GroupProduto group = null;

        String temp = "";
            for (int i = 0; i < produtos.size(); i++) {

                if(!temp.equals(produtos.get(i).getCATEGORIA())){
                    temp = produtos.get(i).getCATEGORIA();

                    group = new GroupProduto(temp);
                }else{
                    if(i > 0){
                        groups.append(j, group);
                        j++;
                    }
                }

                group.children.add(produtos.get(i));
            }

    }



}
