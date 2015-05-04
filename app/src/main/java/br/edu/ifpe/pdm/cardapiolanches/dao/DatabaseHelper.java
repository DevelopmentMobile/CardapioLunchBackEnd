package br.edu.ifpe.pdm.cardapiolanches.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Richardson on 03/05/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "CardapioLuncher";
    private static int VERSAO = 1;
    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios (_id INTEGER PRIMARY KEY," +
                " login TEXT, senha TEXT, nome TEXT, fone TEXT );");
        db.execSQL("CREATE TABLE pagamentos (_id INTEGER PRIMARY KEY," +
                " num_verificador TEXT, quantidade DOUBLE" +
                " FOREIGN KEY(usuario_id) REFERENCES usuario(_id));");
        db.execSQL("CREATE TABLE pedidos (_id INTEGER PRIMARY KEY," +
                " date_pedido DATE, data_entrega DATE, status_pedido TEXT" +
                " FOREIGN KEY(usuario_id) REFERENCES usuario(_id));");
        db.execSQL("CREATE TABLE produtos (_id INTEGER PRIMARY KEY," +
                " nome_produto TEXT, preco DOUBLE, categoria TEXT," +
                " medida TEXT);");
        db.execSQL("CREATE TABLE detalhes_pedidos (_id INTEGER PRIMARY KEY," +
                " qt_solicitada TEXT, preco DOUBLE" +
                " FOREIGN KEY(pedidos_id) REFERENCES pedidos(_id)," +
                " FOREIGN KEY(produtos_id) REFERENCES produtos(_id));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("ALTER TABLE gasto ADD COLUMN pessoa TEXT");
    }


    public static class Login {
        public static final String TABELA = "usuarios";
        public static final String _ID = "_id";
        public static final String LOGIN = "login";
        public static final String NOME = "nome";
        public static final String FONE = "fone";
        public static final String[] COLUNAS = new String[]{
                _ID, LOGIN, NOME, FONE };
    }

    public static class Pedidos{
        public static final String TABELA = "pedidos";
        public static final String _ID = "_id";
        public static final String DT_PEDIDO = "date_pedido";
        public static final String DT_ENTREGA = "data_entrega";
        public static final String STATUS_PEDIDO = "status_pedido";
        public static final String DESCRICAO = "descricao";
        public static final String[] COLUNAS = new String[]{
                _ID, DT_PEDIDO, DT_ENTREGA, STATUS_PEDIDO, DESCRICAO
        };
    }

    public static class Produtos{
        public static final String TABELA = "produtos";
        public static final String _ID = "_id";
        public static final String NOME_PRODUTO = "nome_produto";
        public static final String PRECO = "preco";
        public static final String CATEGORIA = "categoria";
        public static final String MEDIDA = "medida";
        public static final String[] COLUNAS = new String[]{
                _ID, NOME_PRODUTO, PRECO, CATEGORIA, MEDIDA
        };
    }

    public static class DetalhesPedidos{
        public static final String TABELA = "detalhes_pedidos";
        public static final String _ID = "_id";
        public static final String QT_SOLICITADA = "qt_solicitada";
        public static final String PRECO = "preco";
        public static final String[] COLUNAS = new String[]{
                _ID, QT_SOLICITADA, PRECO
        };
    }

}
