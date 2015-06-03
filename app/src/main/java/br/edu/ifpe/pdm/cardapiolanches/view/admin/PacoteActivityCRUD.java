package br.edu.ifpe.pdm.cardapiolanches.view.admin;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;
import br.edu.ifpe.pdm.cardapiolanches.dao.DatabaseHelper;

/**
 * Created by Richardson on 31/05/2015.
 */
public class PacoteActivityCRUD extends Activity {


    private Spinner tipPacote;
    private Spinner sProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crud_pacote_activity);
        listProduto = new ArrayList<Produto>();

        ArrayAdapter<Produto> arrayAdapterProduto = new ArrayAdapter(this, android.R.layout.simple_spinner_item , consultarTodosProduto());
        sProduto = (Spinner) findViewById(R.id.produto_pacote);
        sProduto.setAdapter(arrayAdapterProduto);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.tipo_pacote,//
                android.R.layout.simple_spinner_item); //passando o contexto atual, o identifiador do array de op ções que defiimos no strings.xml e o id do layout que será utilizado para apresentar as op ções.
        tipPacote = (Spinner) findViewById(R.id.tipo_pacote);
        tipPacote.setAdapter(adapter);


     /* DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
       db.execSQL("DROP TABLE IF EXISTS pacote" );
        db.execSQL("DROP TABLE IF EXISTS  pacote_produto" );



                 db.execSQL("CREATE TABLE pacote (" +
                "    _id integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "    nome_pacote text NOT NULL, " +
                "    tipo_pacote integer NOT NULL, " +
                "    descricao_pacote text, " +
                "    preco decimal(6,2) NOT NULL " +
                ");");

                 db.execSQL("CREATE TABLE pacote_produto (" +
                "    _id integer NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "   pacote_id integer NOT NULL, " +
                "   produto_id integer NOT NULL, " +
                "   FOREIGN KEY (produto_id) REFERENCES produto (_id), " +
                "   FOREIGN KEY (pacote_id) REFERENCES pacote (_id) " +
                ");");




            db.close();
        dbHelper.close();*/



    }


    public void buttonInsertClick(View view) {

        int tipo_pacote =    ((Spinner) findViewById(R.id.tipo_pacote)).getSelectedItemPosition() + 1;
        String nome_pacote=    ((EditText)findViewById(R.id.nome_pacote)).getText().toString();
        String descricao =    ((EditText)findViewById(R.id.descricao_pacote)).getText().toString();
        String preco =    ((EditText)findViewById(R.id.preco_pacote)).getText().toString();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Pacote.NOME_PACOTE, nome_pacote);
        values.put(DatabaseHelper.Pacote.DESCRICAO_PACOTE, descricao);
        values.put(DatabaseHelper.Pacote.TIPO_PACOTE, tipo_pacote );
        values.put(DatabaseHelper.Pacote.PRECO, preco );

        long newId = db.insert(DatabaseHelper.Pacote.TABELA, null, values);
        Toast toast = Toast.makeText(this,
                "Registro adicionado. ID = " + newId, Toast.LENGTH_SHORT);
        toast.show();
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        for(Produto produto: listProduto){
            values.put(DatabaseHelper.PacoteProduto.PACOTE_ID, (int)newId );
            values.put(DatabaseHelper.PacoteProduto.PRODUTO_ID, produto.get_ID());
            long newIdPacoteProduto = db.insert(DatabaseHelper.PacoteProduto.TABELA, null, values);
            toast = Toast.makeText(this,
                    "Registro adicionado. ID = " + newIdPacoteProduto, Toast.LENGTH_SHORT);
            toast.show();

        }

        listProduto = new ArrayList<Produto>();

    }

    public void buttonUpdateClick(View view) {


        int tipo_pacote =    ((Spinner) findViewById(R.id.tipo_pacote)).getSelectedItemPosition() + 1;
        String nome_pacote=    ((EditText)findViewById(R.id.nome_pacote)).getText().toString();
        String descricao =    ((EditText)findViewById(R.id.descricao_pacote)).getText().toString();
        String preco =    ((EditText)findViewById(R.id.preco_pacote)).getText().toString();


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values =   new ContentValues();
        values.put(DatabaseHelper.Pacote.NOME_PACOTE, nome_pacote);
        values.put(DatabaseHelper.Pacote.DESCRICAO_PACOTE, descricao);
        values.put(DatabaseHelper.Pacote.TIPO_PACOTE, tipo_pacote );
        values.put(DatabaseHelper.Pacote.PRECO, preco );

        String selection = DatabaseHelper.Pacote.NOME_PACOTE + " LIKE ?";
        String[] selectionArgs = {nome_pacote+ "" };
        int count = db.update(DatabaseHelper.Pacote.TABELA, values, selection, selectionArgs);
        Toast toast = Toast.makeText(this,
                "Registros atualizados: " + count, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void buttonDeleteClick(View view) {

        String nome_pacote =    ((EditText)findViewById(R.id.nome_pacote)).getText().toString();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = DatabaseHelper.Pacote.NOME_PACOTE + " LIKE ?";
        String[] selectionArgs = { nome_pacote + "" };
        int count = db.delete(DatabaseHelper.Pacote.TABELA, selection, selectionArgs);
        Toast toast = Toast.makeText(this,
                "Registros deletados: " + count, Toast.LENGTH_SHORT);
        toast.show();
    }


    public void buttonQueryClick(View view) {

        String nome_pacote =    ((EditText)findViewById(R.id.nome_pacote)).getText().toString();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { DatabaseHelper.Pacote._ID,
                DatabaseHelper.Pacote.NOME_PACOTE,
                DatabaseHelper.Pacote.DESCRICAO_PACOTE,
                DatabaseHelper.Pacote.TIPO_PACOTE,
                };
        String selection = DatabaseHelper.Pacote.NOME_PACOTE + " LIKE ? ";
        String[] selectionArgs = {nome_pacote+ "%"};
        String sortOrder = DatabaseHelper.Pacote.NOME_PACOTE + " DESC";
        Cursor c = db.query(DatabaseHelper.Pacote.TABELA,
                projection,
                selection,
                selectionArgs,
                null, null,
                sortOrder);
        ArrayList<CharSequence> data = new ArrayList<CharSequence>();
        c.moveToFirst();
        String entry = " | " +  DatabaseHelper.Pacote._ID + " | " +
                DatabaseHelper.Pacote.NOME_PACOTE+ " | " +
                DatabaseHelper.Pacote.TIPO_PACOTE+ " | "+
                DatabaseHelper.Pacote.DESCRICAO_PACOTE + " | ";


        while (!c.isAfterLast()) {
             entry += "\n | " + c.getInt(c.getColumnIndex( DatabaseHelper.Pacote._ID)) + " | ";
            entry += c.getString(c.getColumnIndex( DatabaseHelper.Pacote.NOME_PACOTE) )  + " | ";
            entry += c.getInt( c.getColumnIndex( DatabaseHelper.Pacote.TIPO_PACOTE))  + " | ";
          //  entry += c.getString(c.getColumnIndex( DatabaseHelper.Pacote.DESCRICAO_PACOTE))  + " | "; //Erro ao buscar  settar

            data.add(entry);
            entry = "";
            c.moveToNext();
        }
        Intent intent = new Intent(this, QueryResultActivity.class);
        intent.putCharSequenceArrayListExtra("data", data);
        startActivity(intent);
    }


    private List<Produto> listProduto;

    public void buttonAddProduto(View view){

    Produto produto  =  (Produto) ((Spinner) findViewById(R.id.produto_pacote)).getSelectedItem();

        listProduto.add(produto);

        Toast toast = Toast.makeText(this,
                "Registro adicionado!\nID = " + produto.get_ID() + "\nNome Produto: " + produto.getNOME(), Toast.LENGTH_SHORT);
        toast.show();
    }

    private Produto cursorToProduto(Cursor cursor) {
        Produto Produto = new Produto();
        Produto.set_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Produto._ID)));
        Produto.setNOME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.NOME)));

        Produto.setPRECO(cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.Produto.PRECO)));
        Produto.setUNIDADE_ESTOQUE(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Produto.UNIDADE_ESTOQUE)));
        Produto.setNOME_IMAGEM(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.NOME_IMAGEM)));
        Produto.setDESCRICAO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.DESCRICAO)));
        Produto.setCATEGORIA(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.CATEGORIA)));
        Produto.setDESCRICAO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.DESCRICAO)));

        return Produto;
    }
    public List<Produto> consultarTodosProduto() {
        // Cursor cursor = db.query(DatabaseHelper.Produto.TABELA, DatabaseHelper.Produto.COLUNAS,null,null,null,null,null);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Produto.TABELA, null);

        List<Produto> ProdutoList = new ArrayList<Produto>();

        if (cursor.getCount() >0 && cursor != null) {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Produto newUser = cursorToProduto(cursor);
                ProdutoList.add(newUser);
                cursor.moveToNext();
            }

            cursor.close();
        }
        return ProdutoList;
    }



}
