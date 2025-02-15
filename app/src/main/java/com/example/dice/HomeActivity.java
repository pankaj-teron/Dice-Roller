package com.example.dice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private EditText player1Input, player2Input;
    private Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Linking Views
        player1Input = findViewById(R.id.etPlayer1);
        player2Input = findViewById(R.id.etPlayer2);
        startGameButton = findViewById(R.id.btnStartGame);

        // Button Click Listener
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Player Names
                String player1 = player1Input.getText().toString().trim();
                String player2 = player2Input.getText().toString().trim();

                // Default Names if Empty
                if (player1.isEmpty()) player1 = "Player 1";
                if (player2.isEmpty()) player2 = "Player 2";

                // Start MainActivity & Pass Data
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.putExtra("PLAYER_1", player1);
                intent.putExtra("PLAYER_2", player2);
                startActivity(intent);
            }
        });
    }
}
