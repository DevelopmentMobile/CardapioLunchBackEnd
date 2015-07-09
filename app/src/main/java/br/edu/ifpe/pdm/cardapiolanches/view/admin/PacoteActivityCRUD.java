package br.edu.ifpe.pdm.cardapiolanches.view.admin;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.bean.Pacote;
import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;
import br.edu.ifpe.pdm.cardapiolanches.dao.DatabaseHelper;
import br.edu.ifpe.pdm.cardapiolanches.dao.PacoteListener;
import br.edu.ifpe.pdm.cardapiolanches.dao.PacoteListenerProdutos;
import br.edu.ifpe.pdm.cardapiolanches.dao.PacoteTask;

/**
 * Created by Richardson on 31/05/2015.
 */
public class PacoteActivityCRUD extends Activity implements PacoteListenerProdutos,PacoteListener {


    private Spinner tipPacote;
    private Spinner sProduto;
    private List<Produto> produtos = new ArrayList<Produto>();
    private Integer contadorProduto = 0;
    private TextView tvContadorProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crud_pacote_activity);

        tvContadorProduto =   ((TextView) findViewById(R.id.contadorProdutos));

        new ProdutoTaskLoadList(this).execute();




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.tipo_pacote,//
                android.R.layout.simple_spinner_item); //passando o contexto atual, o identifiador do array de op ções que defiimos no strings.xml e o id do layout que será utilizado para apresentar as op ções.
        tipPacote = (Spinner) findViewById(R.id.tipo_pacote);
        tipPacote.setAdapter(adapter);


     /* DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
       db.execSQL("DROP TABLE IF EXISTS pacote" );
        db.execSQL("DROP TABLE IF EXISTS  pacote_produto" );



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




            db.close();
        dbHelper.close();*/



    }


    public void buttonInsertClick(View view) {

        int tipo_pacote =  ((Spinner) findViewById(R.id.tipo_pacote)).getSelectedItemPosition() + 1;
        Produto produto = (Produto) ((Spinner) findViewById(R.id.produto_pacote)).getSelectedItem();
        String nome_pacote=    ((EditText)findViewById(R.id.nome_pacote)).getText().toString();
        String descricao =    ((EditText)findViewById(R.id.descricao_pacote)).getText().toString();
        Float preco =    Float.parseFloat(((EditText) findViewById(R.id.preco_pacote)).getText().toString());
        int unidade =   Integer.parseInt(((EditText) findViewById(R.id.unidade)).getText().toString());
        String nome_imagem =    ((EditText)findViewById(R.id.nome_imagem_pacote)).getText().toString();


        final Pacote[] arrayPacote =  new Pacote[1];
        final     Pacote pacote =  new Pacote(
                nome_pacote,
                tipo_pacote,
                descricao,
                preco,
                unidade,
                nome_imagem);

        pacote.setACAO("inserir");
        pacote.setPRODUTO_ID(produto.get_ID());
        pacote.setListProduto(produtos);

        arrayPacote[0] = pacote;
        new PacoteTask(this).execute(arrayPacote);

    }

    public void buttonUpdateClick(View view) {


        int tipo_pacote =    ((Spinner) findViewById(R.id.tipo_pacote)).getSelectedItemPosition() + 1;
        String nome_pacote=    ((EditText)findViewById(R.id.nome_pacote)).getText().toString();
        String descricao =    ((EditText)findViewById(R.id.descricao_pacote)).getText().toString();
        String preco =    ((EditText)findViewById(R.id.preco_pacote)).getText().toString();


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values =   new ContentValues();
        values.put(DatabaseHelper.Pacote.NOME_PACOTE, nome_pacote);
        values.put(DatabaseHelper.Pacote.DESCRICAO_PACOTE, descricao);
        values.put(DatabaseHelper.Pacote.TIPO_PACOTE, tipo_pacote );
        values.put(DatabaseHelper.Pacote.PRECO, preco );

        String selection = DatabaseHelper.Pacote.NOME_PACOTE + " LIKE ?";
        String[] selectionArgs = {nome_pacote+ "" };
        int count = db.update(DatabaseHelper.Pacote.TABELA, values, selection, selectionArgs);
        Toast toast = Toast.makeText(this,
                "Registros atualizados: " + count, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void buttonDeleteClick(View view) {

        String nome_pacote =    ((EditText)findViewById(R.id.nome_pacote)).getText().toString();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = DatabaseHelper.Pacote.NOME_PACOTE + " LIKE ?";
        String[] selectionArgs = { nome_pacote + "" };
        int count = db.delete(DatabaseHelper.Pacote.TABELA, selection, selectionArgs);
        Toast toast = Toast.makeText(this,
                "Registros deletados: " + count, Toast.LENGTH_SHORT);
        toast.show();
    }


    public void buttonQueryClick(View view) {

        String nome_pacote =    ((EditText)findViewById(R.id.nome_pacote)).getText().toString();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { DatabaseHelper.Pacote._ID,
                DatabaseHelper.Pacote.NOME_PACOTE,
                DatabaseHelper.Pacote.DESCRICAO_PACOTE,
                DatabaseHelper.Pacote.TIPO_PACOTE,
                };
        String selection = DatabaseHelper.Pacote.NOME_PACOTE + " LIKE ? ";
        String[] selectionArgs = {nome_pacote+ "%"};
        String sortOrder = DatabaseHelper.Pacote.NOME_PACOTE + " DESC";
        Cursor c = db.query(DatabaseHelper.Pacote.TABELA,
                projection,
                selection,
                selectionArgs,
                null, null,
                sortOrder);
        ArrayList<CharSequence> data = new ArrayList<CharSequence>();
        c.moveToFirst();
        String entry = " | " +  DatabaseHelper.Pacote._ID + " | " +
                DatabaseHelper.Pacote.NOME_PACOTE+ " | " +
                DatabaseHelper.Pacote.TIPO_PACOTE+ " | "+
                DatabaseHelper.Pacote.DESCRICAO_PACOTE + " | ";


        while (!c.isAfterLast()) {
             entry += "\n | " + c.getInt(c.getColumnIndex( DatabaseHelper.Pacote._ID)) + " | ";
            entry += c.getString(c.getColumnIndex( DatabaseHelper.Pacote.NOME_PACOTE) )  + " | ";
            entry += c.getInt( c.getColumnIndex( DatabaseHelper.Pacote.TIPO_PACOTE))  + " | ";
          //  entry += c.getString(c.getColumnIndex( DatabaseHelper.Pacote.DESCRICAO_PACOTE))  + " | "; //Erro ao buscar  settar

            data.add(entry);
            entry = "";
            c.moveToNext();
        }
        Intent intent = new Intent(this, QueryResultActivity.class);
        intent.putCharSequenceArrayListExtra("data", data);
        startActivity(intent);
    }



    public void buttonAddProduto(View view){

    Produto produto  =  (Produto) ((Spinner) findViewById(R.id.produto_pacote)).getSelectedItem();
        produtos.add(produto);

       tvContadorProduto.setText(Integer.toString(produtos.size()));

        Toast toast = Toast.makeText(this,
                "Produto adicionado!\nID = " + produto.get_ID() + "\nNome Produto: " + produto.getNOME(), Toast.LENGTH_SHORT);
        toast.show();
    }


    public void buttonDeleteProduto(View view){




        if(produtos.size() > 0 ) {
            Toast toast = Toast.makeText(this,
                    "Lista Vazia!\nID Produto Removido:" + produtos.get(produtos.size()-1)
                    + "\n Nome Produto: "+  produtos.get(produtos.size()-1).getNOME()
                    , Toast.LENGTH_SHORT);
            toast.show();
            produtos.remove(produtos.size()-1);
            tvContadorProduto.setText(Integer.toString(produtos.size()));

        }else{
            Toast toast = Toast.makeText(this,
                    "Lista Vazia!\n", Toast.LENGTH_SHORT);
            toast.show();

        }
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

    @Override
    public void  loadProdutos(List<Produto> produto) {

        ArrayAdapter<Produto> arrayAdapterProduto = new ArrayAdapter(this, android.R.layout.simple_spinner_item , produto);
        sProduto = (Spinner) findViewById(R.id.produto_pacote);
        sProduto.setAdapter(arrayAdapterProduto);



    }

    private class ProdutoTaskLoadList extends AsyncTask<Void, Void,List<Produto>> {
        private String[] forecast = null;
        private  Produto produto = null;

//    private List<Map<String,String>> listDetailsEnergyBill =null;

        List<Produto> produtos= null;

        public List<Produto> getProdutos() {
            return produtos;
        }

        public void setProdutos(List<Produto> produtos) {
            this.produtos = produtos;
        }

        private PacoteListenerProdutos listener = null;


        public ProdutoTaskLoadList(PacoteListenerProdutos listener) {
            this.listener = listener;

        }



        public  List<Produto> getProdutoFromArrayJson(String forecastJsonStr) {

            Produto produto = null;
            List<Produto> produtos = null;
            try

            {

                if (forecastJsonStr != null) {

                    produtos = new ArrayList<Produto>();
                    JSONObject forecastJson = new JSONObject(forecastJsonStr);
                    JSONArray ja = new JSONArray();
                    ja = forecastJson.getJSONArray("produtos");
                    for (int i = 0; i < ja.length(); i++) {
                        produto = new Produto();
                        produto.set_ID(ja.getJSONObject(i).getInt("_id"));
                        produto.setUNIDADE_ESTOQUE(ja.getJSONObject(i).getInt("unidade"));
                        produto.setNOME(ja.getJSONObject(i).getString("nome"));
                        produto.setPRECO(Float.parseFloat(ja.getJSONObject(i).getString("preco")));
                        produto.setDESCRICAO(ja.getJSONObject(i).getString("descricao"));
                        produto.setNOME_IMAGEM(ja.getJSONObject(i).getString("nome_imagem"));
                        produto.setTEMPO_PRONTO_PRODUTO(ja.getJSONObject(i).getInt("tempo_pronto"));
                        produto.setCATEGORIA(ja.getJSONObject(i).getString("categoria"));
                        produtos.add(produto);

                    }
                }
            } catch (
                    JSONException e
                    )

            {
                e.printStackTrace();
            }


            return produtos;
        }

        @Override
        protected List<Produto> doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;

            BufferedReader reader = null;

            String forecastJson = null;
            List<Produto> produtos = null;

            try {
                //Uri.Builder builder = new Uri.Builder();
                Uri.Builder builder = Uri.parse("http://10.1.1.44:8080").buildUpon();
                //builder.scheme("http");
                //  builder.authority("10.1.1.44");

                builder.appendPath("produto");

                builder.appendQueryParameter("acao", "consultar");
                //  builder.appendQueryParameter("_id", produto.get_ID().toString());
                builder.appendQueryParameter("unidade", "0");
                builder.appendQueryParameter("nome", "");
                builder.appendQueryParameter("preco", "0.0");
                builder.appendQueryParameter("descricao", "");
                builder.appendQueryParameter("nome_imagem", "");
                builder.appendQueryParameter("tempo_pronto", "0");
                builder.appendQueryParameter("categoria", "");


                URL url = new URL(builder.build().toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();

                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    forecastJson = null;
                }


                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
//                buffer.append(line.replaceAll("\\n",  "").replaceAll("\\t","") );
                    buffer.append(line);
                }

                if (buffer.length() == 0) {
                    forecastJson = null;
                } else {
                    forecastJson = buffer.toString();
                }
                //     listEnergyBill = EnergyBillParser.printValuesCellTable(forecastJson);
                produtos = getProdutoFromArrayJson(forecastJson);


            } catch (IOException e) {
                //Log.e(LOG_TAG, "Error ", e);
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        // Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            this.produtos = produtos;
            return produtos;
        }

        @Override
        protected void onPostExecute(List<Produto> produtos) {
       /* for (Object s : resultStrs) {
            Log.v(LOG_TAG, "Forecast entry: " + s);
        }*/
            listener.loadProdutos(produtos);

        }


    }
    @Override
    public void showPacote(Pacote Pacote) {

        if(Pacote != null) {
            Toast.makeText(this, Pacote.toString(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Produto Não Cadastrado!!", Toast.LENGTH_SHORT).show();
        }

    }
}
