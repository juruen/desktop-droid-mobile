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

import android.content.Context;
import android.content.SharedPreferences;


public class Util {
	public final static String PREFS_NAME = "DESKTOP_DROID";
	
	public static void storeServerConf(String server, Context context) {
		if (!retrieveString("server", context).equals(server))
	      storeString("server", server, context);
	}
	
	public static String retrieveServerConf(Context context) {
		return retrieveString("server", context);
	}
	
	public static void storeIdConf(String server, Context context) {
		if (!retrieveString("id", context).equals(server))
	      storeString("id", server, context);
	}
	
	public static String retrieveIdConf(Context context) {
		return retrieveString("id", context);
	}
	
	private static void storeString(String key, String value, Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
 	    SharedPreferences.Editor editor = settings.edit();
 	    editor.putString(key, value);
 	    editor.commit();
	}
	
	private static String retrieveString(String key, Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		return settings.getString(key, "");
	}
}
