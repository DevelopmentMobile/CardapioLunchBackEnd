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




}
