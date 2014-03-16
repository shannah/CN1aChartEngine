/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;


import com.codename1.ui.Font;
import com.codename1.ui.Graphics;

/**
 *
 * @author shannah
 */
public class Paint {

    
    static Graphics g;
    private boolean antiAlias;
    private Typeface typeface = Typeface.SERIF;
    private Cap strokeCap = Cap.BUTT;
    private Join strokeJoin = Join.BEVEL;
    private float strokeMiter = 1f;
    private PathEffect pathEffect;
    private Style style = Style.STROKE;
    private float strokeWidth = 1f;
    private Shader shader;
    private int color;
    private Align align;
    private float textSize = 12f;
    
    public void getTextWidths(String text, float[] widths) {
        Font f = getTypeface().cn1Font();
        if ( f != null ){
            char[] chars = text.toCharArray();
            for ( int i=0; i<chars.length && i<widths.length; i++){
                widths[i] = f.charWidth(chars[i]);
            }
        } else {
            ca.weblite.pisces.Font f2 = getTypeface().piscesFont();
            if ( f2 != null ){
                char[] chars = text.toCharArray();
                for ( int i=0; i<chars.length && i<widths.length; i++){
                    widths[i] = f2.getGlyph(chars[i]).getWidth();
                }
            }
        }
        
    }

    public int breakText(String text, boolean measureForwards, float maxWidth, float[] measuredWidth) {
        char[] chars = text.toCharArray();
        Font f = getTypeface().cn1Font();
        float tmp = 0;
        if ( f != null ){
            int start = measureForwards ? 0 : chars.length-1;
            int inc = measureForwards ? 1 : -1;
            
            float currWidth = 0f;
            for ( int i=start; (measureForwards && i<chars.length) || (!measureForwards && i>=0) ; i+=inc){
                tmp = f.charWidth(chars[i]);
                if ( currWidth + tmp > maxWidth ){
                    return i;
                }
                if ( measuredWidth != null && i < measuredWidth.length ){
                    measuredWidth[i] = tmp;
                }
                currWidth += tmp;
            }
            
        } else {
            ca.weblite.pisces.Font f2 = getTypeface().piscesFont();
            if ( f2 != null ){
                int start = measureForwards ? 0 : chars.length-1;
                int inc = measureForwards ? 1 : -1;

                float currWidth = 0f;
                for ( int i=start; (measureForwards && i<chars.length) || (!measureForwards && i>=0) ; i+=inc){
                    tmp = f2.getGlyph(chars[i]).getWidth();
                    if ( currWidth + tmp > maxWidth ){
                        return i;
                    }
                    if ( measuredWidth != null && i < measuredWidth.length ){
                        measuredWidth[i] = tmp;
                    }
                    currWidth += tmp;
                }
            }
        }
        return chars.length;
    }

    public void getTextBounds(String string, int start, int count, Rect rect) {
        Font f = getTypeface().cn1Font();
        if ( f != null ){
            getCN1TextBounds(string, start, count, rect);
        } else {
            getPiscesTextBounds(string, start, count, rect);
            
        }
    }
    
    void getCN1TextBounds(String string, int start, int count, Rect rect){
        Font f = getTypeface().cn1Font();
        if ( f != null ){
            int w = f.substringWidth(string, start, count);
            int h = f.getHeight();
            rect.set(0, 0, w, h);
        }
    }
    
    void getPiscesTextBounds(String string, int start, int count, Rect rect){
        //Log.p("Text size "+this.getTextSize());
        ca.weblite.pisces.Font f2 = getTypeface().piscesFont().deriveFont(this.getTextSize());
        if ( f2 != null ){
            char[] chars = string.toCharArray();
            rect.set(0, 0, (int)measurePiscesText(chars, start, count), (int)measurePiscesTextHeight(chars, start, count));
        } else {
            //Log.p("Font is null");
        }
    }

    
    float measureTextHeight(char[] chars, int start, int count){
        Font f = getTypeface().cn1Font();
        float h = 0f;
        if ( f != null ){
            for ( int i=start; i<chars.length && i<start+count; i++){
                float nh = f.getHeight();
                h = nh > h ? nh : h;
            }
        } else {
            return measurePiscesTextHeight(chars, start, count);
        }
        return h;
    }
    
    float measurePiscesTextHeight(char[] chars, int start, int count){
        float h = 0f;
        ca.weblite.pisces.Font f2 = getTypeface().piscesFont().deriveFont(textSize);
        if ( f2 != null ){
            for ( int i=start; i<chars.length && i<start+count; i++){
                float nh = f2.getGlyph(chars[i]).getHeight();
                h = nh > h ? nh : h;
            }
            //Log.p("H is now "+h);
        }
        return h;
    }
    
    public float measureText(String newText) {
        return measureText(newText.toCharArray(), 0, newText.length());
    }
    
    public float measureText(char[] chars, int start, int count){
        float out = 0f;
        Font f = getTypeface().cn1Font();
        if ( f != null ){
            for ( int i=start; i< chars.length && i < start+count; i++){
                out += f.charWidth(chars[i]);
            }
        } else {
            return measurePiscesText(chars, start, count);
        }
        return out;
    }
    
     float measurePiscesText(char[] chars, int start, int count){
        ca.weblite.pisces.Font f2 = getTypeface().piscesFont().deriveFont(textSize);
        float out = 0f;
        if ( f2 != null ){
            
            for ( int i=start; i< chars.length && i < start+count; i++){
                out += f2.getGlyph(chars[i]).getWidth();
            }
        }
        return out;
     }

    public void setAntiAlias(boolean antialiasing) {
        this.antiAlias = antialiasing;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface textTypeface) {
        typeface = textTypeface;
    }

    

    public Cap getStrokeCap() {
        return strokeCap;
    }

    public Join getStrokeJoin() {
        return strokeJoin;
    }

    public float getStrokeMiter() {
        return strokeMiter;
    }

    public PathEffect getPathEffect() {
        return pathEffect;
    }

    public Style getStyle() {
        return style;
    }

    public void setStrokeCap(Cap cap) {
        strokeCap = cap;
    }

    public void setStrokeJoin(Join join) {
        strokeJoin = join;
    }

    public void setStrokeMiter(float miter) {
        strokeMiter = miter;
    }

    public void setPathEffect(PathEffect pathEffect) {
        this.pathEffect = pathEffect;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float i) {
        strokeWidth = i;
    }

    public void setShader(Shader grad) {
        shader = grad;
    }
    
    public static enum Style {
        FILL,
        FILL_AND_STROKE,
        STROKE
    }
    
    public static enum Align {
        CENTER,
        LEFT,
        RIGHT
    }
    
    public static enum Cap {
        BUTT,
        ROUND,
        SQUARE
    }
    
    public static enum Join {
        BEVEL,
        MITER,
        ROUND
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
    
    public void setStyle(Style style){
        this.style = style;
    }
    
    public void setTextAlign(Align align){
        this.align = align;
    }
    
    public Align getTextAlign(){
        return this.align;
    }
    
    public void setTextSize(float size){
        textSize = size;
        if ( this.typeface != null ){
            this.typeface.cn1Font  = Typeface.SERIF.cn1Font.derive(size, Font.STYLE_PLAIN);
            this.typeface.piscesFont = Typeface.SERIF.piscesFont.deriveFont(size);
        }
    }
    
    public float getTextSize(){
        return textSize;
    }
    
    
    public String toString(){
        return "Paint[shader:"+shader+", color:"+color+", align:"+align+", textSize:"+textSize+", style:"+style+", strokeWidth:"+strokeWidth+", pathEffect:"+pathEffect+", strokeMiter:"+strokeMiter+", strokeJoin:"+strokeJoin+" strokeCap:"+strokeCap+"]";
    }
  
    
    
}
