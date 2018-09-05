package br.com.senaijandira.senaiforca16;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by 16254850 on 13/08/2018.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void iniciarJogo(View v){
        Intent Game = new Intent(this, GameActivity.class);
        startActivity(Game);
        finish();
    }
}
