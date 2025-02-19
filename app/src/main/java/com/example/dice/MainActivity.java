package com.example.dice;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView winnerText, player1Name, player2Name, scoreText, modeText;
    private ImageView dice1, dice2;
    private Button rollButton;
    private Random random = new Random();

    private int player1Score = 0, player2Score = 0, roundsPlayed = 0;
    private boolean isPlayer1Turn = true;
    private String currentGameMode;
    private final int TARGET_SCORE = 100;
    private int maxRounds = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Data from Intent
        Intent intent = getIntent();
        String p1 = intent.getStringExtra("PLAYER_1");
        String p2 = intent.getStringExtra("PLAYER_2");
        currentGameMode = intent.getStringExtra("GAME_MODE");

        // Linking Views
        modeText = findViewById(R.id.tvMode);
        winnerText = findViewById(R.id.tvVar1);
        player1Name = findViewById(R.id.tvPlayer1);
        player2Name = findViewById(R.id.tvPlayer2);
        scoreText = findViewById(R.id.tvScore);
        dice1 = findViewById(R.id.ivVar1);
        dice2 = findViewById(R.id.ivVar2);
        rollButton = findViewById(R.id.btVar1);

        // Set Player Names
        player1Name.setText(p1);
        player2Name.setText(p2);

        // Set the Mode Name
        modeText.setText("Mode: " + currentGameMode);
        Toast.makeText(this, "Game Mode: " + currentGameMode, Toast.LENGTH_SHORT).show();

        // Set game rules
        if ("BestOfThree".equals(currentGameMode)) {
            maxRounds = 3;
        } else if ("Race".equals(currentGameMode)) {
            maxRounds = Integer.MAX_VALUE;
        } else {
            maxRounds = 10;
        }

        updateScoreDisplay();

        // Roll Button Functionality
        rollButton.setOnClickListener(v -> playTurn());
    }

    private void playTurn() {
        if ("Race".equals(currentGameMode)) {
            playRaceMode();
        } else if ("BestOfThree".equals(currentGameMode)) {
            playBestOfThreeMode();
        } else {
            playClassicMode();
        }
    }

    //  Mode 1: Classic (10 Rounds)
    private void playClassicMode() {
        if (roundsPlayed >= maxRounds) {
            return;
        }

        animateDice(dice1);
        animateDice(dice2);

        int diceRoll1 = random.nextInt(6) + 1;
        int diceRoll2 = random.nextInt(6) + 1;

        dice1.postDelayed(() -> dice1.setImageResource(getDiceImage(diceRoll1)), 500);
        dice2.postDelayed(() -> dice2.setImageResource(getDiceImage(diceRoll2)), 500);

        // Classic Mode scoring rules
        if (diceRoll1 > diceRoll2) {
            player1Score += 1;
        } else if (diceRoll2 > diceRoll1) {
            player2Score += 1;
        }


        roundsPlayed++;
        updateScoreDisplay();

        if (roundsPlayed >= maxRounds) {
            declareFinalWinner();
        }
    }

    //  Mode 2: Best of Three
    private void playBestOfThreeMode() {
        if (roundsPlayed >= maxRounds) {
            return;
        }

        animateDice(dice1);
        animateDice(dice2);

        int diceRoll1 = random.nextInt(6) + 1;
        int diceRoll2 = random.nextInt(6) + 1;

        dice1.postDelayed(() -> dice1.setImageResource(getDiceImage(diceRoll1)), 500);
        dice2.postDelayed(() -> dice2.setImageResource(getDiceImage(diceRoll2)), 500);

        if (diceRoll1 > diceRoll2) {
            player1Score += 1;
        } else if (diceRoll2 > diceRoll1) {
            player2Score += 1;
        }

        roundsPlayed++;
        updateScoreDisplay();

        if (roundsPlayed >= maxRounds) {
            declareFinalWinner();
        }
    }

    //  Mode 3: Race to 100 (Turn-Based)
    private void playRaceMode() {
        if (player1Score >= TARGET_SCORE || player2Score >= TARGET_SCORE) {
            return;
        }

        animateDice(dice1);
        animateDice(dice2);

        int diceRoll1 = random.nextInt(6) + 1;
        int diceRoll2 = random.nextInt(6) + 1;
        int sum = diceRoll1 + diceRoll2;

        dice1.postDelayed(() -> dice1.setImageResource(getDiceImage(diceRoll1)), 500);
        dice2.postDelayed(() -> dice2.setImageResource(getDiceImage(diceRoll2)), 500);

        if (isPlayer1Turn) {
            player1Score += sum;
        } else {
            player2Score += sum;
        }

        updateScoreDisplay();

        if (player1Score >= TARGET_SCORE) {
            declareWinner(player1Name.getText().toString());
            return;
        } else if (player2Score >= TARGET_SCORE) {
            declareWinner(player2Name.getText().toString());
            return;
        }

        isPlayer1Turn = !isPlayer1Turn;
    }

    //  Update Score Display for All Modes
    private void updateScoreDisplay() {
        if ("Race".equals(currentGameMode)) {
            scoreText.setText("Score: " + player1Score + " - " + player2Score);
        } else if ("BestOfThree".equals(currentGameMode)) {
            scoreText.setText("Score: " + player1Score + " - " + player2Score);
        } else {
            scoreText.setText("Score: " + player1Score + " - " + player2Score);
        }
    }

    private void declareFinalWinner() {
        rollButton.setEnabled(false);
        if (player1Score > player2Score) {
            winnerText.setText("🏆 WINNER: " + player1Name.getText().toString());
        } else if (player1Score < player2Score) {
            winnerText.setText("🏆 WINNER: " + player2Name.getText().toString());
        } else {
            winnerText.setText("It's a DRAW! 🤝");
        }
    }

    private void declareWinner(String winner) {
        rollButton.setEnabled(false);
        winnerText.setText("🏆 WINNER: " + winner);
        Toast.makeText(this, "🏆 " + winner + " reached 100 first!", Toast.LENGTH_LONG).show();
    }

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

    private void animateDice(ImageView dice) {
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        dice.startAnimation(rotate);
    }
}
