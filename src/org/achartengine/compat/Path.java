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
public class Path {

    private ca.weblite.pisces.Path path;
    ca.weblite.pisces.Path path(){
        return path;
        
    }
    
    public Path(){
        path = new ca.weblite.pisces.Path();
        
    }
    
    public void moveTo(float x, float y) {
        path.moveTo(x, y);
    }

    public void lineTo(float x, float y) {
        path.lineTo(x, y);
    }

    public void cubicTo(float x, float y, float x0, float y0, float x1, float y1) {
        path.cubicTo(x, y, x0, y0, x1, y1);
    }
    
}
