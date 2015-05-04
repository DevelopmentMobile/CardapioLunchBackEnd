package br.edu.ifpe.pdm.cardapiolanches.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.bean.Pedidos;
import br.edu.ifpe.pdm.cardapiolanches.dao.DatabaseHelper;

/**
 * Created by Richardson on 04/05/2015.
 */
public class LoginDAO {

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public LoginDAO(Context context){
        helper = new DatabaseHelper(context);
    }
    private SQLiteDatabase getDb() {
        if (db == null) {

            db = helper.getWritableDatabase();
        }
        return db;
    }
    public void close(){
        helper.close();
    }

    public List<Pedidos> listarViagens() {
        Cursor cursor = getDb().query(DatabaseHelper.Pedidos.TABELA,
                DatabaseHelper.Pedidos.COLUNAS,
                null, null, null, null, null);
        List<Pedidos> viagens = new ArrayList<Pedidos>();
        while (cursor.moveToNext()) {
            //Viagem viagem = criarViagem(cursor);
            //viagens.add(viagem);
        }
        cursor.close();
        return viagens;
    }
}
