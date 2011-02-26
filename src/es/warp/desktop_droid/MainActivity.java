/*
 *
 *  Desktop Droid
 *
 *  Copyright (C) 2011   Warp Networks, S.L. All rights reserved.
 *
 *  Author: Javier Uruen Val (juruen@warp.es)
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */
package es.warp.desktop_droid;

import es.warp.desktop_droid.TelephonyEventService;
import es.warp.desktop_droid.Util;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class MainActivity extends Activity implements OnClickListener{
	private static final String LOGTAG = "DESKTOP_DROID";
	private static final String DEFAULT_SERVER = "people.warp.es:3000";
	private static final int ID_LENGTH = 10;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.d(LOGTAG, "Starting up");
        
        Button buttonStart = (Button) findViewById(R.id.buttonStart);
        android.widget.Button buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        
        String storedServer = Util.retrieveServerConf(this);
        TextView server = guiServer();
        if (storedServer.length() > 0) {
        	server.setText(storedServer);
        } else {
        	server.setText(DEFAULT_SERVER);
        }
        
        String storedId = Util.retrieveIdConf(this);
        TextView id = guiId();
        if (storedId.length() > 0) {
        	id.setText(storedId);
        } else {
            createRandomId();
        }
    }
   

    public void onClick(View src) {
    	switch (src.getId()) {
    	case R.id.buttonStart:
    		Log.d(LOGTAG, "Clicked on start");
    		TextView id = guiId();
    		if (!(id.getText().length() > 0))
    			createRandomId();
    		TextView server = guiServer();
    		Util.storeIdConf(id.getText().toString(), this);
    		Util.storeServerConf(server.getText().toString(), this);
    		startService(new Intent(this, TelephonyEventService.class));
    		break;
    	case R.id.buttonStop:
    		Log.d(LOGTAG, "Clicked on stop");
    		stopService(new Intent(this, TelephonyEventService.class));
    		break;
    	}
    }
    
    private void createRandomId() {
    	String charString = "";
    	for (int i = 'a'; i <= 'z'; i++) {
    		charString = charString.concat(Character.toString((char)i));
    	}
    	for (int i = 'A'; i <= 'Z'; i++) {
    		charString = charString.concat(Character.toString((char)i));
    	}
    	for (int i = '0'; i <= '9'; i++) {
    		charString = charString.concat(Character.toString((char)i));
    	}
    	String id = "";
    	for (int i = 0; i < ID_LENGTH; i++) {
    		int rnd  = (int)Math.round(Math.random() * (charString.length() -1));
    		id = id.concat(Character.toString(charString.charAt(rnd)));
    		
    	}
    	TextView idText = guiId();
    	idText.setText(id);
    }
    
    private TextView guiServer() {
    	   return(TextView) findViewById(R.id.serverText);	
    }
    
    private TextView guiId() {
 	   return(TextView) findViewById(R.id.idText);	
    }
    
}
