/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;

import com.codename1.io.Log;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.RGBImage;
import com.codename1.ui.geom.Rectangle;

/**
 *
 * @author shannah
 */

public class View {
    public static int DRAWING_CACHE_QUALITY_HIGH = 1;

    MotionEvent event = new MotionEvent();
    Canvas canvas = new Canvas();

    private boolean drawingCacheEnabled = false;
    private int drawingCacheBackgroundColor = 0xffffff;
    private int drawingCacheQuality = 1;
    private Bitmap drawingCache;
    Peer peer;

    private boolean touchEventsEnabled = false;
    
    
    

    public View(Context context){
        peer = new Peer();
        peer.getStyle().setBgColor(0x000000);
        peer.getUnselectedStyle().setBgColor(0x000000);
        peer.getSelectedStyle().setBgColor(0x000000);
        peer.getStyle().setBgTransparency(255);
    }
    
    public void setTouchEventsEnabled(boolean e){
        touchEventsEnabled = e;
    }
    
    public boolean isTouchEventsEnabled(){
        return touchEventsEnabled;
    }
    
    public Component getPeer(){
        return peer;
    }
    
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void invalidate(int left, int top, int right, int bottom) {
        peer.updateBuffer = true;
        peer.setDirtyRegion(new com.codename1.ui.geom.Rectangle(left, top, right-left, bottom-top));
    }

    public void setDrawingCacheEnabled(boolean b) {
        drawingCacheEnabled = b;
    }

    public boolean isDrawingCacheEnabled() {
        return drawingCacheEnabled;
    }

    public void setDrawingCacheBackgroundColor(int backgroundColor) {
        drawingCacheBackgroundColor = backgroundColor;
    }

    public void invalidate() {
        peer.updateBuffer = true;
        peer.setDirtyRegion(new Rectangle(peer.getX(), peer.getY(), peer.getWidth(), peer.getHeight()));
    }

    public void setDrawingCacheQuality(int q) {
        drawingCacheQuality = q;
    }

    public Bitmap getDrawingCache(boolean b) {
        return drawingCache;
    }
    
    protected void onDraw(Canvas canvas) {

    }

    public int getMeasuredWidth(){
        return peer.getWidth();
    }

    public int getMeasuredHeight(){
        return peer.getHeight();
    }
    
    
    public class Peer extends Component implements Runnable {
        Image buffer;
        boolean renderingBuffer = false;
        boolean updateBuffer = true;
        int renderingWidth = 0;
        int renderingHeight = 0;
        int bgColor = 0;

        @Override
        public void paint(Graphics g) {
            boolean rendered = false;
            if ( (buffer == null || updateBuffer) && !renderingBuffer ){
                renderingBuffer = true;
                renderingWidth = getWidth();
                renderingHeight = getHeight();
                bgColor = this.getStyle().getBgColor();
                if ( "ios".equals(Display.getInstance().getPlatformName())){
                    this.run();
                } else {
                    Display.getInstance().scheduleBackgroundTask(this);
                }
                //this.run();
                
                
                
            } else if ( buffer != null ){
                g.drawImage(buffer, getX(), getY());
                rendered = true;
            }
            
            if ( !rendered ){
                g.setColor(0xffffff);
                Font f = Font.getDefaultFont();
                g.setFont(Font.getDefaultFont());
                g.drawString("Building chart...", getX()+10, getY()+f.getHeight()+10);
                rendered = true;
            }
            
        }



       

        @Override
        public void pointerPressed(int x, int y) {
            super.pointerPressed(x, y); //To change body of generated methods, choose Tools | Templates.
            if ( touchEventsEnabled ){
                event.x = x;
                event.y = y;
                event.action=MotionEvent.ACTION_DOWN;
                event.count=1;
                onTouchEvent(event);
            }
        }

        @Override
        public void pointerReleased(int x, int y) {
            super.pointerReleased(x, y); //To change body of generated methods, choose Tools | Templates.
            if ( touchEventsEnabled ){
                event.x = x;
                event.y = y;
                event.action=MotionEvent.ACTION_UP;
                event.count=1;
                onTouchEvent(event);
                event.action=MotionEvent.ACTION_POINTER_UP;
                onTouchEvent(event);
            }
        }

        @Override
        public void pointerDragged(int x, int y) {
            super.pointerDragged(x, y); //To change body of generated methods, choose Tools | Templates.
            if ( touchEventsEnabled ){
                event.x = x;
                event.y = y;
                event.action=MotionEvent.ACTION_MOVE;
                event.count=1;
                onTouchEvent(event);
            }
        }

        
        
        

        public void run() {
            Image buf = Image.createImage(renderingWidth, renderingHeight, bgColor);
            
            Graphics g = buf.getGraphics();
            g.setClip(0, 0, renderingWidth, renderingHeight);
            Paint.g = g;
            //super.paint(g); //To change body of generated methods, choose Tools | Templates.
            canvas.g = g;
            long start = System.currentTimeMillis();
            onDraw(canvas);
            long end = System.currentTimeMillis();
            Log.p("Took "+(end-start)+"ms to render chart");
            if ( buffer != null ){
                buffer.dispose();
            }
            buffer = new RGBImage(buf.getRGB(), buf.getWidth(), buf.getHeight());
            buf.dispose();
            renderingBuffer = false;
            updateBuffer = false;
            Display.getInstance().callSerially(new Runnable(){

                public void run() {
                    repaint();
                }
                
            });
            
        }





    }
}