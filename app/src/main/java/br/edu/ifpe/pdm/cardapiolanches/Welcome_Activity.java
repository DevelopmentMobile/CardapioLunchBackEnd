package br.edu.ifpe.pdm.cardapiolanches;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class Welcome_Activity extends ActionBarActivity {

    //TextView textViewInfo;
    GifView gifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        gifView = (GifView) findViewById(R.id.gif_view);

        /*
        textViewInfo = (TextView) findViewById(R.id.textinfo);
        String stringInfo="";
        stringInfo += "Duration: "+gifView.getMovieDuration()+"\n";
        stringInfo += "W x H: "+gifView.getMovieWidth()+" x "+ gifView.getMovieHeight()+"\n";
        textViewInfo.setText(stringInfo);
        */
    }

    public void AcessarMenuOnClick(View v) {
        startActivity(new Intent(this, SelectActivity.class));
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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
*/
}
