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

import br.edu.ifpe.pdm.cardapiolanches.bean.Pacote;


/**
 * Created by Richardson on 14/05/2015.
 */
public class PacoteTask extends AsyncTask<Pacote, Void, Pacote> {
    private final String LOG_TAG = PacoteTask.class.getSimpleName();
    private String[] forecast = null;
    private  Pacote Pacote = null;

//    private List<Map<String,String>> listDetailsEnergyBill =null;


    private PacoteListener listener = null;

    public PacoteTask(PacoteListener listener) {
        this.listener = listener;
    }

    @Override
    protected Pacote doInBackground(Pacote... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJson = null;

        try {
           //Uri.Builder builder = new Uri.Builder();

           Uri.Builder builder = Uri.parse("http://10.1.1.44:8080").buildUpon();
            //builder.scheme("http");
          //  builder.authority("10.1.1.44");

            builder.appendPath("pacote");

            builder.appendQueryParameter("acao", params[0].getACAO());
            //  builder.appendQueryParameter("_id", Pacote.get_ID().toString());
           builder.appendQueryParameter("unidade", params[0].getUNIDADE().toString());
            builder.appendQueryParameter("nome", params[0].getNOME_PACOTE());
            builder.appendQueryParameter("preco", params[0].getPRECO().toString());
            builder.appendQueryParameter("descricao", params[0].getDESCRICAO_PACOTE());
            builder.appendQueryParameter("nome_imagem", params[0].getNOME_IMAGE());
            builder.appendQueryParameter("tipo_pacote", params[0].getTIPO_PACOTE().toString());
            builder.appendQueryParameter("produto_id", params[0].getPRODUTO_ID().toString());

            for(int i = 0 ; i <  params[0].getListProduto().size() ; i++){

                builder.appendQueryParameter("ids", params[0].getListProduto().get(i).get_ID().toString());

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
            Pacote = getPacoteFromJson(forecastJson);


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
        return Pacote;
    }


    @Override
    protected void onPostExecute(Pacote Pacote) {
       /* for (Object s : resultStrs) {
            Log.v(LOG_TAG, "Forecast entry: " + s);
        }*/
        listener.showPacote(Pacote);

    }


    public static Pacote getPacoteFromJson(String forecastJsonStr){

    Pacote Pacote = null;
    try

    {

        if(forecastJsonStr != null) {
            Pacote = new Pacote();

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            Pacote.set_ID(forecastJson.getInt("_id"));
            Pacote.setUNIDADE(forecastJson.getInt("unidade"));
            Pacote.setNOME_PACOTE(forecastJson.getString("nome"));
            Pacote.setPRECO(Float.parseFloat(forecastJson.getString("preco")));
            Pacote.setDESCRICAO_PACOTE(forecastJson.getString("descricao"));
            Pacote.setNOME_IMAGE(forecastJson.getString("nome_imagem"));
            Pacote.setTIPO_PACOTE(forecastJson.getInt("tipo_pacote"));

        }
    }

    catch(
    JSONException e
    )

    {
        e.printStackTrace();
    }

    return Pacote;
}

}