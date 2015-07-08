package br.edu.ifpe.pdm.cardapiolanches.dao;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;


/**
 * Created by Richardson on 14/05/2015.
 */
public class ProdutoTask extends AsyncTask<Produto, Void, Produto> {
    private final String LOG_TAG = ProdutoTask.class.getSimpleName();
    private String[] forecast = null;
    private  Produto produto = null;

//    private List<Map<String,String>> listDetailsEnergyBill =null;


    private ProdutoListener listener = null;

    public ProdutoTask(ProdutoListener listener) {
        this.listener = listener;
    }

    @Override
    protected Produto doInBackground(Produto... params) {
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
           builder.appendQueryParameter("unidade", params[0].getUNIDADE_ESTOQUE().toString());
            builder.appendQueryParameter("nome", params[0].getNOME());
            builder.appendQueryParameter("preco", params[0].getPRECO().toString());
            builder.appendQueryParameter("descricao", params[0].getDESCRICAO());
            builder.appendQueryParameter("nome_imagem", params[0].getNOME_IMAGEM());
            builder.appendQueryParameter("tempo_pronto", params[0].getTEMPO_PRONTO_PRODUTO().toString());
            builder.appendQueryParameter("categoria", params[0].getCATEGORIA());


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
            produto = getProdutoFromJson(forecastJson);


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
    protected void onPostExecute(Produto produto) {
       /* for (Object s : resultStrs) {
            Log.v(LOG_TAG, "Forecast entry: " + s);
        }*/
        listener.showProduto(produto);

    }


    public static Produto getProdutoFromJson(String forecastJsonStr){

    Produto produto = null;
    try

    {

        if(forecastJsonStr != null) {
            produto = new Produto();

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            produto.set_ID(forecastJson.getInt("_id"));
            produto.setUNIDADE_ESTOQUE(forecastJson.getInt("unidade"));
            produto.setNOME(forecastJson.getString("nome"));
            produto.setPRECO(Float.parseFloat(forecastJson.getString("preco")));
            produto.setDESCRICAO(forecastJson.getString("descricao"));
            produto.setNOME_IMAGEM(forecastJson.getString("nome_imagem"));
            produto.setTEMPO_PRONTO_PRODUTO(forecastJson.getInt("tempo_pronto"));
            produto.setCATEGORIA(forecastJson.getString("categoria"));

        }
    }

    catch(
    JSONException e
    )

    {
        e.printStackTrace();
    }

    return produto;
}

}