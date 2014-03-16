/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;

import ca.weblite.codename1.fontbox.FontBoxFontProvider;
import com.codename1.ui.Display;
import com.codename1.ui.Font;

import java.io.InputStream;

/**
 *
 * @author shannah
 */
public class Typeface {
    
    Font cn1Font = null;
    ca.weblite.pisces.Font piscesFont = null;
    
    Font cn1Font(){
        return cn1Font;
    }
    
    void setCn1Font(Font f){
        cn1Font = f;
    }
    
    ca.weblite.pisces.Font piscesFont(){
        return piscesFont;
    }
    
    public static final int BOLD =1;
    public static final int BOLD_ITALIC=2;
    public static final int ITALIC=3;
    public static final int NORMAL=4;
    private static boolean piscesFontLoaded = false;
    public static final Typeface SERIF = Typeface.create("SERIF", NORMAL);
    
    
    
    private int style = NORMAL;
    
    public Typeface(String name, int type){
        if ( SERIF != null ){
            this.cn1Font = SERIF.cn1Font;
            this.piscesFont = SERIF.piscesFont;
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
        if ( !piscesFontLoaded ){
            InputStream is = null;
            try {
                is = Display.getInstance().getResourceAsStream(Typeface.class, "/Roboto-Black.ttf");
                FontBoxFontProvider.getDefaultProvider().loadTTF("Roboto-Black", is);
                piscesFontLoaded = true;
            } catch ( Exception ex){
                throw new RuntimeException(ex.getMessage());
            } finally {
                com.codename1.io.Util.cleanup(is);
            }
        }
        Typeface tf = new Typeface(name, type);
        tf.cn1Font = Font.createTrueTypeFont("Roboto-Black", "Roboto-Black.ttf");//Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_SMALL, Font.STYLE_PLAIN);
        tf.piscesFont = ca.weblite.pisces.Font.getFont("Roboto-Black", 10);
        return tf;
    }
}
