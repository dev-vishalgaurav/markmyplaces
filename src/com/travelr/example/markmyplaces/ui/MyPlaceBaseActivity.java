package com.travelr.example.markmyplaces.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

public class MyPlaceBaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog = null ;
    protected void showProgressDialog(String progressMessage,boolean isCancelable){
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(MyPlaceBaseActivity.this);
        }
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(isCancelable);
        mProgressDialog.setMessage(progressMessage);
        mProgressDialog.show();
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }
    protected void dismissProgressDialog(){
        if(mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss() ;
        }
        mProgressDialog = null;
    }
}
