package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverAct extends AppCompatActivity {

    Button button;
    TextView score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        button=findViewById(R.id.btn);
        score=findViewById(R.id.score);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(GameOverAct.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
