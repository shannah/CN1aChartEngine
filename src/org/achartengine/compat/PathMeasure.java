/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;

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
        
        return path.path().length();
    }

    public void getPosTan(int i, float[] coords, float[] tan) {
        if ( tan != null ){
            throw new RuntimeException("Tangents not implemented yet");
        }
        path.path().getPoint(i, coords);
    }
    
}
