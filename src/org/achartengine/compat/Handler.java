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
public class Handler {

    public void post(Runnable runnable) {
        runnable.run();
    }
    
}
