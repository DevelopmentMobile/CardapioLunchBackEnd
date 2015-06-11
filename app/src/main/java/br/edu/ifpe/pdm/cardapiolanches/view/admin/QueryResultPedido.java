package br.edu.ifpe.pdm.cardapiolanches.view.admin;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.bean.Funcionario;
import br.edu.ifpe.pdm.cardapiolanches.bean.Pacote;
import br.edu.ifpe.pdm.cardapiolanches.bean.Pedido;
import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;
import br.edu.ifpe.pdm.cardapiolanches.dao.DatabaseHelper;

/**
 * Created by Richardson on 10/06/2015.
 */
public class QueryResultPedido extends Activity implements AdapterView.OnItemClickListener{


private ViewBinderProdutos simpleCursorAdapter;
private ListView listView;
public  int sumTotalPedidios = 0;
public Menu menuActivity;
private Map<String,Produto> hashMapMesa = new HashMap<String,Produto>();
private int produtoSelecionada;
private final String[] de = {"_id", "tempo_total_pedido","quantidade",
        "funcionario_id", "produto_id", "pacote_id","num_pedido"};
private final int[] para = {R.id.num_pedido_admin, R.id.tempo_total_pedido_admin, R.id.quantidade_pedido_admin,
        R.id.funcioario_pedido_admin, R.id.pacote_pedido_admin, R.id.produto_pedido_admin, R.id.group_pedido_admin
        };


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pedido_admin_listview);

        listView= (ListView) findViewById(R.id.pedido_admin_listview);
        simpleCursorAdapter = new ViewBinderProdutos (this, R.layout.query_result_pedido_admin, listPedidoPorProdutos(),
        de, para );

        listView.setAdapter(simpleCursorAdapter);
        }


//Colocar numero da mesa ou atualiza tabela com o campo com num+id
public Cursor listPedidoPorProdutos(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // ContentValues values = new ContentValues();
     ///   String[] whereArgs = new String[]{Integer.toString(Constantes.NUM_MESA,Constantes.PEDIDO_REALIZADO)};
        //String query = " SELECT * FROM// pedido pe INNER JOIN produto  po ON pe.produto_id = po._id ";
       String query = " SELECT * FROM pedido ORDER BY num_pedido ";
   // String query = "SELECT * FROM pedido INNER JOIN produto ON pedido.produto_id = produto._id WHERE pedido.num_mesa = 5";
        Cursor temp =db.rawQuery(query, null);
              //  dbHelper.close();
        return temp;

        }



public int atualizaPedido(Pedido p){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();


        values.put(DatabaseHelper.Pedido.QUANTIDADE, p.getQUANTIDADE());
        values.put(DatabaseHelper.Pedido.TEMPO_TOTAL_PEDIDO, p.getTEMPO_TOTAL_PEDIDO());
        values.put(DatabaseHelper.Pedido.NUM_MESA,p.getNUM_MESA());
        values.put(DatabaseHelper.Pedido.STATUS_PEDIDO,p.getSTATUS_PEDIDO());
         values.put(DatabaseHelper.Pedido.FUNCIONARIO_ID,p.getFUNCIONARIO_ID());
            values.put(DatabaseHelper.Pedido.PACOTE_ID,p.getPACOTE_ID());
            values.put(DatabaseHelper.Pedido.PRODUTO_ID, p.getPRODUTO_ID());


    String selection = DatabaseHelper.Pedido._ID + " LIKE ?";
        String[] selectionArgs = {p.get_ID()+ "" };
        int count = db.update(DatabaseHelper.Pedido.TABELA, values, selection, selectionArgs);
        Toast toast = Toast.makeText(this,
        "Registros atualizados: " + count, Toast.LENGTH_SHORT);
        toast.show();

        return count;
        }



private class ViewBinderProdutos  extends SimpleCursorAdapter {



    private RadioButton rbProdutoSelecionado;

    private final Cursor c;
    private final Context context;
    private final int mNameIndex;
    private final int mPrecoIndex;
    private final int mTempoProntoProduto;
    private final int mTempoProntoPedido;
    private final int mNumMesa;
    private final int mQuantidade;
    private final int mStatusPedido;
    private final int mIdIndex;
    private final int mIdGroup;
    private final int mProdutoId;
    private final int mPacoteId;
    private final int mFuncionarioId;

    private final LayoutInflater mLayoutInflater;
    public  int sum_tempo_total_pronto_pedido_pago;
    public float sum_valor_total_pedido_pago;
    private final List<Pedido> listPedidos = new ArrayList<Pedido>();;
    private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();
    private final ArrayAdapter<Produto> adapterProduto;
    private final ArrayAdapter<Pacote> adapterPacote;
    private final ArrayAdapter<Funcionario> adapterFuncionario;
    private final Map<Integer,Boolean>  listGroupBy = new HashMap<Integer,Boolean>();
    private Pedido pedidoBinder;

    private ArrayList<Boolean> rbEditar = new ArrayList<Boolean>();
    private ArrayList<Boolean> rbDeletar = new ArrayList<Boolean>();



    public ViewBinderProdutos(Context context, int layout, Cursor c, String[] from,
                              int[] to) {
        super(context, layout, c, from, to);
        this.c = c;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(this.context);
        this.mNameIndex = c.getColumnIndex(DatabaseHelper.Produto.NOME);
        this.mTempoProntoProduto = c.getColumnIndex(DatabaseHelper.Produto.TEMPO_PRONTO_PRODUTO);
        this.mNumMesa = c.getColumnIndex(DatabaseHelper.Produto.CATEGORIA);
        this.mPrecoIndex = c.getColumnIndex(DatabaseHelper.Produto.PRECO);



        //this.mIdIndex = c.getColumnIndex(DatabaseHelper.Pedido._ID);
        this.mIdIndex = c.getColumnIndex(DatabaseHelper.Pedido.NUM_PEDIDO);
        this.mIdGroup = c.getColumnIndex(DatabaseHelper.Pedido._ID);
        this.mTempoProntoPedido = c.getColumnIndex(DatabaseHelper.Pedido.TEMPO_TOTAL_PEDIDO);
        this.mStatusPedido = c.getColumnIndex(DatabaseHelper.Pedido.STATUS_PEDIDO);
        this.mQuantidade = c.getColumnIndex(DatabaseHelper.Pedido.QUANTIDADE);
        mFuncionarioId = c.getColumnIndex(DatabaseHelper.Pedido.FUNCIONARIO_ID);
        mProdutoId = c.getColumnIndex(DatabaseHelper.Pedido.PRODUTO_ID);
        mPacoteId = c.getColumnIndex(DatabaseHelper.Pedido.PACOTE_ID);

        /*this.sum_tempo_total_pronto_pedido_pago = 0;
        this.sum_valor_total_pedido_pago = 0;
*/
        for (int i = 0; i < this.getCount(); i++) {
            rbEditar.add(i, false); // initializes all items value with false
            rbDeletar.add(i,false);
        }

        adapterProduto = new ArrayAdapter(this.context, android.R.layout.simple_spinner_item, consultarTodosProduto());
        adapterFuncionario =  new ArrayAdapter(this.context, android.R.layout.simple_spinner_item,consultarTodosFuncionario());
        adapterPacote =  new ArrayAdapter(this.context, android.R.layout.simple_spinner_item,  consultarTodosPacote());
    }


    class ViewHolder {

        protected TextView preco;
        protected TextView nome;
       // protected TextView numMesa;
        protected TextView groupNumPedido;
       // protected TextView tempo_produto_entrega;

        protected EditText tempo_produto_pedido;
        protected EditText quantidade;
        protected TextView num_pedido;
        protected Spinner funcinario_id;
        protected Spinner produto_id;
        protected Spinner pacote_id;

        protected RadioGroup rgStatusPedido;
        protected RadioButton statusAtendido;
        protected RadioButton statusRealizado;
        protected RadioButton statusPronto;
        protected RadioButton statusEntregue;
        protected RadioButton statusPago;
        protected RadioGroup rgEdicao;
        protected RadioButton editar;
        protected RadioButton deletar;

      //  protected CheckBox pedidoSelecionado;
    }



    @Override
    public View getView(final int pos, View inView, ViewGroup parent) {


        if(c.moveToPosition(pos)) {

            View view = null;

            final ViewHolder viewHolder;
            if (inView == null) {
                LayoutInflater inflator = mLayoutInflater;
                view = inflator.inflate(R.layout.query_result_pedido_admin, null);
                viewHolder = new ViewHolder();
              //  final Produto data = cursorToProduto(this.c);
              final Pedido  pedido = cursorToPedido(this.c);
               // final Funcionario func = cursorToFuncionario(this.c);
               //final Pacote pacote = cursorToPacote(this.c);

                listPedidos.add(pedido);
                viewHolder.num_pedido = (TextView) view.findViewById(R.id.num_pedido_admin);
                viewHolder.groupNumPedido = (TextView) view.findViewById(R.id.group_pedido_admin);


                viewHolder.quantidade = (EditText) view.findViewById(R.id.quantidade_pedido_admin);
                viewHolder.tempo_produto_pedido = (EditText) view.findViewById(R.id.tempo_total_pedido_admin);


                //viewHolder.preco = (TextView) view.findViewById(R.id.valor_pedido_func);
                viewHolder.rgStatusPedido = (RadioGroup) view.findViewById((R.id.rg_pedido_admin));
                viewHolder.rgEdicao = (RadioGroup) view.findViewById((R.id.rg_edicao_pedido_admin));

                viewHolder.statusAtendido = (RadioButton) view.findViewById((R.id.atendido_pedido_admin));
                viewHolder.statusPronto = (RadioButton) view.findViewById((R.id.pronto_pedido_admin));
                viewHolder.statusPago = (RadioButton) view.findViewById((R.id.pagamento_pedido_admin));
                viewHolder.statusEntregue = (RadioButton) view.findViewById((R.id.entregue_pedido_admin));

                viewHolder.statusRealizado = (RadioButton) view.findViewById((R.id.realizado_pedido_admin));
                viewHolder.editar = (RadioButton) view.findViewById((R.id.editar_pedido_admin));

                viewHolder.deletar = (RadioButton) view.findViewById((R.id.deletar_pedido_admin));

                viewHolder.funcinario_id = (Spinner) view.findViewById((R.id.funcioario_pedido_admin));
                viewHolder.pacote_id = (Spinner) view.findViewById((R.id.pacote_pedido_admin));
                viewHolder.produto_id = (Spinner) view.findViewById((R.id.produto_pedido_admin));




                view.setTag(viewHolder);
            } else {
                view = inView;
                viewHolder =  (ViewHolder) view.getTag();
            }

            ((RadioButton) viewHolder.rgStatusPedido.getChildAt(c.getInt(mStatusPedido) -1)).setChecked(true);
            //((RadioButton) viewHolder.rgEdicao.getChildAt(c.getInt(mStatusPedido) -1)).setChecked(true);


           // viewHolder.preco.setText(Float.toString(c.getFloat(mPrecoIndex) * c.getInt(mQuantidade)));
       //   viewHolder.tempo_produto_pedido.setText(Integer.toString(c.getInt(mTempoProntoProduto) * c.getInt(mQuantidade)) );
            viewHolder.editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rbEditar.set(pos, true);
                    Toast.makeText(context, "Registro p/ Editar: "+ pos , Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.deletar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rbDeletar.set(pos, true);
                    Toast.makeText(context, "Registro p/ deletar: "+ pos , Toast.LENGTH_SHORT).show();
                }
            });
            if (!listGroupBy.containsKey(c.getInt(mIdIndex))) {

                viewHolder.groupNumPedido.setText(Integer.toString(c.getInt(mIdIndex)));
                //tipoProduto = textRepresentation;
                listGroupBy.put(mIdIndex, true);
                viewHolder.groupNumPedido.setVisibility(View.VISIBLE);
            } else {
                viewHolder.groupNumPedido.setVisibility(View.GONE);
            }

            viewHolder.num_pedido.setText(c.getString(mIdIndex));
            viewHolder.quantidade.setText(c.getString(mQuantidade));
        //   viewHolder.tempo_produto_entrega.setText(Integer.toString(Float.toString(c.getFloat(mTempoProntoProduto) * c.getInt(mQuantidade)));

            viewHolder.funcinario_id.setAdapter(adapterFuncionario);

           // viewHolder.funcinario_id.setAdapter(adapterFuncionario);

            viewHolder.produto_id.setAdapter(adapterProduto);
            viewHolder.pacote_id.setAdapter(adapterPacote);

            ///viewHolder.pacote_id.setSelection(adapterPacote.getPosition());


          ///  sum_valor_total_pedido_pago = sum_valor_total_pedido_pago + c.getFloat(mPrecoIndex) * c.getInt(mQuantidade) ;
            // System.out.println(  this.sum_valor_total_pedido_pago);
            // viewHolder.total_valor.setText(Float.toString(sum_valor_total_pedido_pago));
       //     sum_tempo_total_pronto_pedido_pago = sum_tempo_total_pronto_pedido_pago + c.getInt(mTempoProntoProduto) * c.getInt(mQuantidade) ;
            // viewHolder.total_tempo.setText(Integer.toString(sum_tempo_total_pronto_pedido_pago));

            return view;
        }

        return inView;

    }






}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menuActivity; this adds items to the action bar if it is present.

        //super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.pedido_produto_func, menu);

        // this.menuActivity = menu;

        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }else*/
        int contAtualizar = 0;
        int contDeletar = 0;
        if (id == R.id.dashboard_admin) {
            startActivity(new Intent(this, DashboardAdmin.class));
            return true;
        }else if (id == R.id.atualizar_pedido_func) {
            for(int i=0;i< simpleCursorAdapter.rbEditar.size() ; i++) {
                if (simpleCursorAdapter.rbEditar.get(i)) {
                    //Settar id do funcionario? -qual importancia???
                    contAtualizar++;
                } else if (simpleCursorAdapter.rbDeletar.get(i)) {
                    contDeletar++;
                    //Settar id do funcionario? -qual importancia???
                }

            }

            Toast.makeText(this, "Registros Atualizados: " + contAtualizar +"\n"+ "Registros Deletados: "+ contDeletar, Toast.LENGTH_SHORT).show();

            /*

            listView= (ListView) findViewById(R.id.pedidos_func_listview);
            simpleCursorAdapter = new ViewBinderProdutos (this, R.layout.pedidos_func, listPedidoPorProdutos(),
                    de, para );


            listView.setAdapter(simpleCursorAdapter);
*/

            return true;
        }





        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.produtoSelecionada = position;
        Intent intent = new Intent(this, br.edu.ifpe.pdm.cardapiolanches.PedidosListActivity.class);

        // intent.putExtra("produto", (java.io.Serializable) produtos.get(produtoSelecionada ));
        startActivity(intent);
    }




    private Funcionario cursorToFuncionario(Cursor cursor) {
        Funcionario Funcionario = new Funcionario();
        Funcionario.set_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Funcionario._ID)));
        Funcionario.setLOGIN(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Funcionario.LOGIN)));
        Funcionario.setSENHA(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Funcionario.SENHA)));
        Funcionario.setTIPO_FUNCIONARIO(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Funcionario.TIPO_FUNCIONARIO)));

        return Funcionario;
    }
    public List<Funcionario> consultarTodosFuncionario() {
        // Cursor cursor = db.query(DatabaseHelper.Funcionario.TABELA, DatabaseHelper.Funcionario.COLUNAS,null,null,null,null,null);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Funcionario.TABELA, null);

        List<Funcionario> funcionarioList = new ArrayList<Funcionario>();

       /* Funcionario Funcionario = new Funcionario();
        Funcionario.set_ID(-1);
        Funcionario.setLOGIN("Selecione opcao:");
        funcionarioList.add(Funcionario);
*/
        if (cursor.getCount() >0 && cursor != null) {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Funcionario newUser = cursorToFuncionario(cursor);
                funcionarioList.add(newUser);
                cursor.moveToNext();
            }

            cursor.close();
        }

        dbHelper.close();
        return funcionarioList;
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

        dbHelper.close();
        return ProdutoList;
    }


    private Pacote cursorToPacote(Cursor cursor) {
        Pacote Pacote = new Pacote();
        Pacote.set_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pacote._ID)));
        Pacote.setNOME_PACOTE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Pacote.NOME_PACOTE)));
        //  Pacote.setDESCRICAO_PACOTE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Pacote.DESCRICAO_PACOTE)));
        Pacote.setTIPO_PACOTE(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pacote.TIPO_PACOTE)));
        return Pacote;
    }
    public List<Pacote> consultarTodosPacote() {
        // Cursor cursor = db.query(DatabaseHelper.Pacote.TABELA, DatabaseHelper.Pacote.COLUNAS,null,null,null,null,null);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +  DatabaseHelper.Pacote.TABELA, null);

        List<Pacote> PacoteList = new ArrayList<Pacote>();


        if (cursor.getCount() >0 && cursor != null) {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Pacote newUser = cursorToPacote(cursor);
                PacoteList.add(newUser);
                cursor.moveToNext();
            }

            cursor.close();
        }
        dbHelper.close();
        return PacoteList;
    }




    private Produto cursorToProduto(Cursor cursor) {

        Produto Produto = new br.edu.ifpe.pdm.cardapiolanches.bean.Produto();
        Produto.set_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Produto._ID)));
        Produto.setNOME_IMAGEM(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.NOME_IMAGEM)));
        Produto.setNOME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.NOME)));
        Produto.setCATEGORIA(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.CATEGORIA)));
        Produto.setDESCRICAO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Produto.DESCRICAO)));
        Produto.setPRECO(cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.Produto.PRECO)));
        Produto.setTEMPO_PRONTO_PRODUTO(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Produto.TEMPO_PRONTO_PRODUTO)));
        Produto.setUNIDADE_ESTOQUE(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Produto.UNIDADE_ESTOQUE)));


        return Produto;
    }

    private Pedido cursorToPedido(Cursor cursor) {

        Pedido Produto = new Pedido();
        Produto.set_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido._ID)));
        Produto.setTEMPO_TOTAL_PEDIDO(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.TEMPO_TOTAL_PEDIDO)));
        Produto.setSTATUS_PEDIDO(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.STATUS_PEDIDO)));
        Produto.setQUANTIDADE(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.QUANTIDADE)));
        Produto.setPRODUTO_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.PRODUTO_ID)));
        Produto.setPACOTE_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.PACOTE_ID)));
        Produto.setFUNCIONARIO_ID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.FUNCIONARIO_ID)));
        Produto.setNUM_MESA(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Pedido.NUM_MESA)));


        return Produto;
    }

}