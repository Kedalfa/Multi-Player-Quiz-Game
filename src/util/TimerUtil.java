package util;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class TimerUtil {
    private Timer timer;
    private int timeLeft;
    private JLabel label;
    private Runnable onTimeout;

    public TimerUtil(int seconds, JLabel label, Runnable onTimeout) {
        this.timeLeft = seconds;
        this.label = label;
        this.onTimeout = onTimeout;
    }

    public void start() {
        timer = new Timer();
        label.setText("Time: " + timeLeft);
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    timeLeft--;
                    label.setText("Time: " + timeLeft);
                    if (timeLeft <= 0) {
                        stop();
                        onTimeout.run();
                    }
                });
            }
        }, 1000, 1000);
    }

    public void stop() {
        if (timer != null) timer.cancel();
    }
} 