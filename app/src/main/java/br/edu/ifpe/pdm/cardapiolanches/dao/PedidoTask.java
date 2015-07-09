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

import br.edu.ifpe.pdm.cardapiolanches.bean.Pedido;


/**
 * Created by Richardson on 14/05/2015.
 */
public class PedidoTask extends AsyncTask<Pedido, Void, List<Pedido>> {
    private final String LOG_TAG = PedidoTask.class.getSimpleName();
    private String[] forecast = null;
    private  List<Pedido> Pedido = null;

//    private List<Map<String,String>> listDetailsEnergyBill =null;


    private PedidoListener listener = null;

    public PedidoTask(PedidoListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Pedido> doInBackground(Pedido... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJson = null;

        try {
           //Uri.Builder builder = new Uri.Builder();

           Uri.Builder builder = Uri.parse("http://10.1.1.44:8080").buildUpon();
            //builder.scheme("http");
          //  builder.authority("10.1.1.44");

            builder.appendPath("pedido");

            builder.appendQueryParameter("acao", params[0].getACAO());
            //  builder.appendQueryParameter("_id", Pedido.get_ID().toString());
            for(int i= 0 ; i< params.length; i++) {
                builder.appendQueryParameter("produto_id", params[i].getPRODUTO_ID().toString());
                builder.appendQueryParameter("funcionario_id", params[i].getFUNCIONARIO_ID().toString());
                builder.appendQueryParameter("pacote_id", params[i].getPACOTE_ID().toString());
                builder.appendQueryParameter("status_pedido", params[i].getSTATUS_PEDIDO().toString());
                builder.appendQueryParameter("num_mesa", params[i].getNUM_MESA().toString());
                builder.appendQueryParameter("quantidade", params[i].getQUANTIDADE().toString());
                builder.appendQueryParameter("num_pedido", params[i].getNUM_PEDIDO());
                builder.appendQueryParameter("tempo_total", params[i].getTEMPO_TOTAL_PEDIDO().toString());

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
            Pedido = getPedidoFromJson(forecastJson);


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
        return Pedido;
    }


    @Override
    protected void onPostExecute(List<Pedido> pedidos) {

            Log.v(LOG_TAG, "Retorno de Pedidos: " + pedidos);

        listener.showPedido(pedidos);

    }


    public static List<Pedido> getPedidoFromJson(String forecastJsonStr){


        List<Pedido> Pedidos = null;



        try
        {
            if(forecastJsonStr != null) {

                JSONObject forecastJson= new JSONObject(forecastJsonStr);
                JSONArray ja = new JSONArray();
                ja = forecastJson.getJSONArray("pedidos");

                Pedidos = new ArrayList<Pedido>();
                for (int i = 0; i < ja.length(); i++){


                    Pedido Pedido=new Pedido();

                    Pedido.set_ID(ja.getJSONObject(i).getInt("_id"));
                    Pedido.setPRODUTO_ID(ja.getJSONObject(i).getInt("produto_id"));
                    Pedido.setFUNCIONARIO_ID(ja.getJSONObject(i).getInt("funcionario_id"));
                    Pedido.setPACOTE_ID(ja.getJSONObject(i).getInt("pacote_id"));
                    Pedido.setPACOTE_ID(ja.getJSONObject(i).getInt("num_mesa"));
                    Pedido.setSTATUS_PEDIDO(ja.getJSONObject(i).getInt("status_pedido"));
                    Pedido.setQUANTIDADE(ja.getJSONObject(i).getInt("quantidade"));
                    Pedido.setNUM_PEDIDO(ja.getJSONObject(i).getString("num_pedido"));
                    Pedido.setTEMPO_TOTAL_PEDIDO(ja.getJSONObject(i).getInt("tempo_total"));
                    Pedido.setACAO(ja.getJSONObject(i).getString("acao"));
                    Pedidos.add(Pedido);
                }
            }
        }catch(
                JSONException e
                )

        {
            e.printStackTrace();
        }

        return Pedidos;

}

}