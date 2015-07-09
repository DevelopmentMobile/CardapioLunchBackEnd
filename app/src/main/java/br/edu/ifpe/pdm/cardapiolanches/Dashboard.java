package br.edu.ifpe.pdm.cardapiolanches;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.edu.ifpe.pdm.cardapiolanches.view.admin.PacoteActivityCRUD;
import br.edu.ifpe.pdm.cardapiolanches.view.admin.PedidoActivityCRUD;
import br.edu.ifpe.pdm.cardapiolanches.view.admin.ProdutoActivityCRUD;

public class Dashboard extends Activity {

    private TextView text1;
    private TextView text2;
    private TextView text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_admin);

        text1 = (TextView) findViewById(R.id.pedidos_admin);
        text2 = (TextView) findViewById(R.id.pacotes_admin);
        text3 = (TextView) findViewById(R.id.produtos_admin);


    }


    public void selecionarOpcao(View view) {
        TextView textView = (TextView) view;
        String opcao = "Opção: " + textView.getText().toString();
        //Toast.makeText(this, opcao, Toast.LENGTH_LONG).show();


        switch (view.getId()) {
            case R.id.pedidos_admin:
                startActivity(new Intent(this, PedidoActivityCRUD.class));
                break;
            case R.id.produtos_admin:
                startActivity(new Intent(this, ProdutoActivityCRUD.class));
                break;
            case R.id.pacotes_admin:
                startActivity(new Intent(this, PacoteActivityCRUD.class));
                break;


        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
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
