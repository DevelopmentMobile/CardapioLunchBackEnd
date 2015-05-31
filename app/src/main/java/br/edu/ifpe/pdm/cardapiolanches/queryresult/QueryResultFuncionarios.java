package br.edu.ifpe.pdm.cardapiolanches.queryresult;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.bean.Funcionario;
import br.edu.ifpe.pdm.cardapiolanches.dao.FuncionarioDAO;

public class QueryResultFuncionarios extends Activity {

    private FuncionarioDAO FuncionarioDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_result_funcionario);

        LinearLayout layout = (LinearLayout) findViewById(R.id.query_result);

        FuncionarioDAO = new FuncionarioDAO(this);
        FuncionarioDAO.read();

        List<Funcionario> FuncionarioList = FuncionarioDAO.consultarTodosFuncionario();

        for (Funcionario Funcionario : FuncionarioList) {
            String entry = Funcionario.get_ID() + ": ";
            entry +=  Funcionario.getLOGIN() + " = ";
            entry += Funcionario.getTIPO_FUNCIONARIO();


            TextView text = new TextView(this);
            text.setText(entry);
            text.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.addView(text);
        }

        FuncionarioDAO.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_query_result_funcionario, menu);
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
