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
import br.edu.ifpe.pdm.cardapiolanches.bean.Funcionario;
import br.edu.ifpe.pdm.cardapiolanches.bean.Pacote;
import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;
import br.edu.ifpe.pdm.cardapiolanches.dao.DatabaseHelper;

/**
 * Created by Richardson on 31/05/2015.
 */
public class PedidoActivityCRUD extends Activity {


    private Spinner statusPedido;
    private Spinner sProduto;
    private Spinner sPacote;
    private Spinner sFuncionario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crud_pedido_activity);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.status_pedido,//
                android.R.layout.simple_spinner_item); //passando o contexto atual, o identifiador do array de op ções que defiimos no strings.xml e o id do layout que será utilizado para apresentar as op ções.
        statusPedido = (Spinner) findViewById(R.id.status_pedido);
        statusPedido.setAdapter(adapter);


        ArrayAdapter<Produto> arrayAdapterProduto = new ArrayAdapter(this, android.R.layout.simple_spinner_item , consultarTodosProduto());
        sProduto = (Spinner) findViewById(R.id.produto_pedido);
        sProduto.setAdapter(arrayAdapterProduto);



        ArrayAdapter<Pacote> arrayAdapterPacote = new ArrayAdapter(this, android.R.layout.simple_spinner_item, consultarTodosPacote());
        sPacote = (Spinner) findViewById(R.id.pacote_pedido);
        sPacote.setAdapter(arrayAdapterPacote);

        ArrayAdapter<Funcionario> arrayAdapterfuncionario = new ArrayAdapter(this, android.R.layout.simple_spinner_item, consultarTodosFuncionario());
        sFuncionario = (Spinner) findViewById(R.id.funcionario_pedido);
        sFuncionario.setAdapter(arrayAdapterfuncionario);

/*

       DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS pedido" );
      //  db.execSQL("DROP TABLE IF EXISTS pedido_item" );
        db.execSQL("CREATE TABLE pedido (" +
                "   _id integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "    produto_id integer," +
                "    pacote_id integer," +
                "    tempo_total_pedido integer," +
                "    num_mesa integer NOT NULL," +
                "    funcionario_id integer," +
                "    status_pedido integer," +
                "    quantidade integer NOT NULL," +
                "   FOREIGN KEY (produto_id) REFERENCES produto(_id)," +
                "    FOREIGN KEY (pacote_id) REFERENCES pacote (_id)," +
                "   FOREIGN KEY (funcionario_id) REFERENCES funcionario (_id) " +
                ");");
            db.close();
        dbHelper.close();
*/
    }




    public void buttonInsertClick(View view) {
        int quantidade_pedido = Integer.parseInt(
              ((EditText) findViewById(R.id.quantidade_pedido)).getText().toString());
        int tempo_pronto_Pedido =    Integer.parseInt(((EditText) findViewById(R.id.total_pedido)).getText().toString());
        int numero_mesa =    Integer.parseInt(((EditText) findViewById(R.id.numero_mesa)).getText().toString());
        int funcionario =   ((Funcionario) ((Spinner)findViewById(R.id.funcionario_pedido)).getSelectedItem()).get_ID();
        int produto=   ((Produto)  ((Spinner)findViewById(R.id.produto_pedido)).getSelectedItem()).get_ID();
        int pacote =     ((Pacote)((Spinner)findViewById(R.id.pacote_pedido)).getSelectedItem()).get_ID();
        int status =    ((Spinner)findViewById(R.id.status_pedido)).getSelectedItemPosition() +1;

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Pedido.QUANTIDADE, quantidade_pedido);
        values.put(DatabaseHelper.Pedido.TEMPO_TOTAL_PEDIDO, tempo_pronto_Pedido);
        values.put(DatabaseHelper.Pedido.NUM_MESA,numero_mesa );

        if(funcionario > 0) {
            values.put(DatabaseHelper.Pedido.FUNCIONARIO_ID, funcionario);
        }
        if(produto >0 ) {
            values.put(DatabaseHelper.Pedido.PRODUTO_ID, produto);
        }

        if(pacote > 0 ) {
            values.put(DatabaseHelper.Pedido.PACOTE_ID, pacote);
        }
        values.put(DatabaseHelper.Pedido.STATUS_PEDIDO,status );

        long newId = db.insert(DatabaseHelper.Pedido.TABELA, null, values);
        Toast toast = Toast.makeText(this,
                "Registro adicionado. ID = " + newId, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void buttonUpdateClick(View view) {

        int id_pedido = Integer.parseInt(
                ((EditText) findViewById(R.id.id_pedido)).getText().toString());
        int quantidade_pedido = Integer.parseInt(
                ((EditText) findViewById(R.id.quantidade_pedido)).getText().toString());

        int tempo_pronto_Pedido =    Integer.parseInt(((EditText) findViewById(R.id.total_pedido)).getText().toString());
        int numero_mesa =    Integer.parseInt(((EditText) findViewById(R.id.numero_mesa)).getText().toString());
        int funcionario =   ((Funcionario) ((Spinner)findViewById(R.id.funcionario_pedido)).getSelectedItem()).get_ID();
        int produto=   ((Produto)  ((Spinner)findViewById(R.id.produto_pedido)).getSelectedItem()).get_ID();
        int pacote =     ((Pacote)((Spinner)findViewById(R.id.pacote_pedido)).getSelectedItem()).get_ID();
        int status =    ((Spinner)findViewById(R.id.status_pedido)).getSelectedItemPosition() +1;



        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Pedido.QUANTIDADE, quantidade_pedido);
        values.put(DatabaseHelper.Pedido.TEMPO_TOTAL_PEDIDO, tempo_pronto_Pedido);
        values.put(DatabaseHelper.Pedido.NUM_MESA,numero_mesa );
        values.put(DatabaseHelper.Pedido.FUNCIONARIO_ID,funcionario );
        values.put(DatabaseHelper.Pedido.PRODUTO_ID,produto );
        values.put(DatabaseHelper.Pedido.PACOTE_ID,pacote);
        values.put(DatabaseHelper.Pedido.STATUS_PEDIDO,status );

        String selection = DatabaseHelper.Pedido._ID + " LIKE ?";
        String[] selectionArgs = {id_pedido+ "" };
        int count = db.update(DatabaseHelper.Pedido.TABELA, values, selection, selectionArgs);
        Toast toast = Toast.makeText(this,
                "Registros atualizados: " + count, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void buttonDeleteClick(View view) {

        int id_pedido = Integer.parseInt(
                ((EditText) findViewById(R.id.id_pedido)).getText().toString());

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = DatabaseHelper.Pedido._ID + " LIKE ?";
        String[] selectionArgs = {id_pedido+ "" };
        int count = db.delete(DatabaseHelper.Pedido.TABELA, selection, selectionArgs);
        Toast toast = Toast.makeText(this,
                "Registros deletados: " + count, Toast.LENGTH_SHORT);
        toast.show();
    }


    public void buttonQueryClick(View view) {

        String id_pedido =    ((EditText)findViewById(R.id.id_pedido)).getText().toString();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { DatabaseHelper.Pedido._ID,
                DatabaseHelper.Pedido.NUM_MESA,
                        DatabaseHelper.Pedido.STATUS_PEDIDO,
                        DatabaseHelper.Pedido.QUANTIDADE,
                        DatabaseHelper.Pedido.TEMPO_TOTAL_PEDIDO,
                        DatabaseHelper.Pedido.PACOTE_ID,
                        DatabaseHelper.Pedido.PRODUTO_ID,
                        DatabaseHelper.Pedido.FUNCIONARIO_ID
                };
        String selection = DatabaseHelper.Pedido._ID + " LIKE ? ";
        String[] selectionArgs = {id_pedido + "%"};
        String sortOrder = DatabaseHelper.Pedido._ID + " DESC";
        Cursor c = db.query(DatabaseHelper.Pedido.TABELA,
                projection,
                selection,
                selectionArgs,
                null, null,
                sortOrder);
        ArrayList<CharSequence> data = new ArrayList<CharSequence>();
        c.moveToFirst();
        String entry = " | " +  DatabaseHelper.Pedido._ID + " | " +
                DatabaseHelper.Pedido.NUM_MESA+ " | " +
                DatabaseHelper.Pedido.STATUS_PEDIDO+ " | " +
                DatabaseHelper.Pedido.QUANTIDADE+ " | " +
                DatabaseHelper.Pedido.TEMPO_TOTAL_PEDIDO+ " | "+
                DatabaseHelper.Pedido.PACOTE_ID+ " | " +
                DatabaseHelper.Pedido.PRODUTO_ID+ " | " +
                DatabaseHelper.Pedido.FUNCIONARIO_ID+ " | ";




        while (!c.isAfterLast()) {
             entry += "\n | " + c.getInt(c.getColumnIndex( DatabaseHelper.Pedido._ID)) + " | ";

            entry += c.getInt(                    c.getColumnIndex( DatabaseHelper.Pedido.NUM_MESA))  + " | ";
            entry += c.getInt(                    c.getColumnIndex( DatabaseHelper.Pedido.STATUS_PEDIDO))  + " | ";
            entry += c.getInt(                    c.getColumnIndex( DatabaseHelper.Pedido.QUANTIDADE))  + " | ";
            entry += c.getInt(                    c.getColumnIndex( DatabaseHelper.Pedido.TEMPO_TOTAL_PEDIDO))  + " | ";
            entry += c.getInt(                    c.getColumnIndex( DatabaseHelper.Pedido.PACOTE_ID))  + " | ";
            entry += c.getInt(                    c.getColumnIndex( DatabaseHelper.Pedido.PRODUTO_ID))  + " | ";
            entry += c.getInt(                    c.getColumnIndex( DatabaseHelper.Pedido.FUNCIONARIO_ID))  + " | ";
            data.add(entry);
            entry = "";
            c.moveToNext();
        }
        Intent intent = new Intent(this, QueryResultActivity.class);
        intent.putCharSequenceArrayListExtra("data", data);
        startActivity(intent);
    }


    private Funcionario cursorToFuncionario(Cursor cursor) {
        Funcionario Funcionario = new Funcionario();
        Funcionario.set_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Funcionario._ID)));
        Funcionario.setLOGIN(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Funcionario.LOGIN)));
        Funcionario.setSENHA(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Funcionario.SENHA)));
        Funcionario.setTIPO_FUNCIONARIO(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Funcionario.TIPO_FUNCIONARIO)));

        return Funcionario;
    }
    public List<Funcionario> consultarTodosFuncionario() {
        // Cursor cursor = db.query(DatabaseHelper.Funcionario.TABELA, DatabaseHelper.Funcionario.COLUNAS,null,null,null,null,null);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Funcionario.TABELA, null);

        List<Funcionario> funcionarioList = new ArrayList<Funcionario>();

        Funcionario Funcionario = new Funcionario();
        Funcionario.set_ID(-1);
        Funcionario.setLOGIN("Selecione opcao:");
        funcionarioList.add(Funcionario);

        if (cursor.getCount() >0 && cursor != null) {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Funcionario newUser = cursorToFuncionario(cursor);
                funcionarioList.add(newUser);
                cursor.moveToNext();
            }

            cursor.close();
        }
        return funcionarioList;
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
        Produto produto = new Produto();
        produto.set_ID(-1);
        produto.setNOME("Selecione opcao:");
        ProdutoList.add(produto);

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


    private Pacote cursorToPacote(Cursor cursor) {
        Pacote Pacote = new Pacote();
        Pacote.set_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pacote._ID)));
        Pacote.setNOME_PACOTE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Pacote.NOME_PACOTE)));
      //  Pacote.setDESCRICAO_PACOTE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Pacote.DESCRICAO_PACOTE)));
        Pacote.setTIPO_PACOTE(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pacote.TIPO_PACOTE)));

        return Pacote;
    }
    public List<Pacote> consultarTodosPacote() {
        // Cursor cursor = db.query(DatabaseHelper.Pacote.TABELA, DatabaseHelper.Pacote.COLUNAS,null,null,null,null,null);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Pacote.TABELA, null);

        List<Pacote> PacoteList = new ArrayList<Pacote>();

        Pacote Pacote = new Pacote();
        Pacote.set_ID(-1);
        Pacote.setNOME_PACOTE("Selecione opcao:");
        PacoteList.add(Pacote);

        if (cursor.getCount() >0 && cursor != null) {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Pacote newUser = cursorToPacote(cursor);
                PacoteList.add(newUser);
                cursor.moveToNext();
            }

            cursor.close();
        }
        return PacoteList;
    }




}
