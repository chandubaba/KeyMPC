/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keympc;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author chandubaba
 */
class Clips {

    public static Clip[] initclips() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Clip[]ret=new Clip[10];
        File soundFile = new File("audio/kick.wav");
        AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);

        // load the sound into memory (a Clip)
        DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
        Clip cc = (Clip) AudioSystem.getLine(info);
        cc.open(sound);
        ret[0]=cc;
        
        return ret;

    }
}
