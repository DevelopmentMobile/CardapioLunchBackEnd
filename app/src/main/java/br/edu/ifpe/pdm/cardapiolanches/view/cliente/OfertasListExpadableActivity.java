package br.edu.ifpe.pdm.cardapiolanches.view.cliente;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ExpandableListView;

/**
 * Created by Richardson on 01/06/2015.
 */


import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;
import br.edu.ifpe.pdm.cardapiolanches.dao.ProdutoDAO;

public class OfertasListExpadableActivity extends Activity{


    SparseArray<Group> groups = new SparseArray<Group>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandable_list);
        createData();
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
                groups);
        listView.setAdapter(adapter);
    }

    public void createData() {

        ProdutoDAO produtoDAO = new ProdutoDAO(OfertasListExpadableActivity.this);
        produtoDAO.open();
        List<Produto> produtos  = produtoDAO.consultarTodosProduto();

        int j = 0;
        Group group = null;

        String temp = "";
            for (int i = 0; i < produtos.size(); i++) {

                if(!temp.equals(produtos.get(i).getCATEGORIA())){
                    temp = produtos.get(i).getCATEGORIA();

                    group = new Group(temp);
                }else{
                    if(i > 0){
                        groups.append(j, group);
                        j++;
                    }
                }

                group.children.add(produtos.get(i).getNOME());
            }

        produtoDAO.close();
    }
}
