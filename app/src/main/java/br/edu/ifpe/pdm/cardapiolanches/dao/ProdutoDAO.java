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

   /* private SQLiteDatabase getDb() {
        if (db == null) {

            db = helper.getWritableDatabase();
        }
        return db;
    }*/

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



    public Produto consultarProdutoLogin(String login){
        Log.v(MY_TAG, "consultarFuncinarioLogin ProdutoDAO");
        Cursor cursor = db.query(DatabaseHelper.Produto.TABELA, DatabaseHelper.Produto.COLUNAS
                , DatabaseHelper.Produto._ID + " = " + login, null,
                null, null, null);
        cursor.moveToFirst();
        Produto newUser = cursorToProduto(cursor);
        cursor.close();
        return  newUser;
    }

    public long criarProduto(Produto Produto) {
        Log.v(MY_TAG, "criarProduto ProdutoDAO");

        ContentValues values = new ContentValues();




        long insertId = db.insert(DatabaseHelper.Produto.TABELA, null,
                values);
        //db.close();
        return insertId;


    }

    private Produto cursorToProduto(Cursor cursor) {
        Log.v(MY_TAG, "cursorToProduto ProdutoDAO");
        Produto Produto = new Produto();
        Produto.set_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Produto._ID)));


        return Produto;
    }




    public List<Produto> consultarTodosProduto() {
        Log.v(MY_TAG,"consultarTodosProduto ");
        // Cursor cursor = db.query(DatabaseHelper.Produto.TABELA, DatabaseHelper.Produto.COLUNAS,null,null,null,null,null);
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Produto.TABELA, null);

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


}
