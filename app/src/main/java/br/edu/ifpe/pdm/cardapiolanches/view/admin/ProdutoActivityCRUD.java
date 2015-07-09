package br.edu.ifpe.pdm.cardapiolanches.view.admin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;
import br.edu.ifpe.pdm.cardapiolanches.dao.ProdutoListener;
import br.edu.ifpe.pdm.cardapiolanches.dao.ProdutoTask;

/**
 * Created by Richardson on 31/05/2015.
 */
public class ProdutoActivityCRUD extends Activity implements ProdutoListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crud_produto_activity);
      /*  DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS produto" );
        db.execSQL("CREATE TABLE produto (" +
                "    _id integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "    unidade_estoque integer NOT NULL," +
                "    nome text UNIQUE," +
                "    preco decimal(6,2) NOT NULL," +
                "    descricao text," +
                "    nome_imagem text," +
                "    tempo_pronto_produto integer NOT NULL," +
                "    categoria text NOT NULL" +
                ");");
            db.close();
        dbHelper.close();
        */
    }


    public void buttonInsertClick(View view) {

        int unidade_estoque = Integer.parseInt(
                ((EditText) findViewById(R.id.unidade_estoque)).getText().toString());
        float preco =   Float.parseFloat(((EditText) findViewById(R.id.preco)).getText().toString());
        int tempo_pronto_produto =    Integer.parseInt(((EditText) findViewById(R.id.tempo_pronto_produto)).getText().toString());
        String categoria =    ((EditText)findViewById(R.id.categoria)).getText().toString();
        String nome_imagem =    ((EditText)findViewById(R.id.nome_imagem)).getText().toString();
        String nome_produto =    ((EditText)findViewById(R.id.nome)).getText().toString();
        String descricao =    ((EditText)findViewById(R.id.descricao)).getText().toString();

        final Produto[] arrayProduto =  new Produto[1];
   final      Produto produto =  new Produto(
                unidade_estoque,
                nome_produto ,
                preco,
                descricao,
               nome_imagem,
                tempo_pronto_produto,
               categoria);

        produto.setACAO("inserir");
/*
        final Produto produto =  new Produto(
                1,
                "1" ,
                (float) 1.5,
                "1",
                "1",
                1,
                "1");
*/



        arrayProduto[0] = produto;
        new ProdutoTask(this).execute(arrayProduto);


    }

    public void buttonUpdateClick(View view) {


        int unidade_estoque = Integer.parseInt(
                ((EditText) findViewById(R.id.unidade_estoque)).getText().toString());
        float preco =   Float.parseFloat(((EditText) findViewById(R.id.preco)).getText().toString());
        int tempo_pronto_produto =    Integer.parseInt(((EditText) findViewById(R.id.tempo_pronto_produto)).getText().toString());
        String categoria =    ((EditText)findViewById(R.id.categoria)).getText().toString();
        String nome_imagem =    ((EditText)findViewById(R.id.nome_imagem)).getText().toString();
        String nome_produto =    ((EditText)findViewById(R.id.nome)).getText().toString();
        String descricao =    ((EditText)findViewById(R.id.descricao)).getText().toString();

        final Produto[] arrayProduto =  new Produto[1];
        final      Produto produto =  new Produto(
                unidade_estoque,
                nome_produto ,
                preco,
                descricao,
                nome_imagem,
                tempo_pronto_produto,
                categoria);

        produto.setACAO("atualizar");
/*
        final Produto produto =  new Produto(
                1,
                "1" ,
                (float) 1.5,
                "1",
                "1",
                1,
                "1");
*/



        arrayProduto[0] = produto;
        new ProdutoTask(this).execute(arrayProduto);
    }

    public void buttonDeleteClick(View view) {

        int unidade_estoque = Integer.parseInt(
                ((EditText) findViewById(R.id.unidade_estoque)).getText().toString());
        float preco =   Float.parseFloat(((EditText) findViewById(R.id.preco)).getText().toString());
        int tempo_pronto_produto =    Integer.parseInt(((EditText) findViewById(R.id.tempo_pronto_produto)).getText().toString());
        String categoria =    ((EditText)findViewById(R.id.categoria)).getText().toString();
        String nome_imagem =    ((EditText)findViewById(R.id.nome_imagem)).getText().toString();
        String nome_produto =    ((EditText)findViewById(R.id.nome)).getText().toString();
        String descricao =    ((EditText)findViewById(R.id.descricao)).getText().toString();

        final Produto[] arrayProduto =  new Produto[1];
        final      Produto produto =  new Produto(
                unidade_estoque,
                nome_produto ,
                preco,
                descricao,
                nome_imagem,
                tempo_pronto_produto,
                categoria);

        produto.setACAO("deletar");
/*
        final Produto produto =  new Produto(
                1,
                "1" ,
                (float) 1.5,
                "1",
                "1",
                1,
                "1");
*/



        arrayProduto[0] = produto;
        new ProdutoTask(this).execute(arrayProduto);
    }


    public void buttonQueryClick(View view) {

        int unidade_estoque = Integer.parseInt(
                ((EditText) findViewById(R.id.unidade_estoque)).getText().toString());
        float preco =   Float.parseFloat(((EditText) findViewById(R.id.preco)).getText().toString());
        int tempo_pronto_produto =    Integer.parseInt(((EditText) findViewById(R.id.tempo_pronto_produto)).getText().toString());
        String categoria =    ((EditText)findViewById(R.id.categoria)).getText().toString();
        String nome_imagem =    ((EditText)findViewById(R.id.nome_imagem)).getText().toString();
        String nome_produto =    ((EditText)findViewById(R.id.nome)).getText().toString();
        String descricao =    ((EditText)findViewById(R.id.descricao)).getText().toString();

        final Produto[] arrayProduto =  new Produto[1];
        final      Produto produto =  new Produto(
                unidade_estoque,
                nome_produto ,
                preco,
                descricao,
                nome_imagem,
                tempo_pronto_produto,
                categoria);

        produto.setACAO("consultarnome");
/*
        final Produto produto =  new Produto(
                1,
                "1" ,
                (float) 1.5,
                "1",
                "1",
                1,
                "1");
*/



        arrayProduto[0] = produto;
        new ProdutoTask(this).execute(arrayProduto);
    }




    @Override
    public void showProduto(List<Produto> produtos) {
        if(produtos.get(0) != null) {
            Toast.makeText(this, produtos.get(0).toString(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Produto Não Cadastrado!!", Toast.LENGTH_SHORT).show();
        }
    }
}
