/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;

import com.codename1.io.Log;
import com.codename1.ui.Image;
import java.io.IOException;
import java.io.InputStream;


/**
 *
 * @author shannah
 */
public class BitmapFactory {

    public static Bitmap decodeStream(InputStream resourceAsStream) {
        Bitmap bm = new Bitmap();
        try {
            bm.img = Image.createImage(resourceAsStream);
        } catch (IOException ex) {
            Log.e(ex);
        }
        return bm;
    }
    
}
