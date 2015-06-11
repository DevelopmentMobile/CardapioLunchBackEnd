package br.edu.ifpe.pdm.cardapiolanches.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Richardson on 03/05/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DBPATH = "/data/data/br.edu.ifpe.pdm.cardapiolanches/databases/";
    private static final String BANCO_DADOS = "cardapioapp";

    private static int VERSAO = 1;
    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE funcionario (" +
                "    _id integer NOT NULL PRIMARY KEY," +
                "    login TEXT UNIQUE," +
                "    senha TEXT NOT NULL," +
                "    tipo_funcionario integer NOT NULL" +
                ");");

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

        db.execSQL("CREATE TABLE pedido (" +
                "   _id integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
               "    produto_id integer," +
               "    pacote_id integer," +
                "    tempo_total_pedido integer," +
                "    num_mesa integer NOT NULL," +
                "    funcionario_id integer," +
                "    status_pedido integer," +
                "    quantidade integer NOT NULL," +
                "   FOREIGN KEY (produto_id) REFERENCES produto (_id)," +
               "    FOREIGN KEY (pacote_id) REFERENCES pacote (_id)," +
                "   FOREIGN KEY (funcionario_id) REFERENCES funcionario (_id) " +
                ");");

        db.execSQL("CREATE TABLE produto (" +
                "    _id integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "    unidade_estoque integer NOT NULL," +
                "    nome text UNIQUE," +
                "    preco decimal(6,2) NOT NULL," +
                "    descricao text," +
                "    nome_imagem text," +
                "    tempo_pronto_produto integer NOT NULL," +
                "    categoria text NOT NULL" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Funcionario.TABELA);
        onCreate(db);
    }

    public static class Funcionario {
        public static final String TABELA = "funcionario";
        public static final String _ID = "_id";
        public static final String LOGIN = "login";
        public static final String SENHA = "senha";
        public static final String TIPO_FUNCIONARIO= "tipo_funcionario";
        public static final String[] COLUNAS = new String[]{
                _ID, LOGIN, SENHA,TIPO_FUNCIONARIO  };
    }

    public static class Pacote {
        public static final String TABELA = "pacote";
        public static final String _ID = "_id";
        public static final String NOME_PACOTE = "nome_pacote";
        public static final String TIPO_PACOTE = "tipo_pacote";
        public static final String DESCRICAO_PACOTE = "descricao_pacote";
        public static final String PRECO = "preco";
        public static final String[] COLUNAS = new String[]{
                _ID, NOME_PACOTE,TIPO_PACOTE, DESCRICAO_PACOTE,PRECO};
    }

    public static class PacoteProduto {
        public static final String TABELA = "pacote_produto";
        public static final String _ID = "_id";
        public static final String PACOTE_ID = "pacote_id";
        public static final String PRODUTO_ID = "produto_id";
        public static final String[] COLUNAS = new String[]{
                _ID, PACOTE_ID,PRODUTO_ID};
    }

    public static class Pedido{
        public static final String TABELA = "pedido";
        public static final String _ID = "_id";
        public static final String NUM_PEDIDO = "num_pedido";
        public static final String TEMPO_TOTAL_PEDIDO = "tempo_total_pedido";
        public static final String QUANTIDADE = "quantidade";
        public static final String NUM_MESA = "num_mesa";
        public static final String FUNCIONARIO_ID = "funcionario_id";
        public static final String PRODUTO_ID = "produto_id";
        public static final String PACOTE_ID = "pacote_id";
        public static final String STATUS_PEDIDO = "status_pedido";

        public static final String[] COLUNAS = new String[]{
                _ID,TEMPO_TOTAL_PEDIDO , NUM_MESA, FUNCIONARIO_ID,QUANTIDADE,PRODUTO_ID,PACOTE_ID,STATUS_PEDIDO,
                NUM_PEDIDO
        };
    }

    public static class Produto{
        public static final String TABELA = "produto";
        public static final String _ID = "_id";
        public static final String UNIDADE_ESTOQUE = "unidade_estoque";
        public static final String NOME = "nome";
        public static final String PRECO = "preco";
        public static final String DESCRICAO = "descricao";
        public static final String NOME_IMAGEM = "nome_imagem";
        public static final String TEMPO_PRONTO_PRODUTO = "tempo_pronto_produto";
        public static final String CATEGORIA = "categoria";

        public static final String[] COLUNAS = new String[]{
                _ID, UNIDADE_ESTOQUE, NOME, PRECO, DESCRICAO,NOME_IMAGEM,TEMPO_PRONTO_PRODUTO,CATEGORIA
        };
    }
}
