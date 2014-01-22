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
public class MotionEvent {
    
    int action, x, y, count;
    
    public static int ACTION_DOWN;
    public static int ACTION_MOVE;
    public static int ACTION_UP;
    public static int ACTION_POINTER_UP;

    public int getAction() {
        return action;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getX(int i) {
        x = i;
        return x;
    }

    public float getY(int i) {
       y = i;
       return y;
    }

    public int getPointerCount() {
        return count;
    }
    
}
