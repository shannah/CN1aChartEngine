/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;


import com.codename1.io.Log;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import org.achartengine.compat.GradientDrawable.Orientation;
import org.achartengine.compat.Paint.Align;
import pisces.d.Point2D;
import pisces.d.Rectangle2D;
import pisces.m.Matrix;
import pisces.m.Tuple;

/**
 *
 * @author shannah
 */
public class Canvas  {
    
    com.codename1.ui.Graphics g;
    static pisces.Graphics pg  = new pisces.Graphics(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());;
    Matrix transform = new Matrix().setIdentity();
    
    // placeholder for loading temp rectangle data
    // maximizes object reuse -- easier on gc
    private Rectangle2D r1 = new Rectangle2D(0,0,0,0);
    private Rect rc1 = new Rect();
    private Tuple t1 = new Tuple(0,0,0);
    private Tuple t2 = new Tuple(0,0,0);
    
    Canvas(){
        Display d = Display.getInstance();
        pg.setAntialiasing(true);
    }
    
    
    public void getClipBounds(Rect mRect) {
        int [] bounds = g.getClip();
        mRect.set(bounds[0], bounds[1], bounds[0]+bounds[2], bounds[1]+bounds[3]);
        
    }

    private void normalizeTransform(){
        if ( transform != null && !transform.isIdentity()){
            if ( Math.abs(transform.m00 - 1.0) < 0.01 ){
                transform.m00 = 1.0;
            }
            if ( Math.abs(transform.m01 - 0.0) < 0.01 ){
                transform.m01 = 0.0;
            }
            if ( Math.abs(transform.m02 - 0.0) < 0.01 ){
                transform.m02 = 0.0;
            }
            if ( Math.abs(transform.m11 - 1.0) < 0.01 ){
                transform.m11 = 1.0;
            }
            if ( Math.abs(transform.m10 - 0.0) < 0.01 ){
                transform.m10 = 0.0;
            }
            if ( Math.abs(transform.m12 - 0.0) < 0.01 ){
                transform.m12 = 0.0;
            }
            if ( Math.abs(transform.m22 - 1.0) < 0.01 ){
                transform.m22 = 1.0;
            }
            if ( Math.abs(transform.m20 - 0.0) < 0.01 ){
                transform.m20 = 0.0;
            }
            if ( Math.abs(transform.m12 - 0.0) < 0.01 ){
                transform.m21 = 0.0;
            }
        }
    }
    
    private void applyPaint(Paint paint){
        applyPaint(paint, false);
    }
    
    
    private void applyPaint(Paint paint, boolean forText){
        //Log.p("Applyingn paint : "+paint);
        g.setColor(paint.getColor());
        int alpha = Color.alpha(paint.getColor());
        g.setAlpha(alpha);
        if ( forText ){
            Typeface typeFace = paint.getTypeface();
            if ( typeFace != null ){
                if ( typeFace.cn1Font() != null ){
                    Font f = typeFace.cn1Font();
                    if ( f.getSize() != (int)paint.getTextSize()){
                        f = f.derive(paint.getTextSize(), Font.STYLE_PLAIN);
                        typeFace.setCn1Font(f);
                    }
                    g.setFont(f);
                } else {
                    g.setFont(null);
                }
            } else {
                g.setFont(null);
            }
        }
        
        
    }
    
    private void applyPaintToPisces(Paint paint, pisces.Graphics g){
        pisces.Color c = new pisces.Color(paint.getColor());
        g.setColor(c);
        Typeface typeFace = paint.getTypeface();
        if ( typeFace != null ){
            if ( typeFace.piscesFont() != null ){
                g.setFont(typeFace.piscesFont().deriveFont((int)paint.getTextSize()));
            } else {
                g.setFont(null);
            }
        } else {
            g.setFont(null);
        }
        
    }
    
    
    
    public void drawRect(float left, float top, float right, float bottom, Paint paint) {
        //Log.p("Drawing rect: "+left+","+top+","+right+","+bottom);
        normalizeTransform();
        if ( transform == null || transform.isIdentity() ){
            //Log.p("Transform is null ");
            applyPaint(paint);
            Paint.Style style = paint.getStyle();
            if ( Paint.Style.FILL.equals(style)){
                //Log.p("Filling it");
                g.fillRect((int)left, (int)top, (int)right-(int)left, (int)bottom-(int)top);
            } else if ( Paint.Style.STROKE.equals(style)){
                g.drawRect((int)left, (int)top, (int)right-(int)left, (int)bottom-(int)top);
            } else if ( Paint.Style.FILL_AND_STROKE.equals(style)){
                g.fillRect((int)left, (int)top, (int)right-(int)left, (int)bottom-(int)top);
                g.drawRect((int)left, (int)top, (int)right-(int)left, (int)bottom-(int)top);
            }
            
        } else {
            pisces.Path p = new pisces.Path();
            p.moveTo(left, top);
            p.lineTo(right, top);
            p.lineTo(right, bottom);
            p.lineTo(left, bottom);
            p.close();
            p.end();
            drawPath(p, paint);
        }
    }

    public void drawText(String string, float x, float y, Paint paint) {
        //Log.p("Drawing text: "+x+","+y);
        normalizeTransform();
        if ( transform != null && !transform.isIdentity()){
            applyPaintToPisces(paint, pg);
            int offX = 0;
            int offY = 0;
            pisces.Font pf = pg.getFont();
            paint.getPiscesTextBounds(string, 0, string.length(), rc1);
            //Log.p(rc1+"");
            if ( paint.getTextAlign() == Align.CENTER){
                offX = -(rc1.right-rc1.left)/2;
            } else if ( paint.getTextAlign() == Align.RIGHT){
                offX = -(rc1.right-rc1.left);
            }
            int h = rc1.bottom-rc1.top;
            //Log.p("H is "+h);
            pisces.Path p = new pisces.Path();
            
            paint.getTypeface().piscesFont().draw(p, string, 0, pf.getAscent(), 0);
            
            //double scale = transform.getScale();
            transform.translate(x, y-h);
            drawPath(p, paint);
            transform.translate(-x,-y+h);
            p.dispose();
            return;
         }
        applyPaint(paint, true);
        int offX = 0;
        int offY = 0;
        if ( paint.getTextAlign() == Align.CENTER){
            offX = -g.getFont().stringWidth(string)/2;
        }
        int h = g.getFont().getHeight();
        
        g.drawString(string, (int)x+offX, (int)y-h+offY);
    }

    public int getHeight() {
        return g.getClipHeight();
    }

    public int getWidth() {
        return g.getClipWidth();
    }

    public void drawPath(Path path, Paint paint) {
        //Log.p("Drawing path:"+path);
        pisces.Path p = path.path();
        drawPath(p, paint);
        
        
    }

    private void drawPath(pisces.Path p, Paint paint){
        //Log.p("Drawing pisces path:"+p);
        normalizeTransform();
        if ( transform != null && !transform.isIdentity()){
            //Log.p("Drawing path with transform "+transform);
            p.transform(transform);
        }
        Rectangle2D bounds = r1;
        p.getBounds2D(bounds);
        if (bounds.width == 0 ) bounds.width = 1;
        if ( bounds.height == 0 ) bounds.height = 1;
        //Log.p(bounds+"");
        pisces.Graphics i = pg;//new pisces.Graphics((int)bounds.getWidth(), (int)bounds.getHeight());
        i.setColor(pisces.Color.Transparent.White);
        i.clearRect(0, 0, bounds.getWidth(), bounds.getHeight());
        applyPaintToPisces(paint, i);
        Matrix translate = Matrix.getTranslateInstance(-bounds.getX(), -bounds.getY());
        p.transform(translate);
        Paint.Style style = paint.getStyle();
        if ( style.equals(Paint.Style.FILL)){
            i.fill(p);
        } else if ( style.equals(Paint.Style.STROKE)){
            i.draw(p);
        } else if ( style.equals(Paint.Style.FILL_AND_STROKE)){
            i.fill(p);
            i.draw(p);
        }
        
        
        Image im = i.toImage(0,0,(int)bounds.getWidth(), (int)bounds.getHeight());
        //Log.p("Im width "+im.getWidth());
        //Log.p("Im height "+im.getHeight());
        //Log.p("Bounds "+bounds);
        g.drawImage(im, (int)bounds.getX(), (int)bounds.getY());
        im.dispose();
        //i.dispose();
    }
    
    public void drawLine(float x1, float y1, float x2, float y2, Paint paint) {
        //Log.p("Drawing line "+x1+","+y1+" to "+x2+","+y2);
        normalizeTransform();
        if ( transform.isIdentity()){
            applyPaint(paint);
            g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        } else {
            
            pisces.Path p = new pisces.Path();
            p.moveTo(x1, y1);
            p.lineTo(x2, y2);
            p.end();
            drawPath(p, paint);
            
            
        }
    }

    public void rotate(float angle, float x, float y) {
        //Log.p("Rotating by angle "+angle);
        
        angle  = (float)Math.toRadians(angle);
        if ( transform == null ){
            transform = new Matrix().setIdentity();
        }
        Matrix r = Matrix.getRotateInstance(angle, x, y);
        transform.mul(r);
        normalizeTransform();
        
    }

    public void scale(float x, float y) {
        //Log.p("Scaling by "+x+","+y);
        if ( transform == null ){
            transform = new Matrix().setIdentity();
        }
        
        transform.scale(x, y);
        normalizeTransform();
    }

    public void translate(float x, float y) {
        if ( transform == null ){
            transform = new Matrix().setIdentity();
        }
        transform.translate(x, y);
        normalizeTransform();
        //Log.p("Translating "+x+","+y);
    }

    public void drawCircle(float cx, float cy, float r, Paint paint) {
        //Log.p("Drawing circle "+cx+","+cy+", "+r);
        
        int inc = 1;
        if ( r < 50 ){
            inc = 5;
        }
        if ( r < 20 ){
            inc = 20;
        }
        
        int numberOfPoints = 360/inc;
        Point2D[] points = new Point2D[numberOfPoints];
        
        int count = 0;
        for(int theta = 0; theta < 360; theta+=inc)
        {
            float x = (float)(cx + r * Math.cos(Math.toRadians(theta)));
            float y = (float)(cy + r * Math.sin(Math.toRadians(theta)));
            points[count++] = new Point2D(x, y);
        }
        
        pisces.Path circle = new pisces.Path();
        for(int j = 0; j < points.length; j++)
        {
            if(j == 0)
                circle.moveTo(points[j].x, points[j].y);
            else
                circle.lineTo(points[j].x, points[j].y);
        }
        circle.close();
        
        //Log.p("Drawing circle");
        //Log.p("Bounds "+circle.getBounds2D());
        
        drawPath(circle, paint);
 
    }

    public void drawArc(RectF oval, float currentAngle, float sweepAngle, boolean useCenter, Paint paint) {
        
        float cx = 0;
        float cy = 0;
        float r = (oval.right-oval.left)/2f;
        float inc = 1;
       
        int numberOfPoints = (int)sweepAngle+1;
        
        
        
        
        int count = 0;
        if ( useCenter ){
            numberOfPoints++;
            
        }
        Point2D[] points = new Point2D[numberOfPoints];
        if ( useCenter ){
            points[count++] = new Point2D(cx,cy);
        }
        for(float theta = currentAngle; theta < currentAngle+sweepAngle; theta+=inc)
        {
            float x = (int)(cx + r * Math.cos(Math.toRadians(theta)));
            float y = (int)(cy + r * Math.sin(Math.toRadians(theta)));
            points[count++] = new Point2D(x, y);
        }
        
        pisces.Path circle = new pisces.Path();
        for(int j = 0; j < count; j++)
        {
            if(j == 0)
                circle.moveTo(points[j].x, points[j].y);
            else
                circle.lineTo(points[j].x, points[j].y);
        }
        if ( useCenter ){
            circle.close();
        }
        
        cx = (oval.left+oval.right)/2f;
        cy = (oval.top+oval.bottom)/2f;
        
        //Matrix at = Matrix.getTranslateInstance(cx, cy);
        Matrix at = Matrix.getScaleInstance(1.0,(oval.bottom-oval.top)/ (2.0*r) );
        //at.scale(1.0, (oval.bottom-oval.top)/ (2.0*r));
        at.translate(cx, cy);
        
        circle.transform(at);
        
        
        //Log.p("Drawing arc ");
        //Log.p("Bounds: "+circle.getBounds2D());
        drawPath(circle, paint);
    }

    public void drawPoint(Float get, Float get0, Paint paint) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void drawRoundRect(RectF rect, float rx, float ry, Paint mPaint) {
        normalizeTransform();
        if ( transform == null || transform.isIdentity() ){
            applyPaint(mPaint);
            Paint.Style style = mPaint.getStyle();
            if ( Paint.Style.FILL.equals(style)){
                //Log.p("Filling it");
                g.fillRoundRect((int)rect.left, (int)rect.top, (int)(rect.right-rect.left), (int)(rect.bottom-rect.top), (int)rx, (int)ry);
            } else if ( Paint.Style.STROKE.equals(style)){
                g.drawRoundRect((int)rect.left, (int)rect.top, (int)(rect.right-rect.left), (int)(rect.bottom-rect.top), (int)rx, (int)ry);
            } else if ( Paint.Style.FILL_AND_STROKE.equals(style)){
                g.fillRoundRect((int)rect.left, (int)rect.top, (int)(rect.right-rect.left), (int)(rect.bottom-rect.top), (int)rx, (int)ry);
                g.drawRoundRect((int)rect.left, (int)rect.top, (int)(rect.right-rect.left), (int)(rect.bottom-rect.top), (int)rx, (int)ry);
            }
            g.drawRoundRect((int)rect.left, (int)rect.top, (int)(rect.right-rect.left), (int)(rect.bottom-rect.top), (int)rx, (int)ry);
        } else {
            Log.p("Round rects with transform not supported.  Drawing rect with transform");
        }
        
    }

    public void drawBitmap(Bitmap img, float left, float top, Paint paint) {
        normalizeTransform();
        if ( transform == null || transform.isIdentity()){
            g.drawImage(img.img, (int)left, (int)top);
        } else {
            Log.p("drawBitmap not implemented yet with transforms");
        }
    }
    
    void drawGradient(GradientDrawable gradient){
        normalizeTransform();
        if ( transform == null || transform.isIdentity() ){
            Orientation o = gradient.orientation;
            Rect r = gradient.bounds;
            //Log.p("Gradient bounds "+r);
            int[] colors = gradient.colors;
            //Log.p("Gradient orientation: "+o+" with "+colors.length+" colors");
            if ( Orientation.TOP_BOTTOM.equals(o) || Orientation.BOTTOM_TOP.equals(o)){
               
               if ( Orientation.BOTTOM_TOP.equals(o) ){
                   colors = new int[colors.length];
                   //Log.p("Looping through colors");
                   for ( int i=0; i<colors.length; i++){
                       //Log.p("Gradient from "+(new pisces.Color(gradient.colors[i])));
                       colors[i] = gradient.colors[colors.length-i-1];
                   }
               }
               g.fillLinearGradient(colors[0], colors[colors.length-1], r.left, r.top, r.right-r.left, r.bottom-r.top, false);
               
            } else if ( Orientation.LEFT_RIGHT.equals(o)){
               g.fillLinearGradient(gradient.colors[0], gradient.colors[gradient.colors.length-1], r.left, r.top, r.right-r.left, r.bottom-r.top, true);
            } else {
               Log.p("Gradient with type "+o+" not implemented yet.  Just filling solid rect");
               g.setColor(gradient.colors[0]);
               g.fillRect(r.left, r.top, r.right-r.left, r.bottom-r.top);
            }
        } else {
            //Log.p(transform+"");
            Paint p = new Paint();
            p.setStyle(Paint.Style.FILL);
            p.setColor(gradient.colors[0]);
            Rect r = gradient.bounds;
            Log.p("Gradients not implemented yet with transforms.");
            this.drawRect(r.left, r.top, r.right, r.bottom, p);
            
        }
    }
    
}
