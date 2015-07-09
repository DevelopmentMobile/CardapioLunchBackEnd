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

import br.edu.ifpe.pdm.cardapiolanches.bean.Funcionario;


/**
 * Created by Richardson on 14/05/2015.
 */
public class FuncionarioTask extends AsyncTask<Funcionario, Void, List<Funcionario>> {
    private final String LOG_TAG = FuncionarioTask.class.getSimpleName();
    private String[] forecast = null;
    private  List<Funcionario> funcionarios = null;
    public static final String MY_TAG = "FuncTask";

//    private List<Map<String,String>> listDetailsEnergyBill =null;


    private FuncionarioListener listener = null;

    public FuncionarioTask(FuncionarioListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Funcionario> doInBackground(Funcionario... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJson = null;

        try {
            //Uri.Builder builder = new Uri.Builder();

            Uri.Builder builder = Uri.parse("http://10.1.1.44:8080").buildUpon();
            //builder.scheme("http");
            //builder.authority("10.1.1.44");

            builder.appendPath("funcionario");

            builder.appendQueryParameter("acao", params[0].getACAO());
            //  builder.appendQueryParameter("_id", Funcionario.get_ID().toString());
            builder.appendQueryParameter("login", params[0].getLOGIN().toString());
            builder.appendQueryParameter("senha", params[0].getSENHA());
            builder.appendQueryParameter("tipo_funcionario", params[0].getTIPO_FUNCIONARIO().toString());

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
            funcionarios = getFuncionarioFromJson(forecastJson);
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
        return funcionarios;
    }


    @Override
    protected void onPostExecute(List<Funcionario> funcionarios) {

            Log.v(LOG_TAG, "Resulto onPost Execute: " + funcionarios);

        listener.showFuncionario(funcionarios);
    }


    public List<Funcionario> getFuncionarioFromJson(String forecastJsonStr){

        List<Funcionario> funcionarios = null;



        try
        {
            if(forecastJsonStr != null) {

        JSONObject forecastJson= new JSONObject(forecastJsonStr);
                JSONArray ja = new JSONArray();
                ja = forecastJson.getJSONArray("funcionarios");

           funcionarios = new ArrayList<Funcionario>();
        for (int i = 0; i < ja.length(); i++){


       Funcionario funcionario=new Funcionario();

        funcionario.set_ID(ja.getJSONObject(i).getInt("_id"));
        funcionario.setLOGIN(ja.getJSONObject(i).getString("login"));
        funcionario.setSENHA(ja.getJSONObject(i).getString("senha"));
        funcionario.setTIPO_FUNCIONARIO(ja.getJSONObject(i).getInt("tipo_funcionario"));
        funcionario.setACAO(ja.getJSONObject(i).getString("acao"));
        funcionarios.add(funcionario);
             }
            }
        }catch(
                JSONException e
                )

        {
            e.printStackTrace();
        }

        return funcionarios;
    }

}