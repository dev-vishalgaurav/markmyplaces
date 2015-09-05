/**
 * 
 */
package com.travelr.example.markmyplaces.ui.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.travelr.example.markmyplaces.R;
import com.travelr.example.markmyplaces.app.PlacesApplication;
import com.travelr.example.markmyplaces.db.MyPlace;
import com.travelr.example.markmyplaces.ui.AddPlaceActivity;
import com.travelr.example.markmyplaces.ui.adapters.PlacesListAdapter;
import com.travelr.example.markmyplaces.ui.adapters.PlacesListAdapter.OnPlacesActionListener;

/**
 * @author vishal
 *
 */
public class CheckinsListFragment extends Fragment {

    public static final String TAG_FIXED = "checkins";

    private PlacesListAdapter mAdapterPlaces = null;
    private List<MyPlace> mPlacesList = null;
    private ListView mlstPlaces = null;
    private OnPlacesActionListener mPlacesActionListener = new OnPlacesActionListener() {

        @Override
        public void onRowItemClick(int position, MyPlace place) {
           onClickPlace(place);

        }

        @Override
        public void onClickShare(MyPlace place) {
            onClickPlaceShare(place);

        }

        @Override
        public void onClickMark(MyPlace place) {
            onClickPlaceAddToFence(place);

        }

    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_places_list, container, false);
        initFragment(rootView);
        return rootView;
    }

    private void initFragment(View rootView) {
        initDataComponents();
        initViews(rootView);
    }

    private void initDataComponents() {
        mPlacesList = new ArrayList<MyPlace>();
        mAdapterPlaces = new PlacesListAdapter(getActivity(), mPlacesList, mPlacesActionListener);
    }

    private void initViews(View rootView) {
        mlstPlaces = (ListView) rootView.findViewById(R.id.lstPlaces);
        mlstPlaces.setAdapter(mAdapterPlaces);
        mlstPlaces.setOnItemClickListener(mAdapterPlaces);
        mlstPlaces.setEmptyView(rootView.findViewById(R.id.empty_view));
    }
    
    private void onClickPlace(MyPlace place) {
        AddPlaceActivity.launchAddPlace(getActivity(),place);
        
    }
    private void onClickPlaceShare(MyPlace place) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(place.getShareUri());
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            PlacesApplication.showGenericToast(getActivity(), getString(R.string.failure));
        }

    }

    private void onClickPlaceAddToFence(MyPlace place) {
       onClickPlace(place);

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePlaces();
    }

    private void updatePlaces() {
        mPlacesList.clear();
        mPlacesList.addAll(PlacesApplication.getDatabase(getActivity()).getMyPlaces());
        mAdapterPlaces.notifyDataSetChanged();

    }

    /**
     * returns new instance of OrderTracking
     */
    public static CheckinsListFragment getInstance(int position) {
        CheckinsListFragment orderFragment = new CheckinsListFragment();
        Bundle args = new Bundle();
        args.putInt(CheckinsTabsFactory.TAG_POSITION, position);
        args.putString(TAG_FIXED, TAG_FIXED);
        orderFragment.setArguments(args);
        return orderFragment;
    }

}
