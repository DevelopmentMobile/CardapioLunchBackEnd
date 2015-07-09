package br.edu.ifpe.pdm.cardapiolanches.view.admin;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import br.edu.ifpe.pdm.cardapiolanches.bean.Funcionario;
import br.edu.ifpe.pdm.cardapiolanches.bean.Pacote;
import br.edu.ifpe.pdm.cardapiolanches.bean.Pedido;
import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;
import br.edu.ifpe.pdm.cardapiolanches.dao.PedidoListener;
import br.edu.ifpe.pdm.cardapiolanches.dao.PedidoListenerFuncionarios;
import br.edu.ifpe.pdm.cardapiolanches.dao.PedidoListenerPacotes;
import br.edu.ifpe.pdm.cardapiolanches.dao.PedidoListenerProdutos;
import br.edu.ifpe.pdm.cardapiolanches.dao.PedidoTask;

/**
 * Created by Richardson on 31/05/2015.
 */
public class PedidoActivityCRUD extends Activity implements PedidoListener,PedidoListenerFuncionarios,PedidoListenerPacotes,PedidoListenerProdutos {


    private Spinner statusPedido;
    private Spinner sProduto;
    private Spinner sPacote;
    private Spinner sFuncionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crud_pedido_activity);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.status_pedido,//
                android.R.layout.simple_spinner_item); //passando o contexto atual, o identifiador do array de op ções que defiimos no strings.xml e o id do layout que será utilizado para apresentar as op ções.
        statusPedido = (Spinner) findViewById(R.id.status_pedido);
        statusPedido.setAdapter(adapter);

        new FuncionarioTaskLoadList(this).execute();
        new ProdutoTaskLoadList(this).execute();
       // new PacoteTaskLoadList(this).execute();


    }




    public void buttonInsertClick(View view) {

        try {

            int quantidade_pedido = Integer.parseInt(
                    ((EditText) findViewById(R.id.quantidade_pedido)).getText().toString());

           // int tempo_pronto_Pedido = Integer.parseInt(((EditText) findViewById(R.id.total_pedido)).getText().toString());
            int numero_mesa = Integer.parseInt(((EditText) findViewById(R.id.numero_mesa)).getText().toString());
            int funcionario = ((Funcionario) ((Spinner) findViewById(R.id.funcionario_pedido)).getSelectedItem()).get_ID();
            int produto = ((Produto) ((Spinner) findViewById(R.id.produto_pedido)).getSelectedItem()).get_ID();
            Produto produtoSelecionado = ((Produto) ((Spinner) findViewById(R.id.produto_pedido)).getSelectedItem());
           // int pacote = ((Pacote) ((Spinner) findViewById(R.id.pacote_pedido)).getSelectedItem()).get_ID();
            int status = ((Spinner) findViewById(R.id.status_pedido)).getSelectedItemPosition() + 1;
          //  String num_pedido = ((EditText) findViewById(R.id.id_num_pedido)).getText().toString();
          String num_pedido = "-1";
            int totalTempo = produtoSelecionado.getTEMPO_PRONTO_PRODUTO() * quantidade_pedido;
            Pedido pedido = new Pedido(totalTempo,quantidade_pedido,numero_mesa,funcionario,produto,-1,status,num_pedido);
            pedido.setACAO("inserir");
            Pedido [] pedidos = new Pedido[1];
            pedidos[0] = pedido;
            new PedidoTask(this).execute(pedidos);
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public void buttonUpdateClick(View view) {
        try{
            int quantidade_pedido = Integer.parseInt(
                    ((EditText) findViewById(R.id.quantidade_pedido)).getText().toString());

            // int tempo_pronto_Pedido = Integer.parseInt(((EditText) findViewById(R.id.total_pedido)).getText().toString());
            int numero_mesa = Integer.parseInt(((EditText) findViewById(R.id.numero_mesa)).getText().toString());
            int funcionario = ((Funcionario) ((Spinner) findViewById(R.id.funcionario_pedido)).getSelectedItem()).get_ID();
            int produto = ((Produto) ((Spinner) findViewById(R.id.produto_pedido)).getSelectedItem()).get_ID();
            Produto produtoSelecionado = ((Produto) ((Spinner) findViewById(R.id.produto_pedido)).getSelectedItem());
            // int pacote = ((Pacote) ((Spinner) findViewById(R.id.pacote_pedido)).getSelectedItem()).get_ID();
            int status = ((Spinner) findViewById(R.id.status_pedido)).getSelectedItemPosition() + 1;
            //  String num_pedido = ((EditText) findViewById(R.id.id_num_pedido)).getText().toString();
            String num_pedido = "-1";
            int totalTempo = produtoSelecionado.getTEMPO_PRONTO_PRODUTO() * quantidade_pedido;
            Pedido pedido = new Pedido(totalTempo,quantidade_pedido,numero_mesa,funcionario,produto,-1,status,num_pedido);
        pedido.setACAO("atualizar");
        Pedido [] pedidos = new Pedido[1];
        pedidos[0] = pedido;
        new PedidoTask(this).execute(pedidos);
    }catch (Exception e){
        e.printStackTrace();
    }
    }

    public void buttonDeleteClick(View view) {

        try{
            int quantidade_pedido = Integer.parseInt(
                    ((EditText) findViewById(R.id.quantidade_pedido)).getText().toString());

            // int tempo_pronto_Pedido = Integer.parseInt(((EditText) findViewById(R.id.total_pedido)).getText().toString());
            int numero_mesa = Integer.parseInt(((EditText) findViewById(R.id.numero_mesa)).getText().toString());
            int funcionario = ((Funcionario) ((Spinner) findViewById(R.id.funcionario_pedido)).getSelectedItem()).get_ID();
            int produto = ((Produto) ((Spinner) findViewById(R.id.produto_pedido)).getSelectedItem()).get_ID();
            Produto produtoSelecionado = ((Produto) ((Spinner) findViewById(R.id.produto_pedido)).getSelectedItem());
            // int pacote = ((Pacote) ((Spinner) findViewById(R.id.pacote_pedido)).getSelectedItem()).get_ID();
            int status = ((Spinner) findViewById(R.id.status_pedido)).getSelectedItemPosition() + 1;
            //  String num_pedido = ((EditText) findViewById(R.id.id_num_pedido)).getText().toString();
            String num_pedido = "-1";
            int totalTempo = produtoSelecionado.getTEMPO_PRONTO_PRODUTO() * quantidade_pedido;
            Pedido pedido = new Pedido(totalTempo,quantidade_pedido,numero_mesa,funcionario,produto,-1,status,num_pedido);
            pedido.setACAO("deletar");
            Pedido [] pedidos = new Pedido[1];
            pedidos[0] = pedido;
            new PedidoTask(this).execute(pedidos);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void buttonQueryClick(View view) {

        try{
            int quantidade_pedido = Integer.parseInt(
                    ((EditText) findViewById(R.id.quantidade_pedido)).getText().toString());

            // int tempo_pronto_Pedido = Integer.parseInt(((EditText) findViewById(R.id.total_pedido)).getText().toString());
            int numero_mesa = Integer.parseInt(((EditText) findViewById(R.id.numero_mesa)).getText().toString());
            int funcionario = ((Funcionario) ((Spinner) findViewById(R.id.funcionario_pedido)).getSelectedItem()).get_ID();
            int produto = ((Produto) ((Spinner) findViewById(R.id.produto_pedido)).getSelectedItem()).get_ID();
            Produto produtoSelecionado = ((Produto) ((Spinner) findViewById(R.id.produto_pedido)).getSelectedItem());
            // int pacote = ((Pacote) ((Spinner) findViewById(R.id.pacote_pedido)).getSelectedItem()).get_ID();
            int status = ((Spinner) findViewById(R.id.status_pedido)).getSelectedItemPosition() + 1;
            //  String num_pedido = ((EditText) findViewById(R.id.id_num_pedido)).getText().toString();
            String num_pedido = "-1";
            int totalTempo = produtoSelecionado.getTEMPO_PRONTO_PRODUTO() * quantidade_pedido;
            Pedido pedido = new Pedido(totalTempo,quantidade_pedido,numero_mesa,funcionario,produto,-1,status,num_pedido);
            pedido.setACAO("consultarnumpedido");
            Pedido [] pedidos = new Pedido[1];
            pedidos[0] = pedido;
            new PedidoTask(this).execute(pedidos);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void loadFuncionarios(List<Funcionario> funcionarios) {

        Funcionario Funcionario = new Funcionario();
        Funcionario.setLOGIN("Selecione Funcionario:");
        Funcionario.set_ID(-1);
        List<Funcionario> Funcionarios = new ArrayList<>();
        Funcionarios.add(Funcionario);
        Funcionarios.addAll(1, funcionarios);
        ArrayAdapter<Funcionario> arrayAdapterfuncionario = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Funcionarios);
        sFuncionario = (Spinner) findViewById(R.id.funcionario_pedido);
        sFuncionario.setAdapter(arrayAdapterfuncionario);


    }

    private class FuncionarioTaskLoadList extends AsyncTask<Void, Void,List<Funcionario>> {
        private String[] forecast = null;
        private  Funcionario Funcionario = null;

//    private List<Map<String,String>> listDetailsEnergyBill =null;

        List<Funcionario> Funcionarios= null;

        public List<Funcionario> getFuncionarios() {
            return Funcionarios;
        }

        public void setFuncionarios(List<Funcionario> Funcionarios) {
            this.Funcionarios = Funcionarios;
        }

        private PedidoListenerFuncionarios listener = null;


        public FuncionarioTaskLoadList(PedidoListenerFuncionarios listener) {
            this.listener = listener;

        }



        public  List<Funcionario> getFuncionarioFromArrayJson(String forecastJsonStr) {

            Funcionario funcionario = null;
            List<Funcionario> funcionarios = null;
            try

            {

                if (forecastJsonStr != null) {

                    funcionarios = new ArrayList<Funcionario>();
                    JSONObject forecastJson = new JSONObject(forecastJsonStr);
                    JSONArray ja = new JSONArray();
                    ja = forecastJson.getJSONArray("funcionarios");
                    for (int i = 0; i < ja.length(); i++) {
                        funcionario = new Funcionario();

                        funcionario.set_ID(ja.getJSONObject(i).getInt("_id"));
                        funcionario.setLOGIN(ja.getJSONObject(i).getString("login"));
                        funcionario.setSENHA(ja.getJSONObject(i).getString("senha"));
                        funcionario.setTIPO_FUNCIONARIO(ja.getJSONObject(i).getInt("tipo_funcionario"));
                        funcionario.setACAO(ja.getJSONObject(i).getString("acao"));
                        funcionarios.add(funcionario);

                    }
                }
            } catch (
                    JSONException e
                    )

            {
                e.printStackTrace();
            }


            return funcionarios;
        }

        @Override
        protected List<Funcionario> doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;

            BufferedReader reader = null;

            String forecastJson = null;
            List<Funcionario> Funcionarios = null;

            try {
                //Uri.Builder builder = new Uri.Builder();
                Uri.Builder builder = Uri.parse("http://10.1.1.44:8080").buildUpon();
                //builder.scheme("http");
                //  builder.authority("10.1.1.44");

                builder.appendPath("funcionario");

                builder.appendQueryParameter("acao", "consultar");
                //  builder.appendQueryParameter("_id", Funcionario.get_ID().toString());
                    builder.appendQueryParameter("login","");
                builder.appendQueryParameter("senha", "");
                builder.appendQueryParameter("tipo_funcionario", "0");



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
                Funcionarios = getFuncionarioFromArrayJson(forecastJson);


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

            this.Funcionarios = Funcionarios;
            return Funcionarios;
        }

        @Override
        protected void onPostExecute(List<Funcionario> Funcionarios) {

            Log.v(null, "Response Server Funcionarios: " + Funcionarios);

            listener.loadFuncionarios(Funcionarios);

        }


    }

    @Override
    public void loadPacotes(List<Pacote> Pacote) {
       /*Pacote pacote = new Pacote();
        pacote.setNOME_PACOTE("Selecione Pacote:");
        pacote.set_ID(-1);
        List<Pacote> pacotes = new ArrayList<>();
        pacotes.add(pacote);
        pacotes.addAll(1, Pacote);
        ArrayAdapter<Pacote> arrayAdapterPacote = new ArrayAdapter(this, android.R.layout.simple_spinner_item, pacotes);
        sPacote = (Spinner) findViewById(R.id.pacote_pedido);
        sPacote.setAdapter(arrayAdapterPacote);
*/
    }

    private class PacoteTaskLoadList extends AsyncTask<Void, Void,List<Pacote>> {
        private String[] forecast = null;
        private  Pacote Pacote = null;

//    private List<Map<String,String>> listDetailsEnergyBill =null;

        List<Pacote> Pacotes= null;

        public List<Pacote> getPacotes() {
            return Pacotes;
        }

        public void setPacotes(List<Pacote> Pacotes) {
            this.Pacotes = Pacotes;
        }

        private PedidoListenerPacotes listener = null;


        public PacoteTaskLoadList(PedidoListenerPacotes listener) {
            this.listener = listener;

        }



        public  List<Pacote> getPacoteFromArrayJson(String forecastJsonStr) {

            Pacote Pacote = null;
            List<Pacote> Pacotes = null;
            try

            {

                if (forecastJsonStr != null) {

                    Pacotes = new ArrayList<Pacote>();
                    JSONObject forecastJson = new JSONObject(forecastJsonStr);
                    JSONArray ja = new JSONArray();
                    ja = forecastJson.getJSONArray("pacotes");
                    for (int i = 0; i < ja.length(); i++) {
                        Pacote = new Pacote();

                        Pacote.set_ID(ja.getJSONObject(i).getInt("_id"));
                        Pacote.setUNIDADE(ja.getJSONObject(i).getInt("unidade"));
                        Pacote.setNOME_PACOTE(ja.getJSONObject(i).getString("nome"));
                        Pacote.setPRECO(Float.parseFloat(ja.getJSONObject(i).getString("preco")));
                        Pacote.setDESCRICAO_PACOTE(ja.getJSONObject(i).getString("descricao"));
                        Pacote.setNOME_IMAGE(ja.getJSONObject(i).getString("nome_imagem"));
                        Pacote.setTIPO_PACOTE(ja.getJSONObject(i).getInt("tipo_pacote"));
                        Pacotes.add(Pacote);

                    }
                }
            } catch (
                    JSONException e
                    )

            {
                e.printStackTrace();
            }


            return Pacotes;
        }

        @Override
        protected List<Pacote> doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;

            BufferedReader reader = null;

            String forecastJson = null;
            List<Pacote> Pacotes = null;

            try {
                //Uri.Builder builder = new Uri.Builder();
                Uri.Builder builder = Uri.parse("http://10.1.1.44:8080").buildUpon();
                //builder.scheme("http");
                //  builder.authority("10.1.1.44");

                builder.appendPath("pacote");

                builder.appendQueryParameter("acao", "consultar");
                builder.appendQueryParameter("unidade", "0");
                builder.appendQueryParameter("nome", "");
                builder.appendQueryParameter("preco", "0.0");
                builder.appendQueryParameter("descricao", "");
                builder.appendQueryParameter("nome_imagem", "");
                builder.appendQueryParameter("tipo_pacote", "0");
                builder.appendQueryParameter("ids","0");


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
                Pacotes = getPacoteFromArrayJson(forecastJson);


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

            this.Pacotes = Pacotes;
            return Pacotes;
        }

        @Override
        protected void onPostExecute(List<Pacote> Pacotes) {

            Log.v(null, "Response Server Pacotes: " + Pacotes);

            listener.loadPacotes(Pacotes);

        }


    }


    @Override
    public void loadProdutos(List<Produto> produto) {
        Produto Produto = new Produto();
        Produto.setNOME("Selecione Produto:");
        Produto.set_ID(-1);
        List<Produto> Produtos = new ArrayList<>();
        Produtos.add(Produto);
        Produtos.addAll(1, produto);
        ArrayAdapter<Produto> arrayAdapterProduto = new ArrayAdapter(this, android.R.layout.simple_spinner_item , Produtos);
        sProduto = (Spinner) findViewById(R.id.produto_pedido);
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

        private PedidoListenerProdutos listener = null;


        public ProdutoTaskLoadList(PedidoListenerProdutos listener) {
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
    public void showPedido(List<Pedido> pedidos) {

        String acao = pedidos.get(0).getACAO();
        Pedido pedido =pedidos.get(0);

        if(acao.equals("inserir") )
        {
            if (pedido.get_ID() != null) {
                Toast.makeText(this, "Num Pedido Cadastrado :  "+ pedido.getNUM_PEDIDO(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Pedido Nao Cadastrado", Toast.LENGTH_LONG).show();
            }

        }else if(acao.equals("consultarnupedido")){
            if (pedido.get_ID() != null) {
                Toast.makeText(this, "Num Pedido Encontrado :  "+ pedido.getNUM_PEDIDO(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Pedido Nao Encontrado", Toast.LENGTH_LONG).show();
            }


        }else if(acao.equals("consultarnumeropedidostatus")){
            if (pedido.get_ID() != null) {
                Toast.makeText(this, "Num Pedido Encontrado :  "+ pedido.getNUM_PEDIDO(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Pedido Nao Encontrado", Toast.LENGTH_LONG).show();
            }
        }else if(acao.equals("atualizar")){
            if (pedido.get_ID() != null) {
                Toast.makeText(this, "Num Pedido Atualizado :  "+ pedido.getNUM_PEDIDO(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Pedido Nao Atualizado", Toast.LENGTH_LONG).show();
            }


        }else if(acao.equals("deletar")){
            if (pedido.get_ID() != null) {
                Toast.makeText(this, "Num Pedido Apagado :  "+ pedido.getNUM_PEDIDO(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Pedido Nao Apagado", Toast.LENGTH_LONG).show();
            }

        }


    }
}
