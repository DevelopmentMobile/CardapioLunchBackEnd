package br.edu.ifpe.pdm.cardapiolanches.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;
import br.edu.ifpe.pdm.cardapiolanches.bean.Pedido;

/**
 * Created by Richardson on 04/05/2015.
 */
public class ProdutoDAO {

    public static final String MY_TAG = "the_custom_message";
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public ProdutoDAO(Context context){

        helper = new DatabaseHelper(context);

        Log.v(MY_TAG, "ProdutoDao Build");

    }

    public void open() throws SQLException {


        db = helper.getWritableDatabase();
        //  db.execSQL("DROP TABLE IF EXISTS Produto" );
        //  helper.onCreate(db);
        //onUpgrade(db,0,1);
        //   helper.onUpgrade(db,0,5);


        Log.v(MY_TAG, "WriteDatabse");
        System.out.println("Status Banco de Dados " + db.isOpen());
    }

    public void write() throws SQLException{
        db = helper.getWritableDatabase();

        Log.v(MY_TAG, "WriteDatabse");
    }
    public void read() throws SQLException{
        db = helper.getReadableDatabase();
        System.out.println("Status Banco de Dados " + db.isOpen());
        // helper.onUpgrade(db, 1, 2);
        Log.v(MY_TAG, "read Database");
    }



    public void close()
    {
        Log.v(MY_TAG, "Close Database ProdutoDAO");
        helper.close();
    }



    public Produto consultarProdutoNome(String nomeProduto){
        Log.v(MY_TAG, "consultarFuncinarioLogin ProdutoDAO");
        Cursor cursor = db.query(DatabaseHelper.Produto.TABELA, DatabaseHelper.Produto.COLUNAS
                , DatabaseHelper.Produto._ID + " = " + nomeProduto, null,
                null, null, null);
        cursor.moveToFirst();
        Produto newUser = cursorToProduto(cursor);
        cursor.close();
        return  newUser;
    }

    public long criarProduto(Produto produto) {
        Log.v(MY_TAG, "criarProduto ProdutoDAO");

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Produto.NOME, produto.getNOME() );
        values.put(DatabaseHelper.Produto.CATEGORIA, produto.getCATEGORIA());
        values.put(DatabaseHelper.Produto.DESCRICAO, produto.getDESCRICAO() );
        values.put(DatabaseHelper.Produto.NOME_IMAGEM, produto.getNOME_IMAGEM());
        values.put(DatabaseHelper.Produto.PRECO, produto.getPRECO() );
        values.put(DatabaseHelper.Produto.TEMPO_PRONTO_PRODUTO, produto.getTEMPO_PRONTO_PRODUTO() );
        values.put(DatabaseHelper.Produto.UNIDADE_ESTOQUE, produto.getUNIDADE_ESTOQUE() );


        long insertId = db.insert(DatabaseHelper.Produto.TABELA, null,
                values);
        //db.close();
        return insertId;


    }

    private Produto cursorToProduto(Cursor cursor) {
        Log.v(MY_TAG, "cursorToProduto ProdutoDAO");
        Produto Produto = new Produto();
        Produto.set_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Produto._ID)));
        Produto.setNOME_IMAGEM(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.NOME_IMAGEM)));
        Produto.setNOME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.NOME)));
        Produto.setCATEGORIA(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.CATEGORIA)));
        Produto.setDESCRICAO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.DESCRICAO)));
        Produto.setPRECO(cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.Produto.PRECO)));
        Produto.setTEMPO_PRONTO_PRODUTO(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Produto.TEMPO_PRONTO_PRODUTO)));
        Produto.setUNIDADE_ESTOQUE(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Produto.UNIDADE_ESTOQUE)));


        return Produto;
    }




    public List<Produto> consultarTodosProduto() {
        Log.v(MY_TAG,"consultarTodosProduto ");
        // Cursor cursor = db.query(DatabaseHelper.Produto.TABELA, DatabaseHelper.Produto.COLUNAS,null,null,null,null,null);
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Produto.TABELA + " ORDER BY " + DatabaseHelper.Produto.CATEGORIA + " ASC , " +  DatabaseHelper.Produto.NOME + " DESC " , null);

        List<Produto> ProdutoList = new ArrayList<Produto>();

        if (cursor != null) {

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






    public List<Produto> consultarTodosProdutosOrdenadoCategoriaMenorPreco() {
        Log.v(MY_TAG,"consultarTodosProdutosOrdenadoCategoriaMenorPreco ");
        // Cursor cursor = db.query(DatabaseHelper.Produto.TABELA, DatabaseHelper.Produto.COLUNAS,null,null,null,null,null);
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Produto.TABELA + " ORDER BY " + DatabaseHelper.Produto.CATEGORIA + " ASC , " + DatabaseHelper.Produto.PRECO + " ASC , "  + DatabaseHelper.Produto.NOME + " ASC " , null);

        List<Produto> ProdutoList = new ArrayList<Produto>();

        if (cursor.getCount() > 0) {

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


    public List<Produto> consultarTodosProdutosOrdenadoCategoriaMaiorPreco() {
        Log.v(MY_TAG,"consultarTodosProdutosOrdenadoCategoriaMaiorPreco ");
        // Cursor cursor = db.query(DatabaseHelper.Produto.TABELA, DatabaseHelper.Produto.COLUNAS,null,null,null,null,null);
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Produto.TABELA + " ORDER BY " + DatabaseHelper.Produto.CATEGORIA + " DESC , " + DatabaseHelper.Produto.PRECO + " ASC , "  + DatabaseHelper.Produto.NOME + " ASC " , null);

        List<Produto> ProdutoList = new ArrayList<Produto>();

        if (cursor.getCount() > 0)  {

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

    public List<Produto> consultarProdutoPorPacote(String idPacote){
    String[] whereArgs = new String[]{idPacote};
    Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Produto.TABELA + " INNER JOIN " +
            DatabaseHelper.PacoteProduto.TABELA + " ON "+    DatabaseHelper.Produto.TABELA + "." + DatabaseHelper.Produto._ID +
            "="+DatabaseHelper.PacoteProduto._ID + " WHERE " + DatabaseHelper.PacoteProduto.TABELA + "." + DatabaseHelper.PacoteProduto._ID , whereArgs);

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

    public List<Produto> consultarProdutoPorPedido(String idPedido){
        String[] whereArgs = new String[]{idPedido};
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Produto.TABELA + " INNER JOIN " +
                DatabaseHelper.Pedido.TABELA + " ON "+    DatabaseHelper.Produto.TABELA + "." + DatabaseHelper.Produto._ID +
                "="+DatabaseHelper.Pedido._ID + " WHERE " + DatabaseHelper.Pedido.TABELA + "." + DatabaseHelper.Pedido._ID , whereArgs);

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
