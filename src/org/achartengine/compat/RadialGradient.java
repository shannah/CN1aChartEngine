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
public class RadialGradient extends Shader {

    float cx, cy, r;
    int startColor, stopColor;
    TileMode tileMode;
    
    public RadialGradient(float mCenterX, float mCenterY, float longRadius, int gradientStartColor, int gradientStopColor, TileMode tileMode) {
        cx  = mCenterX;
        cy = mCenterY;
        r = longRadius;
        startColor = gradientStartColor;
        stopColor = gradientStopColor;
        this.tileMode = tileMode;
    }
    
}
