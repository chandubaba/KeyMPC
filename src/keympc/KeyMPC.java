package keympc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.LineUnavailableException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class KeyMPC {

    String b[] = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};
    Action[] actions = new AbstractAction[26];
    public static AudioStream[] a;

    private JButton btnRecord;
    private JLabel lblRecordTime;
    private SoundRecordingUtil audioRecorder;
    private boolean isRecording;
    private Timer recordTimer;
    private int seconds;

    private JPanel keyboardPanel;
    private JPanel recordPanel;
    public static AudioStream audioStream1, audioStream2, audioStream3, audioStream4, audioStream5, audioStream6, audioStream7, audioStream8, audioStream9, audioStream10;

    public KeyMPC() throws Exception {

        JFrame frame = new JFrame();

        // initialize button Record and label RecordTime
        btnRecord = new JButton("Record");
        lblRecordTime = new JLabel("Record Time: 00:00:00");
        audioRecorder = new SoundRecordingUtil();        

        recordPanel = new JPanel(new FlowLayout());
        recordPanel.add(btnRecord);
        recordPanel.add(lblRecordTime);

        keyboardPanel = new JPanel(new GridLayout(3, 9, 5, 3));
        JButton[] buttons = new JButton[26];
        Action[] actions = new AbstractAction[26];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(b[i]);
            buttons[i].setSize(80, 80);
            buttons[i].addKeyListener(new KeyAdapter() {

                @Override
                public void keyPressed(KeyEvent e) {
//                    System.out.println("pressed = " + e.getKeyChar());
                    try {
                        InputStream audioInputStream;

                        if (e.getKeyChar() == 'd') {
                            audioInputStream = new FileInputStream("audio/kick.wav");
                            a[0] = new AudioStream(audioInputStream);
                            AudioPlayer.player.start(a[0]);
                        } else if (e.getKeyChar() == 'e') {
                            audioInputStream = new FileInputStream("audio/snare.wav");
                            a[1] = new AudioStream(audioInputStream);
                            AudioPlayer.player.start(a[1]);
                        } else if (e.getKeyChar() == 'z') {
                            audioInputStream = new FileInputStream("audio/bhee2.wav");
                            a[2] = new AudioStream(audioInputStream);
                            AudioPlayer.player.start(a[2]);
                        } else if (e.getKeyChar() == 'x') {
                            audioInputStream = new FileInputStream("audio/bhee3.wav");
                            a[3] = new AudioStream(audioInputStream);
                            AudioPlayer.player.start(a[3]);
                        } else if (e.getKeyChar() == 'c') {
                            audioInputStream = new FileInputStream("audio/bhee4.wav");
                            a[4] = new AudioStream(audioInputStream);
                            AudioPlayer.player.start(a[4]);
                        } else if (e.getKeyChar() == 'v') {
                            audioInputStream = new FileInputStream("audio/bhee5.wav");
                            a[5] = new AudioStream(audioInputStream);
                            AudioPlayer.player.start(a[5]);
                        } else if (e.getKeyChar() == 'b') {
                            audioInputStream = new FileInputStream("audio/bhee1.wav");
                            a[6] = new AudioStream(audioInputStream);
                            AudioPlayer.player.start(a[6]);
                        }

                        //clip = AudioSystem.getClip();
                        //clip.open(audioInputStream);
                        //clip.start();
                    } catch (IOException ex) {
                        System.out.println("Error with playing sound." + ex.toString());
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyChar() == 'd') {
                        // AudioPlayer.player.stop(a[0]);
                    }
                    if (e.getKeyChar() == 'e') {
                        // AudioPlayer.player.stop(a[1]);
                    }
                    // if (audioStream1 != null) {
                    //  AudioPlayer.player.stop(audioStream1);
                    // }
                }

            });
            keyboardPanel.add(buttons[i]);
        }
        
        frame.setLayout(new BorderLayout());
        frame.add(keyboardPanel, BorderLayout.CENTER);
        frame.add(recordPanel, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void startRecording() {
        Thread recordThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    isRecording = true;
                    btnRecord.setText("Stop");
                    audioRecorder.start();
                } catch (LineUnavailableException lue) {
                    lue.printStackTrace();
                }
            }
        });
        recordThread.start();
        seconds = 0;
        recordTimer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ++seconds;
                lblRecordTime.setText("Record: " + toTimeString(seconds));
            }
        });
    }    
    
    private String toTimeString(int secs) {
        int hours = secs / 3600;
        secs %= 3600;
        int minutes = secs / 60;
        secs %= 60;
        return String.format("%2d:%2d:%2d", hours, minutes, secs);        
    }

    public static void main(String[] args) {
        a = new AudioStream[]{audioStream1, audioStream2, audioStream3, audioStream4, audioStream5, audioStream6, audioStream7, audioStream8, audioStream9, audioStream10};
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    KeyMPC keyMPC;
                    keyMPC = new KeyMPC();
                } catch (Exception e) {
                }
            }
        });
    }

}
