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

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Service;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import java.io.IOException;


public class TelephonyEventService extends Service {
	private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {

	    @Override
	    public void onCallStateChanged(int state, String incomingNumber) {
	        super.onCallStateChanged(state, incomingNumber);

	        switch (state) {
	        case TelephonyManager.CALL_STATE_OFFHOOK:
	        			 Log.d("TelephonyEventService", "off hook");
	                     break;
	        case TelephonyManager.CALL_STATE_RINGING:
	        			 Log.d("TelephonyEventService", "ringing");
	        			 connectHTTP();
	                     break;
	        case TelephonyManager.CALL_STATE_IDLE:
	        			 Log.d("TelephonyEventService", "idle");
	                     break;
	        }
	    }

	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		Toast.makeText(this, "My service created", Toast.LENGTH_LONG);
		Log.d("TelephonyEventService", "onCreate");
		TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		manager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG);
		Log.d("TelephonyEventService", "onDestroy");
		TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		manager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
	}
	
    public void connectHTTP() {
    	// TODO Check if internet is available
    	String server = Util.retrieveServerConf(this);
    	String id = Util.retrieveIdConf(this);
    	String http = "http://" + server + "/notify-call/" + id;
    	Log.d("TelephonyEventService", "Connecting to " + http);
    	
   	 	HttpClient httpclient = new DefaultHttpClient();
   	    HttpGet httpget = new HttpGet(http);
   	    try {
   	    	httpclient.execute(httpget);
   	    } catch (ClientProtocolException e1) {
   	    	// TODO Auto-generated catch block
   	        e1.printStackTrace();
   	    } catch (IOException e1) {
   	    	// TODO Auto-generated catch block
   	        e1.printStackTrace();
   	    }
	}
}
