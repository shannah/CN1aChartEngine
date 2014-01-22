/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.achartengine.compat;

import com.codename1.io.Log;
import com.codename1.ui.Command;
import com.codename1.ui.Form;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalActivity;
import org.achartengine.chart.XYChart;

/**
 *
 * @author shannah
 */
public class Intent {

    private Bundle extras = new Bundle();
    Activity activity;
    Context context;
    
    public Intent(Context context, Class<GraphicalActivity> aClass) {
        this.context = context;
        activity = new GraphicalActivity(){

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                extras = savedInstanceState;
                String title = getExtras().getString(ChartFactory.TITLE);
                if ( title == null || "".equals(title)){
                    title = "Chart";
                }
                this.peer = new Form(title);
                if ( Intent.this.context.getBackCommand() != null ){
                    Log.p("Setting back command");
                    this.peer.setBackCommand(Intent.this.context.getBackCommand());
                    
                }
                super.onCreate(savedInstanceState); //To change body of generated methods, choose Tools | Templates.
                
            }
            
        };
        activity.setIntent(this);
    }

    public void putExtra(String CHART, Object obj) {
        getExtras().put(CHART, obj);
    }

    public Bundle getExtras() {
        return extras;
    }

    
}
