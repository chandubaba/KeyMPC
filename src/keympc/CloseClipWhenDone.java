/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keympc;

import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 *
 * @author chandubaba
 */
class CloseClipWhenDone implements LineListener
{
    @Override public void update(LineEvent event)
    {
        if (event.getType().equals(LineEvent.Type.STOP))
        {
            Line soundClip = event.getLine();
            soundClip.close();
             
            //Just to prove that it is called...
            System.out.println("Done playing " + soundClip.toString());
        }
    }

}