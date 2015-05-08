package br.edu.ifpe.pdm.cardapiolanches;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.edu.ifpe.pdm.cardapiolanches.ProdutosListActivity;

public class Dashboard extends Activity {

    private TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        text1 = (TextView) findViewById(R.id.fazer_pedido);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forwardProdutoList();
            }
        });


}
    public boolean forwardProdutoList() {

        startActivity(new Intent(this, ProdutosListActivity.class));

        return true;
    }


    public void selecionarOpcao(View view) {
        TextView textView = (TextView) view;
        String opcao = "Opção: " + textView.getText().toString();
        //Toast.makeText(this, opcao, Toast.LENGTH_LONG).show();


        switch (view.getId()) {
            case R.id.fazer_pedido:
                startActivity(new Intent(this, ProdutosListActivity.class));
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
