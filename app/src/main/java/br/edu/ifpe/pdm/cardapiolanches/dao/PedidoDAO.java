package br.edu.ifpe.pdm.cardapiolanches.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.bean.Pedido;
import br.edu.ifpe.pdm.cardapiolanches.bean.Pedido;

/**
 * Created by Richardson on 04/05/2015.
 */
public class PedidoDAO {

    public static final String MY_TAG = "the_custom_message";
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public PedidoDAO(Context context){

        helper = new DatabaseHelper(context);

        Log.v(MY_TAG, "PedidoDao Build");

    }

   /* private SQLiteDatabase getDb() {
        if (db == null) {

            db = helper.getWritableDatabase();
        }
        return db;
    }*/

    public void open() throws SQLException {


        db = helper.getWritableDatabase();
        //  db.execSQL("DROP TABLE IF EXISTS Pedido" );
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
        Log.v(MY_TAG, "Close Database PedidoDAO");
        helper.close();
    }


public long criarPedido(Pedido pedido) {

    ContentValues values = new ContentValues();
    values.put(DatabaseHelper.Pedido.QUANTIDADE, pedido.getQUANTIDADE());
    values.put(DatabaseHelper.Pedido.TEMPO_TOTAL_PEDIDO, pedido.getTEMPO_TOTAL_PEDIDO());
    values.put(DatabaseHelper.Pedido.NUM_MESA, pedido.getNUM_MESA());
    values.put(DatabaseHelper.Pedido.FUNCIONARIO_ID, pedido.getFUNCIONARIO_ID());
    values.put(DatabaseHelper.Pedido.PRODUTO_ID, pedido.getPRODUTO_ID());
    values.put(DatabaseHelper.Pedido.PACOTE_ID, pedido.getPACOTE_ID());
    values.put(DatabaseHelper.Pedido.STATUS_PEDIDO, pedido.getSTATUS_PEDIDO());

   return db.insert(DatabaseHelper.Pedido.TABELA, null, values);
}

    public void atualizaStatusPedido(int numPedido, int statusPedido){
    ContentValues values = new ContentValues();

    values.put(DatabaseHelper.Pedido.STATUS_PEDIDO,statusPedido );

    String selection = DatabaseHelper.Pedido._ID + " LIKE ?";
    String[] selectionArgs = {numPedido+ "" };
    int count = db.update(DatabaseHelper.Pedido.TABELA, values, selection, selectionArgs);


}

    public int deletarPedido(int id_pedido){

        String selection = DatabaseHelper.Pedido._ID + " LIKE ?";
        String[] selectionArgs = {id_pedido+ "" };
        int count = db.delete(DatabaseHelper.Pedido.TABELA, selection, selectionArgs);

        return count;
    }


    public void atualizaPedido(Pedido pedido){

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Pedido.QUANTIDADE, pedido.getQUANTIDADE() );
        values.put(DatabaseHelper.Pedido.TEMPO_TOTAL_PEDIDO,  pedido.getTEMPO_TOTAL_PEDIDO());
        values.put(DatabaseHelper.Pedido.NUM_MESA,pedido.getNUM_MESA() );
        values.put(DatabaseHelper.Pedido.FUNCIONARIO_ID,pedido.getFUNCIONARIO_ID() );
        values.put(DatabaseHelper.Pedido.PRODUTO_ID,pedido.getPRODUTO_ID());
        values.put(DatabaseHelper.Pedido.PACOTE_ID,pedido.getPACOTE_ID());
        values.put(DatabaseHelper.Pedido.STATUS_PEDIDO,pedido.getSTATUS_PEDIDO() );

        String selection = DatabaseHelper.Pedido._ID + " LIKE ?";
        String[] selectionArgs = {pedido.get_ID()+ "" };
        int count = db.update(DatabaseHelper.Pedido.TABELA, values, selection, selectionArgs);

    }



    private Pedido cursorToPedido(Cursor cursor) {
        Pedido Pedido = new Pedido();
        Pedido.set_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido._ID)));
        Pedido.setFUNCIONARIO_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.FUNCIONARIO_ID)));

        Pedido.setNUM_MESA(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.NUM_MESA)));
        Pedido.setPACOTE_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.PACOTE_ID)));
        Pedido.setPRODUTO_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.PRODUTO_ID)));
        Pedido.setQUANTIDADE(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.QUANTIDADE)));
        Pedido.setSTATUS_PEDIDO(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.STATUS_PEDIDO)));
        Pedido.setTEMPO_TOTAL_PEDIDO(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.TEMPO_TOTAL_PEDIDO)));

        return Pedido;
    }
    public List<Pedido> consultarTodosPedido() {
        // Cursor cursor = db.query(DatabaseHelper.Pedido.TABELA, DatabaseHelper.Pedido.COLUNAS,null,null,null,null,null);
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Pedido.TABELA, null);

        List<Pedido> PedidoList = new ArrayList<Pedido>();

        if (cursor.getCount() >0 && cursor != null) {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Pedido newUser = cursorToPedido(cursor);
                PedidoList.add(newUser);
                cursor.moveToNext();
            }

            cursor.close();
        }
        return PedidoList;
    }

    public List<Pedido> consultarTodosPedidosPorGarcom(String nomeGarcom) {
        // Cursor cursor = db.query(DatabaseHelper.Pedido.TABELA, DatabaseHelper.Pedido.COLUNAS,null,null,null,null,null);

        String[] whereArgs = new String[]{nomeGarcom};
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Pedido.TABELA + " INNER JOIN " +
                DatabaseHelper.Funcionario.TABELA + " ON "+    DatabaseHelper.Pedido.TABELA + "." + DatabaseHelper.Pedido._ID +
                "="+DatabaseHelper.Funcionario._ID + " WHERE " + DatabaseHelper.Funcionario.TABELA + "." + DatabaseHelper.Funcionario.LOGIN , whereArgs);

        List<Pedido> PedidoList = new ArrayList<Pedido>();

        if (cursor.getCount() >0 && cursor != null) {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Pedido newUser = cursorToPedido(cursor);
                PedidoList.add(newUser);
                cursor.moveToNext();
            }

            cursor.close();
        }
        return PedidoList;
    }



    public List<Pedido> consultarTodosPedidosPorNomePacote(String nomePacote) {
        // Cursor cursor = db.query(DatabaseHelper.Pedido.TABELA, DatabaseHelper.Pedido.COLUNAS,null,null,null,null,null);
        String[] whereArgs = new String[]{nomePacote};
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Pedido.TABELA + " INNER JOIN " +
                DatabaseHelper.Pacote.TABELA + " ON "+    DatabaseHelper.Pedido.TABELA + "." + DatabaseHelper.Pedido._ID +
                "="+DatabaseHelper.Pacote._ID + " WHERE " + DatabaseHelper.Pacote.TABELA + "." + DatabaseHelper.Pacote.NOME_PACOTE , whereArgs);


        List<Pedido> PedidoList = new ArrayList<Pedido>();

        if (cursor.getCount() >0 && cursor != null) {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Pedido newUser = cursorToPedido(cursor);
                PedidoList.add(newUser);
                cursor.moveToNext();
            }

            cursor.close();
        }
        return PedidoList;
    }


    public List<Pedido> consultarTodosPedidosPorNomeProduto(String nomeProduto) {
        // Cursor cursor = db.query(DatabaseHelper.Pedido.TABELA, DatabaseHelper.Pedido.COLUNAS,null,null,null,null,null);
        String[] whereArgs = new String[]{nomeProduto};
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Pedido.TABELA + " INNER JOIN " +
                DatabaseHelper.Produto.TABELA + " ON "+    DatabaseHelper.Pedido.TABELA + "." + DatabaseHelper.Pedido._ID +
                " = " + DatabaseHelper.Produto._ID + " WHERE " + DatabaseHelper.Produto.TABELA + "." + DatabaseHelper.Produto.NOME + " =  ?" , whereArgs);


        List<Pedido> PedidoList = new ArrayList<Pedido>();

        if (cursor.getCount() >0 && cursor != null) {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Pedido newUser = cursorToPedido(cursor);
                PedidoList.add(newUser);
                cursor.moveToNext();
            }

            cursor.close();
        }
        return PedidoList;
    }



}
