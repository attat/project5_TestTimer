package com.murach.ch10_ex5;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    Timer timer = new Timer(true);
    private TextView messageTextView; 
    private Button startButton;
    private Button stopButton;

    FileIO fileIO;
    int numOfDownloads = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileIO = new FileIO(getApplicationContext());

        messageTextView = (TextView) findViewById(R.id.messageTextView);
        startTimer();
        //references to start and stop buttons
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
    }
    
    private void startTimer() {
        final long startMillis = System.currentTimeMillis();
        timer = new Timer(true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                long elapsedMillis = System.currentTimeMillis() - startMillis;
                fileIO.downloadFile();
                numOfDownloads++;
                updateView(elapsedMillis);
            }
        };
        timer.schedule(task, 0, 10000);
    }

    private void updateView(final long elapsedMillis) {
        // UI changes need to be run on the UI thread
        messageTextView.post(new Runnable() {

            int elapsedSeconds = (int) elapsedMillis/1000;

            @Override
            public void run() {
                messageTextView.setText(String.format("File downloaded %d time(s)", numOfDownloads));
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        //discards timer
        timer.cancel();
    }

    public void callOnStart(View v)
    {
        startTimer();
    }

    public void callOnPause(View v)
    {
        onPause();
    }

}