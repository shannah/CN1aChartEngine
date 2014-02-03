/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;

import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import org.achartengine.GraphicalView;

/**
 *
 * @author shannah
 */
public class Activity extends Context{
    
    protected Form peer;
    
    private String title;
    private Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        
    }
    
    public Intent getIntent() {
        return intent;
    }
    
    public void setIntent(Intent intent){
        this.intent = intent;
    }
    
    public void requestWindowFeature(int f){
        switch ( f ){
            case Window.FEATURE_NO_TITLE:
                title = peer.getTitle();
                //peer.setTitle("");
                
        }
    }
    
    
    public void setTitle(String title){
        peer.setTitle(title);
    }
    
    public void setContentView(View v){
        Layout l = peer.getLayout();
        if ( !(l instanceof BorderLayout)){
            peer.setLayout(new BorderLayout());
        }
        peer.addComponent(BorderLayout.CENTER, v.peer);
        
    }
    
    public Form getPeer(){
        return peer;
    }
}
