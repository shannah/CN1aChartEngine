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
public class Rect {
    public int top;
    public int left;
    public int right;
    public int bottom;

    public int width() {
        return right-left;
    }

    public int height() {
        return bottom-top;
    }

    public void set(int left, int top, int right, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }
    
    public String toString(){
        return "["+left+","+top+","+right+","+bottom+"]";
    }
    
}
