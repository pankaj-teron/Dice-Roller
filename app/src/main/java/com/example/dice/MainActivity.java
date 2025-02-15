package com.example.dice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView winnerText, player1Name, player2Name;
    private ImageView dice1, dice2;
    private Button rollButton;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Player Names from Intent
        Intent intent = getIntent();
        String p1 = intent.getStringExtra("PLAYER_1");
        String p2 = intent.getStringExtra("PLAYER_2");

        // Linking Views
        winnerText = findViewById(R.id.tvVar1);
        player1Name = findViewById(R.id.tvPlayer1);
        player2Name = findViewById(R.id.tvPlayer2);
        dice1 = findViewById(R.id.ivVar1);
        dice2 = findViewById(R.id.ivVar2);
        rollButton = findViewById(R.id.btVar1);

        // Set Player Names
        player1Name.setText(p1);
        player2Name.setText(p2);

        // Roll Button Functionality with Animation
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateDice(dice1);
                animateDice(dice2);

                int diceRoll1 = random.nextInt(6) + 1;
                int diceRoll2 = random.nextInt(6) + 1;

                // Delay setting the images until animation ends
                dice1.postDelayed(() -> dice1.setImageResource(getDiceImage(diceRoll1)), 500);
                dice2.postDelayed(() -> dice2.setImageResource(getDiceImage(diceRoll2)), 500);

                // Determine Winner
                if (diceRoll1 > diceRoll2) {
                    winnerText.setText("WINNER: " + p1);
                } else if (diceRoll1 < diceRoll2) {
                    winnerText.setText("WINNER: " + p2);
                } else {
                    winnerText.setText("DRAW!");
                }
            }
        });
    }

    // Helper Method to Get Dice Image
    private int getDiceImage(int roll) {
        switch (roll) {
            case 1: return R.drawable.dice1;
            case 2: return R.drawable.dice2;
            case 3: return R.drawable.dice3;
            case 4: return R.drawable.dice4;
            case 5: return R.drawable.dice5;
            default: return R.drawable.dice6;
        }
    }

    // Method to Animate Dice Rotation
    private void animateDice(ImageView dice) {
        RotateAnimation rotate = new RotateAnimation(
                0, 360,  // Start and end angle
                Animation.RELATIVE_TO_SELF, 0.5f,  // Pivot X
                Animation.RELATIVE_TO_SELF, 0.5f   // Pivot Y
        );
        rotate.setDuration(500); // Duration in milliseconds
        dice.startAnimation(rotate);
    }
}
