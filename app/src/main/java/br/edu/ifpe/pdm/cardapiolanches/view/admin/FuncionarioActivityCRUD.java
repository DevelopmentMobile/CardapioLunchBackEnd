package br.edu.ifpe.pdm.cardapiolanches.view.admin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.bean.Funcionario;
import br.edu.ifpe.pdm.cardapiolanches.dao.FuncionarioListener;
import br.edu.ifpe.pdm.cardapiolanches.dao.FuncionarioTask;

/**
 * Created by Richardson on 31/05/2015.
 */
public class FuncionarioActivityCRUD extends Activity implements FuncionarioListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crud_func_activity);
      /*  DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS Funcionario" );
        db.execSQL("CREATE TABLE Funcionario (" +
                "    _id integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "    unidade_estoque integer NOT NULL," +
                "    nome text UNIQUE," +
                "    preco decimal(6,2) NOT NULL," +
                "    descricao text," +
                "    nome_imagem text," +
                "    tempo_pronto_Funcionario integer NOT NULL," +
                "    categoria text NOT NULL" +
                ");");
            db.close();
        dbHelper.close();
        */
    }


    public void buttonInsertClick(View view) {

        int tipoFuncionario =    Integer.parseInt(((EditText) findViewById(R.id.tipo_funcionario)).getText().toString());
        String login =    ((EditText)findViewById(R.id.login)).getText().toString();
        String senha =    ((EditText)findViewById(R.id.senha)).getText().toString();

        final Funcionario[] arrayFuncionario =  new Funcionario[1];
   final      Funcionario Funcionario =  new Funcionario(login,senha ,tipoFuncionario);

        Funcionario.setACAO("inserir");

           arrayFuncionario[0] = Funcionario;
        new FuncionarioTask(this).execute(arrayFuncionario);



    }

    public void buttonUpdateClick(View view) {


        int tipoFuncionario =    Integer.parseInt(((EditText) findViewById(R.id.tipo_funcionario)).getText().toString());
        String login =    ((EditText)findViewById(R.id.login)).getText().toString();
        String senha =    ((EditText)findViewById(R.id.senha)).getText().toString();

        final Funcionario[] arrayFuncionario =  new Funcionario[1];
        final      Funcionario Funcionario =  new Funcionario(login,senha ,tipoFuncionario);

        Funcionario.setACAO("atualizar");

        arrayFuncionario[0] = Funcionario;
        new FuncionarioTask(this).execute(arrayFuncionario);



    }

    public void buttonDeleteClick(View view) {


        int tipoFuncionario =    Integer.parseInt(((EditText) findViewById(R.id.tipo_funcionario)).getText().toString());
        String login =    ((EditText)findViewById(R.id.login)).getText().toString();
        String senha =    ((EditText)findViewById(R.id.senha)).getText().toString();

        final Funcionario[] arrayFuncionario =  new Funcionario[1];
        final      Funcionario Funcionario =  new Funcionario(login,senha ,tipoFuncionario);

        Funcionario.setACAO("deletar");

        arrayFuncionario[0] = Funcionario;
        new FuncionarioTask(this).execute(arrayFuncionario);


    }


    public void buttonQueryClick(View view) {

        int tipoFuncionario =    Integer.parseInt(((EditText) findViewById(R.id.tipo_funcionario)).getText().toString());
        String login =    ((EditText)findViewById(R.id.login)).getText().toString();
        String senha =    ((EditText)findViewById(R.id.senha)).getText().toString();

        final Funcionario[] arrayFuncionario =  new Funcionario[1];
        final      Funcionario Funcionario =  new Funcionario(login,senha ,tipoFuncionario);

        Funcionario.setACAO("consultarnome");

        arrayFuncionario[0] = Funcionario;
        new FuncionarioTask(this).execute(arrayFuncionario);


    }


    @Override
    public void showFuncionario(Funcionario Funcionario) {
        if(Funcionario != null) {
            Toast.makeText(this, Funcionario.toString(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Funcionario Não Cadastrado!!", Toast.LENGTH_SHORT).show();
        }
    }
}
