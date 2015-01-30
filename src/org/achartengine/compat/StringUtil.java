/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;

import java.util.List;

/**
 *
 * @author shannah
 */
public class StringUtil {
    static char[] stopCharCandidates = "!@#$%^&*()?><,./+-qwertyuiop[zxcvbnm,./\\|}{".toCharArray();
    public static String[] split(String input, String sep){
        if ( sep.length() > 1 ){
            for ( int i=0; i<stopCharCandidates.length; i++){
                if ( input.indexOf(stopCharCandidates[i]) == -1 ){
                    input = com.codename1.util.StringUtil.replaceAll(input, sep, String.valueOf(stopCharCandidates[i]));
                    sep = String.valueOf(stopCharCandidates[i]);
                }
            }
        }
        if ( sep.length() > 1 ){
            throw new RuntimeException("Failed to find appropriate stop character");
        }
        List<String> parts = com.codename1.util.StringUtil.tokenize(input, sep);
        return parts.toArray(new String[parts.size()]);
    }
}
