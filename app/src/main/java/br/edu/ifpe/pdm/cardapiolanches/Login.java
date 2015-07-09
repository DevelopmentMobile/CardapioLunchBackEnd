package br.edu.ifpe.pdm.cardapiolanches;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.bean.Funcionario;
import br.edu.ifpe.pdm.cardapiolanches.dao.FuncionarioDAO;
import br.edu.ifpe.pdm.cardapiolanches.dao.FuncionarioListener;
import br.edu.ifpe.pdm.cardapiolanches.dao.FuncionarioTask;
import br.edu.ifpe.pdm.cardapiolanches.utils.Constantes;
import br.edu.ifpe.pdm.cardapiolanches.view.admin.DashboardAdmin;
import br.edu.ifpe.pdm.cardapiolanches.view.func.PedidoAtendidoProdutoCozinheiroActivity;


public class Login extends Activity implements FuncionarioListener {
    private static final String MANTER_CONECTADO = "manter_conectado";
    private static final String NOME = "nome_conectado";
    private static final String FONE = "fone_conectado";
    private static final String LOGIN = "login_conectado";
    private EditText usuario;
    private EditText senha;
    private CheckBox manterConectado;
    private Button btCadastrar;
    private Button btAcessar;
    private RadioGroup rgTipoUsuario;
    private         Funcionario Funcionario = null;


    public static final String MY_TAG = "login";


    private FuncionarioDAO FuncionarioDAO ;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usuario = (EditText) findViewById(R.id.usuario);
        senha = (EditText) findViewById(R.id.senha);
        btAcessar = (Button) findViewById(R.id.btAcessar);
        btCadastrar = (Button) findViewById(R.id.btCadastrar);
        rgTipoUsuario = (RadioGroup) findViewById(R.id.tipoUsuario);
    }



    public void buttonInsertClick(View view) {
//        int tipoFuncionario =    Integer.parseInt(((EditText) findViewById(R.id.tipo_funcionario)).getText().toString());

        int tipo = rgTipoUsuario.getCheckedRadioButtonId();


        String login =    ((EditText)findViewById(R.id.usuario)).getText().toString();
        String senha =    ((EditText)findViewById(R.id.senha)).getText().toString();

        final Funcionario[] arrayFuncionario =  new Funcionario[1];
        Integer tipoFuncionario = -1;
        if (tipo == R.id.garcom) {
            tipoFuncionario = Constantes.GARCOM;
        } else if (tipo == R.id.conzinheiro) {
            tipoFuncionario = Constantes.CONZINHEIRO;

        }else if (tipo == R.id.empresario) {
            tipoFuncionario = Constantes.ADMIN;
        }

        final      Funcionario Funcionario =  new Funcionario(login,senha ,tipoFuncionario);


        Funcionario.setACAO("inserir");
        Log.v(MY_TAG, "Consultar Funcionario: " + Funcionario);
        arrayFuncionario[0] = Funcionario;
        new FuncionarioTask(this).execute(arrayFuncionario);


    }


    public void buttonConsultarClick(View view) {

        int tipo = rgTipoUsuario.getCheckedRadioButtonId();


        String login =    ((EditText)findViewById(R.id.usuario)).getText().toString();
        String senha =    ((EditText)findViewById(R.id.senha)).getText().toString();

        final Funcionario[] arrayFuncionario =  new Funcionario[1];
        Integer tipoFuncionario = -1;
        if (tipo == R.id.garcom) {
            tipoFuncionario = Constantes.GARCOM;
        } else if (tipo == R.id.conzinheiro) {
            tipoFuncionario = Constantes.CONZINHEIRO;

        }else if (tipo == R.id.empresario) {
            tipoFuncionario = Constantes.ADMIN;
        }

        final      Funcionario Funcionario =  new Funcionario(login,senha ,tipoFuncionario);
        Log.v(MY_TAG, "Consultar Funcionario: " + Funcionario);
        Funcionario.setACAO("consultarnomesenha");

        arrayFuncionario[0] = Funcionario;
        new FuncionarioTask(this).execute(arrayFuncionario);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showFuncionario(List<Funcionario> funcionarios) {

        for (Funcionario funcionario : funcionarios) {

            Log.v(MY_TAG, "Funcionario: " + funcionario);
            if (funcionario.getACAO().equals("inserir")) {

                if (funcionario.get_ID() != null) {
                    Toast.makeText(this, "Usuario Cadastrado com Sucesso !!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Usuario Nao encotrado !!!", Toast.LENGTH_LONG).show();
                }


            } else if (funcionario.getACAO().equals("consultarnomesenha")) {
                if (funcionario.get_ID() != null) {
                    if (funcionario.getTIPO_FUNCIONARIO() == Constantes.CONZINHEIRO) {
                        startActivity(new Intent(this, PedidoAtendidoProdutoCozinheiroActivity.class));

                    } else if (funcionario.getTIPO_FUNCIONARIO() == Constantes.GARCOM) {
                        startActivity(new Intent(this, PedidoAtendidoProdutoCozinheiroActivity.class));
                    } else if (funcionario.getTIPO_FUNCIONARIO() == Constantes.ADMIN) {

                        startActivity(new Intent(this, DashboardAdmin.class));
                    }


                } else {
                    Toast.makeText(this, "Usuario nao encotrado", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
