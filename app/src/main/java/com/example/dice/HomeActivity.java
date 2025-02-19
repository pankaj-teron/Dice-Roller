package com.example.dice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private EditText etPlayer1, etPlayer2;
    private RadioGroup rgGameModes;
    private Button btnStartGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Linking UI Elements
        etPlayer1 = findViewById(R.id.etPlayer1);
        etPlayer2 = findViewById(R.id.etPlayer2);
        rgGameModes = findViewById(R.id.rgGameModes);
        btnStartGame = findViewById(R.id.btnStartGame);



        // Start Game Button Click
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player1 = etPlayer1.getText().toString().trim();
                String player2 = etPlayer2.getText().toString().trim();

                // Ensure both names are entered
                if (player1.isEmpty() || player2.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Please enter both player names!", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Get selected game mode
                int selectedModeId = rgGameModes.getCheckedRadioButtonId();
                String gameMode = "Classic"; // Default mode
                if (selectedModeId == R.id.rbBestOfThree) {
                    gameMode = "BestOfThree";
                } else if (selectedModeId == R.id.rbChallenge) {
                    gameMode = "Race";
                }


                // Pass data to MainActivity
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.putExtra("PLAYER_1", player1);
                intent.putExtra("PLAYER_2", player2);
                intent.putExtra("GAME_MODE", gameMode); //  Pass radio button-selected mode
                startActivity(intent);

            }
        });
    }
}
