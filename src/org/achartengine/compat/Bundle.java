/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;

import java.util.HashMap;

/**
 *
 * @author shannah
 */
public class Bundle {
    
    public HashMap map = new HashMap();
    public Object getSerializable(String str){
        return map.get(str);
    }
    
    public String getString(String str){
        return (String)map.get(str);
    }
    
    public void put(String str, Object o){
        map.put(str, o);
    }
    
    
}
