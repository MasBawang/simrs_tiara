/*
 */
package fungsi;

/**
 *
 * @author rstiara
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class TimeUtil {
    private static Timer exitNewDateTimer;
    private static Timer exitTimer;
    private static final int STANDBY_TIME_SECONDS = 180; // 3 menit = 180 detik
    private static final int CHECK_INTERVAL_MS = 1000;   // cek setiap 1 detik
    private static boolean isTimerRunning = false;
    private static LocalDate initialDate = LocalDate.now(); // simpan tanggal saat aplikasi mulai

    public static void waktugantihari(JFrame frame) {
        exitNewDateTimer = new Timer(CHECK_INTERVAL_MS, (ActionEvent evt) -> {
            checkTimeAndExit(frame);
        });
        exitNewDateTimer.start();
    }

    public static void waktustandbyStart(JFrame frame) {
        if (!isTimerRunning) {
            exitTimer = new Timer(STANDBY_TIME_SECONDS * 1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    exitTimer.stop();
                    isTimerRunning = false;
                    JOptionPane.showMessageDialog(frame, "Aplikasi keluar karena standby terlalu lama, silakan jalankan kembali.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            });
            exitTimer.setRepeats(false);
            exitTimer.start();
            isTimerRunning = true;
        } else {
            exitTimer.restart();
        }
    }

    public static void waktustandbyStop() {
        if (exitTimer != null) {
            exitTimer.stop();
            isTimerRunning = false;
        }
    }

    private static void checkTimeAndExit(JFrame frame) {
        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();
        LocalTime targetTime = LocalTime.of(23, 59, 59);

        if (currentTime.isAfter(targetTime) || !currentDate.equals(initialDate)) {
            exitNewDateTimer.stop();
            JOptionPane.showMessageDialog(frame, "Waktu telah berganti hari. Aplikasi akan ditutup, silakan jalankan kembali.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }
}