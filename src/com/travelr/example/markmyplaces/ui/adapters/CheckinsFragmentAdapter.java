/**
 * 
 */
package com.travelr.example.markmyplaces.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.travelr.example.markmyplaces.ui.fragments.CheckinsTabsFactory;

/**
 * @author vishal
 *
 */
public class CheckinsFragmentAdapter extends FragmentPagerAdapter {

	public static final int TOTAL_TABS = 2 ; 
	public static final int TAB_1 = 0 ; 
	public static final int TAB_2 = 1; 


	
	public CheckinsFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		
		return CheckinsTabsFactory.getTab(position);
	}

	
	@Override
	public int getCount() {
		return TOTAL_TABS;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		
		return CheckinsTabsFactory.getTabTitle(position);
	}

}
