/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;

import com.codename1.ui.Display;
import com.codename1.ui.Font;

import java.io.InputStream;

/**
 *
 * @author shannah
 */
public class Typeface {
    
    Font cn1Font = null;
    
    Font cn1Font(){
        return cn1Font;
    }
    
    void setCn1Font(Font f){
        cn1Font = f;
    }
    
    
    
    public static final int BOLD =1;
    public static final int BOLD_ITALIC=2;
    public static final int ITALIC=3;
    public static final int NORMAL=4;
    public static final Typeface SERIF = Typeface.create("SERIF", NORMAL);
    
    
    
    private int style = NORMAL;
    
    public Typeface(String name, int type){
        if ( SERIF != null ){
            this.cn1Font = SERIF.cn1Font;
            
        }
    }
    
    public static Typeface create(Typeface face, int type){
        return face;
    }

    public int getStyle() {
        return style;
    }
    
    public static Typeface create(String name){
        return SERIF;
        
    }
    
    public static Typeface create(String name, int type){
        
        Typeface tf = new Typeface(name, type);
        tf.cn1Font = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_SMALL, Font.STYLE_PLAIN);//, type)Font.createTrueTypeFont("Roboto-Black", "Roboto-Black.ttf");//Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_SMALL, Font.STYLE_PLAIN);
        return tf;
    }
}
