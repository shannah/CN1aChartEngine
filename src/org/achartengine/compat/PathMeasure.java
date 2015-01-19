/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;

import com.codename1.ui.geom.PathIterator;

/**
 *
 * @author shannah
 */
public class PathMeasure {

    Path path;
    boolean forceClosed = false;
    
    public PathMeasure(Path p, boolean b) {
        path = p;
        forceClosed = b;
    }
    
    public float getLength(){
        return 10;
    }

    public void getPosTan(int i, float[] coords, float[] tan) {
        
        //throw new RuntimeException("Tangents not implemented yet");
        
    }
    
}
