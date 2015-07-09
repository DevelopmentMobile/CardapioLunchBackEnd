package br.edu.ifpe.pdm.cardapiolanches.dao;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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

import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;


/**
 * Created by Richardson on 14/05/2015.
 */
public class ProdutoTask extends AsyncTask<Produto, Void, List<Produto>> {
    private final String LOG_TAG = ProdutoTask.class.getSimpleName();
    private String[] forecast = null;
    private  List<Produto> produto = null;

//    private List<Map<String,String>> listDetailsEnergyBill =null;


    private ProdutoListener listener = null;

    public ProdutoTask(ProdutoListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Produto> doInBackground(Produto... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJson = null;

        try {
           //Uri.Builder builder = new Uri.Builder();

           Uri.Builder builder = Uri.parse("http://10.1.1.44:8080").buildUpon();
            //builder.scheme("http");
          //  builder.authority("10.1.1.44");

            builder.appendPath("produto");

            builder.appendQueryParameter("acao", params[0].getACAO());
            //  builder.appendQueryParameter("_id", produto.get_ID().toString());
            for(Produto produto:params) {
                builder.appendQueryParameter("unidade", produto.getUNIDADE_ESTOQUE().toString());
                builder.appendQueryParameter("nome", produto.getNOME());
                builder.appendQueryParameter("preco",produto.getPRECO().toString());
                builder.appendQueryParameter("descricao",produto.getDESCRICAO());
                builder.appendQueryParameter("nome_imagem", produto.getNOME_IMAGEM());
                builder.appendQueryParameter("tempo_pronto", produto.getTEMPO_PRONTO_PRODUTO().toString());
                builder.appendQueryParameter("categoria", produto.getCATEGORIA());

            }
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
            produto = getProdutoFromArrayJson(forecastJson);


        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return produto;
    }


    @Override
    protected void onPostExecute(List<Produto> produtos) {
       /* for (Object s : resultStrs) {
            Log.v(LOG_TAG, "Forecast entry: " + s);
        }*/
        listener.showProduto(produtos);

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

}