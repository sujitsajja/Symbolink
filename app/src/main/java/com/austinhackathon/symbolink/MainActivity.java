package com.austinhackathon.symbolink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public Button easyButton;
    public Button mediumButton;
    public Button hardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        easyButton = (Button) findViewById(R.id.easyButton);
        mediumButton = (Button) findViewById(R.id.mediumButton);
        hardButton = (Button) findViewById(R.id.hardButton);
    }

    public void buttonOnClicked(View v) {
        Button button = (Button) v;
        Intent game = new Intent(button.getContext(), GameActivity.class);
        game.putExtra("GAME_LEVEL", button.getText().toString());
        startActivity(game);
    }

    public void displayHelp(View v) {
        Button button = (Button) v;
        Intent help = new Intent(button.getContext(), HelpActivity.class);
        startActivity(help);
    }

}
