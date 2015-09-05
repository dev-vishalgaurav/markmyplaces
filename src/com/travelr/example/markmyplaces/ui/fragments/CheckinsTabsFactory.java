/**
 * 
 */
package com.travelr.example.markmyplaces.ui.fragments;

import android.support.v4.app.Fragment;

import com.travelr.example.markmyplaces.ui.adapters.CheckinsFragmentAdapter;

/**
 * @author vishal
 *
 */
public class CheckinsTabsFactory {
    public static final String TAG_POSITION = "position";

	public static  Fragment getTab(int position){
		Fragment userFragment = null ;
		switch (position) {
			case CheckinsFragmentAdapter.TAB_1:
				userFragment =  CheckinsListFragment.getInstance(position);
				break ; 
			case CheckinsFragmentAdapter.TAB_2:
				userFragment =  GeoFenceListFragment.getInstance(position);
		}
		return userFragment ;
		
	}
	
	public static String getTabTitle(int position){
		String tabTitle = null ;
		switch (position) {
			case CheckinsFragmentAdapter.TAB_1:
				tabTitle =  "Checkins";
				break ; 
			case CheckinsFragmentAdapter.TAB_2:
				tabTitle =  "Geofences";
				break ; 
			default:
				tabTitle =  "No Title";

				break; 
			}
			
		return tabTitle ;
		
	}

}
