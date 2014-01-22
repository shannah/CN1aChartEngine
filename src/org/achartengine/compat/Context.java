/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;

import com.codename1.ui.Command;

/**
 *
 * @author shannah
 */
public class Context {
    
    Command backCommand;
    
    public void setBackCommand(Command back){
        backCommand = back;
    }
    
    public Command getBackCommand(){
        return backCommand;
    }
    
    public void startActivity(Intent intent){
        intent.activity.onCreate(intent.getExtras());
        intent.activity.peer.show();
    }
}
