/**
 * 
 */
package com.travelr.example.markmyplaces.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.travelr.example.markmyplaces.R;
import com.travelr.example.markmyplaces.ui.adapters.CheckinsFragmentAdapter;

/**
 *
 */
public class CheckinsHistoryActivity extends AppCompatActivity {

    private ViewPager mPagerEndUser;
    private TabLayout mTabLayoutuser;
    private CheckinsFragmentAdapter mAdapterPager;
    private Toolbar mToolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkins_history);
        initViews();
        initActionBar();
    }

    private void initActionBar() {
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

    }

    private void initViews() {

        mPagerEndUser = (ViewPager) findViewById(R.id.pager_checkins);
        mTabLayoutuser = (TabLayout) findViewById(R.id.tab_layout_checkins);
        mAdapterPager = new CheckinsFragmentAdapter(getSupportFragmentManager());
        mPagerEndUser.setAdapter(mAdapterPager);
        mTabLayoutuser.setupWithViewPager(mPagerEndUser);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public static void launchCheckinsHistory(Context context) {
        Intent checkinsListIntent = new Intent(context, CheckinsHistoryActivity.class);
        context.startActivity(checkinsListIntent);
    }
}
