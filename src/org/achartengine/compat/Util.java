/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;

import com.codename1.io.Log;
import com.codename1.ui.Display;
import java.io.InputStream;

/**
 *
 * @author shannah
 */
public class Util {
    
    static String prefix = "/org_achartengine_";

    public static InputStream getResourceAsStream(String path) {
        if ( path.indexOf("/") != -1 ){
            path = path.substring(path.lastIndexOf("/")+1);
        }
        //Log.p("Loading "+prefix+path);
        return Display.getInstance().getResourceAsStream(Util.class, prefix+path);
    }
    
}
