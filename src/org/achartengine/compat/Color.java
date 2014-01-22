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
public class Color {
    public static int LTGRAY = pisces.Color.LightGray.argb;
    public static int BLUE = pisces.Color.Blue.argb;
    public static int BLACK = pisces.Color.Black.argb;
    public static int WHITE = pisces.Color.White.argb;
    public static int CYAN = pisces.Color.Cyan.argb;
    public static int GREEN = pisces.Color.Green.argb;
    public static int YELLOW = pisces.Color.Yellow.argb;
    public static int MAGENTA = pisces.Color.Magenta.argb;
    public static int GRAY = pisces.Color.Gray.argb;
    
    

    public static int argb(int a, int r, int g, int b) {
        pisces.Color c = new pisces.Color(a,r,g,b);
        return c.argb;
    }

    public static int alpha(int c) {
        pisces.Color pc = new pisces.Color(c);
        return pc.alpha;
    }

    public static int red(int c) {
        pisces.Color pc = new pisces.Color(c);
        return pc.red;
    }

    public static int green(int c) {
        pisces.Color pc = new pisces.Color(c);
        return pc.green;
    }

    public static int blue(int c) {
        pisces.Color pc = new pisces.Color(c);
        return pc.blue;
    }

    public static int rgb(int r, int g, int b) {
        pisces.Color c = new pisces.Color(r,g,b);
        return c.argb;
    }
    
}
