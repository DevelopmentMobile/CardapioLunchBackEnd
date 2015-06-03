package br.edu.ifpe.pdm.cardapiolanches.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.bean.Pacote;

/**
 * Created by Richardson on 30/05/2015.
 */
public class PacoteDAO {

    public static final String MY_TAG = "the_custom_message";
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public PacoteDAO(Context context){

        helper = new DatabaseHelper(context);

        Log.v(MY_TAG, "PacoteDao Build");

    }

   /* private SQLiteDatabase getDb() {
        if (db == null) {

            db = helper.getWritableDatabase();
        }
        return db;
    }*/

    public void open() throws SQLException {


        db = helper.getWritableDatabase();
        //  db.execSQL("DROP TABLE IF EXISTS Pacote" );
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
        Log.v(MY_TAG, "Close Database PacoteDAO");
        helper.close();
    }

public long criarPacote(Pacote pacote){


    ContentValues values = new ContentValues();
    values.put(DatabaseHelper.Pacote.NOME_PACOTE, pacote.getNOME_PACOTE());
    values.put(DatabaseHelper.Pacote.DESCRICAO_PACOTE, pacote.getDESCRICAO_PACOTE());
    values.put(DatabaseHelper.Pacote.TIPO_PACOTE, pacote.getTIPO_PACOTE() );

  return db.insert(DatabaseHelper.Pacote.TABELA, null, values);
}

    public long atualizarPacote(Pacote pacote){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Pacote.NOME_PACOTE, pacote.getNOME_PACOTE());
        values.put(DatabaseHelper.Pacote.DESCRICAO_PACOTE,pacote.getDESCRICAO_PACOTE() );
        values.put(DatabaseHelper.Pacote.TIPO_PACOTE, pacote.getTIPO_PACOTE() );
        //values.put(DatabaseHelper.Pacote.PRECO, pacote );

        String selection = DatabaseHelper.Pacote.NOME_PACOTE + " LIKE ?";
        String[] selectionArgs = {pacote.getNOME_PACOTE() + "" };
        int count = db.update(DatabaseHelper.Pacote.TABELA, values, selection, selectionArgs);
        return count;
    }

    public long deletarPacote(Pacote pacote){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Pacote.NOME_PACOTE, pacote.getNOME_PACOTE());
        values.put(DatabaseHelper.Pacote.DESCRICAO_PACOTE,pacote.getDESCRICAO_PACOTE() );
        values.put(DatabaseHelper.Pacote.TIPO_PACOTE, pacote.getTIPO_PACOTE() );
        //values.put(DatabaseHelper.Pacote.PRECO, pacote );

        String selection = DatabaseHelper.Pacote.NOME_PACOTE + " LIKE ?";
        String[] selectionArgs = {pacote.getNOME_PACOTE() + "" };

        int count = db.delete(DatabaseHelper.Pacote.TABELA, selection, selectionArgs);
        return count;
    }


    private Pacote cursorToPacote(Cursor cursor) {
        Pacote Pacote = new Pacote();
        Pacote.set_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pacote._ID)));
        Pacote.setNOME_PACOTE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Pacote.NOME_PACOTE)));
        Pacote.setDESCRICAO_PACOTE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Pacote.DESCRICAO_PACOTE)));
        Pacote.setTIPO_PACOTE(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pacote.TIPO_PACOTE)));

        return Pacote;
    }


    public List<Pacote> consultarTodosPacote() {
        // Cursor cursor = db.query(DatabaseHelper.Pacote.TABELA, DatabaseHelper.Pacote.COLUNAS,null,null,null,null,null);

        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Pacote.TABELA, null);

        List<Pacote> PacoteList = new ArrayList<Pacote>();

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


    public List<Pacote> consultarTodosPacoteMaiorPreco() {
        // Cursor cursor = db.query(DatabaseHelper.Pacote.TABELA, DatabaseHelper.Pacote.COLUNAS,null,null,null,null,null);

        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Pacote.TABELA + " ORDER BY " +  DatabaseHelper.Pacote.PRECO + " DESC , " + DatabaseHelper.Pacote.NOME_PACOTE + " ASC ", null);

        List<Pacote> PacoteList = new ArrayList<Pacote>();

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



    public List<Pacote> consultarTodosPacoteMenorPreco() {
        // Cursor cursor = db.query(DatabaseHelper.Pacote.TABELA, DatabaseHelper.Pacote.COLUNAS,null,null,null,null,null);

        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Pacote.TABELA + " ORDER BY " +  DatabaseHelper.Pacote.PRECO + " ASC , " + DatabaseHelper.Pacote.NOME_PACOTE + " ASC ", null);

        List<Pacote> PacoteList = new ArrayList<Pacote>();

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


    public List<Pacote> consultarPacotePorPedido(String idPedido){
        String[] whereArgs = new String[]{idPedido};
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Pacote.TABELA + " INNER JOIN " +
                DatabaseHelper.Pedido.TABELA + " ON "+    DatabaseHelper.Pacote.TABELA + "." + DatabaseHelper.Pacote._ID +
                "="+DatabaseHelper.Pedido._ID + " WHERE " + DatabaseHelper.Pedido.TABELA + "." + DatabaseHelper.Pedido._ID , whereArgs);

        List<Pacote> PacoteList = new ArrayList<Pacote>();

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
