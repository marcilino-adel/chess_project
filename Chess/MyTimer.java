package Chess;//package Chess;
//
//import javax.swing.*;
//import java.util.concurrent.TimeUnit;
//
//public class MyTimer extends JLabel {
//    private Timer timer;
//    public long millisRemaining;
//    private boolean paused = true;
//
//    public MyTimer() {
//        setTime();
//        timer = new Timer(1000, e -> {
//            if (!paused) {
//                millisRemaining -= 1000;
//            }
//            if (millisRemaining >= 0) {
//                updateLabel();
//            } else {
//                gameEngine.endGame(gameEngine.TIMEOUT);
//                timer.stop();
//            }
//        });
//        timer.start();
//    }
//
//    private void setTime() {
//        millisRemaining = TimeUnit.MINUTES.toMillis(3);
//    }
//
//    public void pause() {
//        paused = true;
//    }
//
//    public void unpause() {
//        paused = false;
//    }
//
//    private void updateLabel() {
//        String timeString = String.format("%02d:%02d",
//                TimeUnit.MILLISECONDS.toMinutes(millisRemaining),
//                TimeUnit.MILLISECONDS.toSeconds(millisRemaining) -
//                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisRemaining)));
//        setText(timeString);
//    }
//}
import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class MyTimer extends JLabel {
    private static int count = 0;
    private static String timeSet;
    private Timer timer;
    private long millisRemaining;
    private boolean paused = true;

    public MyTimer() {
        setTime();
        timer = new Timer(1000, e -> {
            if (!paused) {
                millisRemaining -= 1000;
            }
            if (millisRemaining >= 0) {
                updateLabel();
            } else {
                gameEngine.endGame(gameEngine.TIMEOUT);
                timer.stop();
            }
        });
        timer.start();
    }

    private void setTime() {
        if (timeSet != null) {
            int minutes = Integer.parseInt(timeSet);
            millisRemaining = TimeUnit.MINUTES.toMillis(minutes);
            return;
        }
        String input = JOptionPane.showInputDialog(null, "Enter time allowed for each player :", "Set Timer", JOptionPane.QUESTION_MESSAGE);
        count++;
        try {
            int minutes = Integer.parseInt(input);
            timeSet = input;
            millisRemaining = TimeUnit.MINUTES.toMillis(minutes);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Using default time: 3 minutes.");
            timeSet = "3";
            millisRemaining = TimeUnit.MINUTES.toMillis(3);
        }
    }

    public void pause() {
        paused = true;
    }

    public void unpause() {
        paused = false;
    }

    private void updateLabel() {
        String timeString = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millisRemaining),
                TimeUnit.MILLISECONDS.toSeconds(millisRemaining) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisRemaining)));
        setText(timeString);
    }
}

