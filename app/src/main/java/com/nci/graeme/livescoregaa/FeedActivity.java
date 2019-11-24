package com.nci.graeme.livescoregaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FeedActivity extends AppCompatActivity {

    Button liveScoresButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        liveScoresButton = findViewById(R.id.buttonLive);
        liveScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLiveScores();
            }
        });


    }//end of onCreate

    public void openLiveScores(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
