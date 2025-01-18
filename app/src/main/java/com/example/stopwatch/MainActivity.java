package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private boolean isRunning = false;
    private long timeInMillis = 0;
    private Handler handler = new Handler();
    private TextView tvStopwatch;
    private Button btnStart, btnStop, btnReset;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timeInMillis++;
            tvStopwatch.setText(formatTime(timeInMillis));
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        tvStopwatch = findViewById(R.id.tvStopwatch);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnReset = findViewById(R.id.btnReset);

        // Start or Pause button
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    isRunning = true;
                    handler.postDelayed(runnable, 1000);
                    btnStart.setText("Pause");
                } else {
                    isRunning = false;
                    handler.removeCallbacks(runnable);
                    btnStart.setText("Resume");
                }
            }
        });

        // Stop button
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                handler.removeCallbacks(runnable);
                btnStart.setText("Resume");
            }
        });

        // Reset button
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                timeInMillis = 0;
                handler.removeCallbacks(runnable);
                tvStopwatch.setText(formatTime(timeInMillis));
                btnStart.setText("Start");
            }
        });
    }

    // Format the time in seconds and minutes
    private String formatTime(long timeInMillis) {
        long hours = timeInMillis / 3600;
        long minutes = (timeInMillis % 3600) / 60;
        long seconds = timeInMillis % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
