package br.edu.ifpe.pdm.cardapiolanches.view.admin.crud;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.dao.DatabaseHelper;

/**
 * Created by Richardson on 31/05/2015.
 */
public class ProdutoActivityCRUD extends Activity {

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

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Produto.NOME, nome_produto);
        values.put(DatabaseHelper.Produto.CATEGORIA, categoria);
        values.put(DatabaseHelper.Produto.DESCRICAO,descricao );
        values.put(DatabaseHelper.Produto.NOME_IMAGEM,nome_imagem );
        values.put(DatabaseHelper.Produto.PRECO,preco );
        values.put(DatabaseHelper.Produto.TEMPO_PRONTO_PRODUTO,tempo_pronto_produto );
        values.put(DatabaseHelper.Produto.UNIDADE_ESTOQUE,unidade_estoque );

        long newId = db.insert(DatabaseHelper.Produto.TABELA, null, values);
        Toast toast = Toast.makeText(this,
                "Registro adicionado. ID = " + newId, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void buttonUpdateClick(View view) {


        int unidade_estoque = Integer.parseInt(
                ((EditText) findViewById(R.id.unidade_estoque)).getText().toString());
        String nome =    ((EditText)findViewById(R.id.nome)).getText().toString();
        float preco =   Float.parseFloat(((EditText) findViewById(R.id.preco)).getText().toString());
        int tempo_pronto_produto =    Integer.parseInt(((EditText) findViewById(R.id.tempo_pronto_produto)).getText().toString());
        String categoria =    ((EditText)findViewById(R.id.categoria)).getText().toString();
        String nome_imagem =    ((EditText)findViewById(R.id.nome_imagem)).getText().toString();
        String nome_produto =    ((EditText)findViewById(R.id.nome)).getText().toString();
        String descricao =    ((EditText)findViewById(R.id.descricao)).getText().toString();



        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Produto.NOME, nome_produto);
        values.put(DatabaseHelper.Produto.CATEGORIA, categoria);
        values.put(DatabaseHelper.Produto.DESCRICAO,descricao );
        values.put(DatabaseHelper.Produto.NOME_IMAGEM,nome_imagem );
        values.put(DatabaseHelper.Produto.PRECO,preco );
        values.put(DatabaseHelper.Produto.TEMPO_PRONTO_PRODUTO,tempo_pronto_produto );
        values.put(DatabaseHelper.Produto.UNIDADE_ESTOQUE,unidade_estoque );

        String selection = DatabaseHelper.Produto.NOME + " LIKE ?";
        String[] selectionArgs = {nome_produto + "" };
        int count = db.update(DatabaseHelper.Produto.TABELA, values, selection, selectionArgs);
        Toast toast = Toast.makeText(this,
                "Registros atualizados: " + count, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void buttonDeleteClick(View view) {

        String nome_produto =    ((EditText)findViewById(R.id.nome)).getText().toString();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = DatabaseHelper.Produto.NOME + " LIKE ?";
        String[] selectionArgs = { nome_produto + "" };
        int count = db.delete(DatabaseHelper.Produto.TABELA, selection, selectionArgs);
        Toast toast = Toast.makeText(this,
                "Registros deletados: " + count, Toast.LENGTH_SHORT);
        toast.show();
    }


    public void buttonQueryClick(View view) {

        String nome_produto =    ((EditText)findViewById(R.id.nome)).getText().toString();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { DatabaseHelper.Produto._ID,
                DatabaseHelper.Produto.CATEGORIA,
                DatabaseHelper.Produto.DESCRICAO,
                DatabaseHelper.Produto.NOME,
                DatabaseHelper.Produto.NOME_IMAGEM,
                DatabaseHelper.Produto.PRECO,
                DatabaseHelper.Produto.TEMPO_PRONTO_PRODUTO,
                DatabaseHelper.Produto.UNIDADE_ESTOQUE,
                };
        String selection = DatabaseHelper.Produto.NOME + " LIKE ? ";
        String[] selectionArgs = {nome_produto + "%"};
        String sortOrder = DatabaseHelper.Produto.NOME + " DESC";
        Cursor c = db.query(DatabaseHelper.Produto.TABELA,
                projection,
                selection,
                selectionArgs,
                null, null,
                sortOrder);
        ArrayList<CharSequence> data = new ArrayList<CharSequence>();
        c.moveToFirst();
        String entry = " | " +  DatabaseHelper.Produto._ID + " | " +
                DatabaseHelper.Produto.CATEGORIA+ " | " +
                DatabaseHelper.Produto.DESCRICAO+ " | " +
                DatabaseHelper.Produto.NOME+ " | " +
                DatabaseHelper.Produto.NOME_IMAGEM+ " | " +
                DatabaseHelper.Produto.PRECO+ " | " +
                DatabaseHelper.Produto.TEMPO_PRONTO_PRODUTO+ " | " +
                DatabaseHelper.Produto.UNIDADE_ESTOQUE + " | ";

        while (!c.isAfterLast()) {
             entry += "\n | " + c.getInt(c.getColumnIndex( DatabaseHelper.Produto._ID)) + " | ";
            entry += c.getString(c.getColumnIndex( DatabaseHelper.Produto.CATEGORIA) )  + " | ";
            entry += c.getString(c.getColumnIndex( DatabaseHelper.Produto.DESCRICAO))  + " | ";
            entry += c.getString(c.getColumnIndex( DatabaseHelper.Produto.NOME)) + " | ";
            entry += c.getString(c.getColumnIndex( DatabaseHelper.Produto.NOME_IMAGEM))  + " | ";
            entry += c.getFloat(                    c.getColumnIndex( DatabaseHelper.Produto.PRECO)) + " | ";
            entry += c.getInt(                    c.getColumnIndex( DatabaseHelper.Produto.TEMPO_PRONTO_PRODUTO))  + " | ";
            entry += c.getInt(                    c.getColumnIndex( DatabaseHelper.Produto.UNIDADE_ESTOQUE))  + " | ";


            data.add(entry);
            c.moveToNext();
        }
        Intent intent = new Intent(this, QueryResultActivity.class);
        intent.putCharSequenceArrayListExtra("data", data);
        startActivity(intent);
    }



}
