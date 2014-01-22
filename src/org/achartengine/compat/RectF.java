/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;

import com.codename1.io.Log;

/**
 *
 * @author shannah
 */
public class RectF {
    public float bottom;
    public float left;
    public float top;
    public float right;

    public RectF(float left, float top , float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public RectF() {
        
    }

    public boolean intersects(float l, float t, float r, float b) {
        
        RectF r2 = new RectF(l,t,r,b);
        //Log.p("Checking for intersection:");
        //Log.p(this+" vs "+r2);
        return r2.contains(left,top) || r2.contains(right, bottom) || r2.contains(right,top) || r2.contains(left,bottom)
                || this.contains(l,t) || this.contains(r,b) || this.contains(r,t) || this.contains(l,b);
                
    }

    public boolean contains(float x, float y) {
        return x > left && x< right && y > top && y < bottom;
    }

    public void offset(float translateX, float translateY) {
        left += translateX;
        right += translateX;
        top += translateY;
        bottom += translateY;
    }

    public void set(int l, float t, float r , float b) {
        this.left = l;
        this.top = t;
        this.right = r;
        this.bottom = b;
    }

    public float width() {
        return this.right - this.left;
    }
    
    public String toString(){
        return "["+left+","+top+","+right+","+bottom+"]";
    }
    
}
