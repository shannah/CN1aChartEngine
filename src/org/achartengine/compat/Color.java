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
    public static int LTGRAY = ca.weblite.pisces.Color.LightGray.argb;
    public static int BLUE = ca.weblite.pisces.Color.Blue.argb;
    public static int BLACK = ca.weblite.pisces.Color.Black.argb;
    public static int WHITE = ca.weblite.pisces.Color.White.argb;
    public static int CYAN = ca.weblite.pisces.Color.Cyan.argb;
    public static int GREEN = ca.weblite.pisces.Color.Green.argb;
    public static int YELLOW = ca.weblite.pisces.Color.Yellow.argb;
    public static int MAGENTA = ca.weblite.pisces.Color.Magenta.argb;
    public static int GRAY = ca.weblite.pisces.Color.Gray.argb;
    
    

    public static int argb(int a, int r, int g, int b) {
        ca.weblite.pisces.Color c = new ca.weblite.pisces.Color(a,r,g,b);
        return c.argb;
    }

    public static int alpha(int c) {
        ca.weblite.pisces.Color pc = new ca.weblite.pisces.Color(c);
        return pc.alpha;
    }

    public static int red(int c) {
        ca.weblite.pisces.Color pc = new ca.weblite.pisces.Color(c);
        return pc.red;
    }

    public static int green(int c) {
        ca.weblite.pisces.Color pc = new ca.weblite.pisces.Color(c);
        return pc.green;
    }

    public static int blue(int c) {
        ca.weblite.pisces.Color pc = new ca.weblite.pisces.Color(c);
        return pc.blue;
    }

    public static int rgb(int r, int g, int b) {
        ca.weblite.pisces.Color c = new ca.weblite.pisces.Color(r,g,b);
        return c.argb;
    }
    
}
