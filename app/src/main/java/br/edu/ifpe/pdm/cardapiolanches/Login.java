package br.edu.ifpe.pdm.cardapiolanches;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
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

import br.edu.ifpe.pdm.cardapiolanches.bean.Funcionario;
import br.edu.ifpe.pdm.cardapiolanches.dao.FuncionarioDAO;
import br.edu.ifpe.pdm.cardapiolanches.view.admin.QueryResultFuncionarios;
import br.edu.ifpe.pdm.cardapiolanches.utils.Constantes;



public class Login extends ActionBarActivity {
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


    public static final String MY_TAG = "the_custom_message";


    private FuncionarioDAO FuncionarioDAO ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usuario = (EditText) findViewById(R.id.usuario);
        senha = (EditText) findViewById(R.id.senha);
        // manterConectado = (CheckBox) findViewById(R.id.manterConectado);
        btAcessar = (Button) findViewById(R.id.btAcessar);
        btCadastrar = (Button) findViewById(R.id.btCadastrar);
        rgTipoUsuario = (RadioGroup) findViewById(R.id.tipoUsuario);

        FuncionarioDAO =  new FuncionarioDAO(this);
        FuncionarioDAO.open();
/*
        SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
        boolean conectado =
                preferencias.getBoolean(MANTER_CONECTADO,false);
        if(conectado){
            startActivity(new Intent(this, Dashboard.class));
        }
*/


        Funcionario = new Funcionario();
        //   Funcionario.setSENHA("admin");

                /*Funcionario.setNOME("admin");
                Funcionario.setFONE("900000000");
                Funcionario.setLOGIN("admin");*/


      //  Funcionario.setTIPO_FUNCIONARIO(99);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tipo = rgTipoUsuario.getCheckedRadioButtonId();

                if (tipo == R.id.garcom) {
                    Funcionario.setTIPO_FUNCIONARIO(Constantes.GARCOM);
                } else if (tipo == R.id.conzinheiro) {
                    Funcionario.setTIPO_FUNCIONARIO(Constantes.CONZINHEIRO);
                }

                Funcionario.setSENHA(senha.getText().toString());
                Funcionario.setLOGIN(usuario.getText().toString());

              Long resultado =   FuncionarioDAO.criarUsuario(Funcionario);
              //  FuncionarioDAO.close();
                if (resultado > 0) {

                    startActivity(new Intent(Login.this, QueryResultFuncionarios.class));
                } else {
                    Toast.makeText(Login.this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
                }

                //startActivity(new Intent(Login.this, QueryResultFuncionarios.class));



            }
        });

        btAcessar.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {

                                             Log.i(MY_TAG, "Usuario:" + usuario.getText().toString() + "Senha:" + senha.getText().toString() );

                                     ///   FuncionarioDAO = new FuncionarioDAO(Login.this);
                                             FuncionarioDAO.read();
                                             Funcionario =   FuncionarioDAO.consultarFuncionarioPorLoginSenha(usuario.getText().toString(), senha.getText().toString());
                                            // FuncionarioDAO.close();
                                             if (Funcionario != null) {

                                               /*  SharedPreferences preferencias =
                                                         getPreferences(MODE_PRIVATE);
                                                 SharedPreferences.Editor editor = preferencias.edit();
                                                 editor.putBoolean(MANTER_CONECTADO,
                                                         manterConectado.isChecked());
                                                 editor.putString(NOME, Funcionario.getNOME());
                                                 editor.putString(FONE, Funcionario.getFONE());
                                                 editor.putString(LOGIN, Funcionario.getLOGIN());

                                                 editor.commit();*/
                                                 Log.v(MY_TAG, "Usuario encontrado!");

                                                 startActivity(new Intent(Login.this, Dashboard.class));

                                             } else {
                                                 Log.v(MY_TAG, "Usuario nao encontrado!");

                                                 Toast toast = Toast.makeText(Login.this, R.string.messagemerrologin,
                                                         Toast.LENGTH_SHORT);
                                                 toast.show();
                                             }



                                         }
                                     }
        );







        Log.v(MY_TAG, "Oncreate sucess");
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



}
