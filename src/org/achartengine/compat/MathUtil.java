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
public class MathUtil {
    
    
    
    public static double ulp(double value) {
        long bits = Double.doubleToLongBits(value);
        if ((bits & 0x7FF0000000000000L) == 0x7FF0000000000000L) { // if x is not finite
          if ((bits & 0x000FFFFFFFFFFFFFL) != 0x0 ) { // if x is a NaN
            return value;  // I did not force the sign bit here with NaNs.
            } 
          return Double.longBitsToDouble(0x7FF0000000000000L); // Positive Infinity;
          }
        bits &= 0x7FFFFFFFFFFFFFFFL; // make positive
        if (bits == 0x7FEFFFFFFFFFFFFL) { // if x == max_double (notice the _E_)
          return Double.longBitsToDouble(bits) - Double.longBitsToDouble(bits - 1);
        }
        double nextValue = Double.longBitsToDouble(bits + 1);
        double result = nextValue - value;
        return result;
}

    public static double atan2(double dy, double dx) {
        return com.codename1.util.MathUtil.atan2(dy, dx);
    }

    public static double pow(float f, int i) {
        return com.codename1.util.MathUtil.pow(f, i);
    }

    public static double log10(double val) {
        return com.codename1.util.MathUtil.log10(val);
    }
}
