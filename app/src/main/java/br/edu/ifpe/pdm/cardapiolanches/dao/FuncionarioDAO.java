package br.edu.ifpe.pdm.cardapiolanches.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.bean.Funcionario;

/**
 * Created by Richardson on 28/05/2015.
 */
public class FuncionarioDAO {
    public static final String MY_TAG = "the_custom_message";
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public FuncionarioDAO(Context context){
      //context.deleteDatabase("cardapioapp.db");
      //context.deleteDatabase("cardapioapp");
        helper = new DatabaseHelper(context);

        Log.v(MY_TAG, "FuncionarioDao Build");

    }

    public void open() throws SQLException {

        db = helper.getWritableDatabase();
        //  db.execSQL("DROP TABLE IF EXISTS Funcionario" );
        //helper.onCreate(db);
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
        Log.v(MY_TAG, "read Database");
    }



    public void close()
    {
        Log.v(MY_TAG, "Close Database FuncionarioDAO");
        helper.close();
    }



    public Funcionario consultarFuncionarioLogin(String login){
        Log.v(MY_TAG, "consultarFuncinarioLogin FuncionarioDAO");
        Cursor cursor = db.query(DatabaseHelper.Funcionario.TABELA, DatabaseHelper.Funcionario.COLUNAS
                , DatabaseHelper.Funcionario.LOGIN + " = " + login, null,
                null, null, null);
        cursor.moveToFirst();
        Funcionario newUser = cursorToFuncionario(cursor);
        cursor.close();
        return  newUser;
    }

    public long criarUsuario(Funcionario usuario) {
        Log.v(MY_TAG, "criarFuncionario FuncionarioDAO");

        ContentValues values = new ContentValues();


        values.put(DatabaseHelper.Funcionario.LOGIN, usuario.getLOGIN());
        values.put(DatabaseHelper.Funcionario.SENHA, usuario.getSENHA());
        values.put(DatabaseHelper.Funcionario.TIPO_FUNCIONARIO, usuario.getTIPO_FUNCIONARIO());

        long insertId = db.insert(DatabaseHelper.Funcionario.TABELA, null,
                values);
        //db.close();
        return insertId;


    }

    private Funcionario cursorToFuncionario(Cursor cursor) {
        Log.v(MY_TAG, "cursorToFuncionario UsuarioDAO");
        Funcionario Funcionario = new Funcionario();
        Funcionario.set_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Funcionario._ID)));
        Funcionario.setLOGIN(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Funcionario.LOGIN)));
        Funcionario.setSENHA(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Funcionario.SENHA)));
        Funcionario.setTIPO_FUNCIONARIO(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Funcionario.TIPO_FUNCIONARIO)));

        return Funcionario;
    }


    public Funcionario consultarFuncionarioPorLoginSenha(String login, String senha) {
        Log.v(MY_TAG, "consultarFuncionarioPorLoginSenha");

        //Cursor cursor = db.query(DatabaseHelper.Funcionario.TABELA, DatabaseHelper.Funcionario.COLUNAS, DatabaseHelper.Funcionario.LOGIN + " = " + login +" AND "+ DatabaseHelper.Funcionario.SENHA + " = " + senha, null,null, null, null);
        String[] whereArgs = new String[]{login, senha};
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.Funcionario.TABELA + " WHERE LOGIN = ? AND SENHA  = ?", whereArgs);
        Funcionario newUser = null;

        if (cursor.getCount() >0 && cursor != null){
            cursor.moveToFirst();

            newUser = cursorToFuncionario(cursor);
            cursor.close();

        }
        return  newUser;
    }



    public List<Funcionario> consultarTodosFuncionario() {
        Log.v(MY_TAG,"consultarTodosFuncionario ");
        // Cursor cursor = db.query(DatabaseHelper.Funcionario.TABELA, DatabaseHelper.Funcionario.COLUNAS,null,null,null,null,null);
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Funcionario.TABELA, null);

        List<Funcionario> funcionarioList = new ArrayList<Funcionario>();

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
    


}
