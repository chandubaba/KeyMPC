package keympc;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class KeyMPC {

    JFrame mainFrame;

    String b[] = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};
    Action[] actions = new AbstractAction[26];
    public static AudioStream[] a;

    private JButton btnRecord;
    private JLabel lblRecordTime;
    private SoundRecordingUtil audioRecorder;
    private boolean isRecording = false;
    private Timer recordTimer;
    private int seconds;

    private JPanel keyboardPanel;
    private JPanel recordPanel;
    public static AudioStream audioStream1, audioStream2, audioStream3, audioStream4, audioStream5, audioStream6, audioStream7, audioStream8, audioStream9, audioStream10;
    JButton[] buttons;
    public static Clip[] k;

    public KeyMPC() throws Exception {

        mainFrame = new JFrame();

        //Clips.initclips(k);
        // initialize button Record and label RecordTime
        btnRecord = new JButton("Record");
        lblRecordTime = new JLabel("Record Time: 00:00:00");

        audioRecorder = new SoundRecordingUtil();
        btnRecord.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRecording) {
                    startRecording();
                } else {
                    stopRecording();
                }
            }
        });

        recordPanel = new JPanel(new FlowLayout());
        recordPanel.add(btnRecord);
        recordPanel.add(lblRecordTime);

        keyboardPanel = new JPanel(new GridLayout(3, 9, 5, 3));
        buttons = new JButton[26];
        Action[] actions = new AbstractAction[26];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(b[i]);
            buttons[i].setSize(80, 80);
            buttons[i].addKeyListener(new KeyAdapter() {

                @Override
                public void keyPressed(KeyEvent e) {

                    try {
                        InputStream audioInputStream;

                        if (e.getKeyChar() == 'd') {
                            Clip kk = k[0]; //copying clip to temp Clip kk and later will close it
                           // kk.addLineListener(new CloseClipWhenDone()); this is need for kk to close but it closes k[0] also
                            kk.start();

                            System.out.println(kk.isOpen()?"ya":"no");//check their state kk should close and k[0] should be open so that more copy can be made later
                            System.out.println(k[0].isOpen()?"ya":"no"); //check their state.. both are open or close most of the time
                            /*audioInputStream = new FileInputStream("audio/kick.wav");
                             a[0] = new AudioStream(audioInputStream);
                             AudioPlayer.player.start(a[0]);*/
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
                        } else if (e.getKeyChar() == 'p') {
                            audioInputStream = new FileInputStream("audio/RememberTheName.wav");
                            a[7] = new AudioStream(audioInputStream);
                            AudioPlayer.player.start(a[7]);
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

        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(keyboardPanel, BorderLayout.CENTER);
        mainFrame.add(recordPanel, BorderLayout.SOUTH);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
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
                lblRecordTime.setText("Record Time: " + toTimeString(seconds));
            }
        });
        recordTimer.start();
    }

    private String toTimeString(int secs) {
        int hours = secs / 3600;
        secs %= 3600;
        int minutes = secs / 60;
        secs %= 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    private void stopRecording() {
        isRecording = false;
        try {
            recordTimer.stop();
            btnRecord.setText("Record");

            mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            audioRecorder.stop();
            mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            // saveFile();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /*private void saveFile() {
     JFileChooser chooser = new JFileChooser();
     FileNameExtensionFilter filter = new FileNameExtensionFilter("Sound file (*.WAV)", "wav");
     chooser.setFileFilter(filter);
     int returnVal = chooser.showSaveDialog(mainFrame);
     if (returnVal == JFileChooser.APPROVE_OPTION) {
     String saveFilePath = chooser.getSelectedFile().getAbsolutePath();
     if (!saveFilePath.toLowerCase().endsWith(".wav")) {
     saveFilePath += ".wav";
     }
     File wavFile = new File(saveFilePath);
     try {
     audioRecorder.save(wavFile);
     JOptionPane.showMessageDialog(mainFrame,
     "Save recorded sound to:\n" + saveFilePath);
     } catch (IOException ioe) {
     ioe.printStackTrace();
     }
     }
     }*/
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        // cc=new Clips();
        k = Clips.initclips();

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
