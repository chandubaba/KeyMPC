/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keympc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author canhn_000
 */
public class SoundRecordingUtil {

    // Maximal number of bytes per reading
    private static final int BUFFER_SIZE = 4096;

    // All recorded bytes
    private ByteArrayOutputStream recordedBytes;

    // Object connected to audio input hardware
    private TargetDataLine audioLine;

    // Output audio format
    private AudioFormat audioFormat;
    private AudioInputStream ais;
    // Check if recorder is running
    private boolean isRunning;

    // Default constructor
    public SoundRecordingUtil() {
    }

    AudioFormat getAudioFormat() {
        float rate = 44100;
        int sizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(rate, sizeInBits, channels, signed, bigEndian);
    }

    public void start() throws LineUnavailableException, IOException {
        audioFormat = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);

        // check if the system supports specified audio format
        if (!AudioSystem.isLineSupported(info)) {
            throw new LineUnavailableException(
                    "System does not support specified format!");
        }

        audioLine = AudioSystem.getTargetDataLine(audioFormat);
        audioLine.open(audioFormat);
        audioLine.start();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = 0;
        ais = new AudioInputStream(audioLine);
        File ff=new File("audio/file.wav");
        AudioSystem.write(ais,AudioFileFormat.Type.WAVE,ff);
        recordedBytes = new ByteArrayOutputStream();
        isRunning = true;
        while (isRunning) {
            bytesRead = audioLine.read(buffer, 0, buffer.length);
            recordedBytes.write(buffer, 0, bytesRead);

        }
    }

    public void stop() throws IOException {
        isRunning = false;
        if (audioLine != null) {
            audioLine.drain();
            audioLine.close();
            ais.close();
            recordedBytes.close();
        }
    }

    public void save(File wavFile) throws IOException {
        byte[] audioData = recordedBytes.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
        try (AudioInputStream audioInputStream = new AudioInputStream(bais,
                audioFormat,
                audioData.length / audioFormat.getFrameSize())) {
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, wavFile);
        }
        recordedBytes.close();
    }
}
