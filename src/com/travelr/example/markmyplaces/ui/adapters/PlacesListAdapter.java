
package com.travelr.example.markmyplaces.ui.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelr.example.markmyplaces.R;
import com.travelr.example.markmyplaces.db.MyPlace;

public class PlacesListAdapter extends BaseAdapter implements OnItemClickListener  {
    
    public interface OnPlacesActionListener{
        public void onClickShare(MyPlace place);
        public void onClickMark(MyPlace place);
        public void onRowItemClick(int position,MyPlace place);
    }
    
    static class ViewHolder {
        private TextView txtTitle;

        private TextView txtMessage;

        private TextView txtAddress;

        private ImageView imgMark;

        private ImageView imgShare;
    }

    private OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgMark:
                    onClickMark(v);
                    break;
                case R.id.imgShare:
                    onClickShare(v);
                    break;
                default:
                    break;
            }

        }
    };
    
    private Context mContext = null;

    private List<MyPlace> mPlacesList;
    private OnPlacesActionListener mOnPlacesActionListener = null ;

    public PlacesListAdapter(Context mContext, List<MyPlace> mPlacesList, OnPlacesActionListener mOnPlacesActionListener) {
        super();
        this.mContext = mContext;
        this.mPlacesList = mPlacesList;
        this.mOnPlacesActionListener = mOnPlacesActionListener;
    }

    public void setPlacesList(List<MyPlace> placesList) {
        this.mPlacesList = placesList;
    }

    @Override
    public int getCount() {
        return mPlacesList == null ? 0 : mPlacesList.size();
    }

    @Override
    public Object getItem(int position) {

        return mPlacesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mPlacesList.get(position).getDbId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = checkAndGetView(convertView);
        updateView(position,convertView);
        return convertView;
    }
    
    
    
    private void updateView(int position, View convertView) {
        ViewHolder viewHolder = (ViewHolder)convertView.getTag(); 
        MyPlace place = (MyPlace) getItem(position);
        viewHolder.txtTitle.setText(place.getTitle());
        viewHolder.txtMessage.setText(place.getMessage());
        viewHolder.txtAddress.setText(place.getAddress());
        viewHolder.imgMark.setImageResource((place.isAddedToFence()? R.drawable.marked : R.drawable.unmarked));
        updatecallBacks(viewHolder,position,place);
        
    }
    private void onClickShare(View v) {
        mOnPlacesActionListener.onClickShare((MyPlace) v.getTag());
        
    }

    private void onClickMark(View v) {
        mOnPlacesActionListener.onClickMark((MyPlace) v.getTag());
        
    }
    private void updatecallBacks(ViewHolder viewHolder, int position, MyPlace place) {
        viewHolder.imgMark.setTag(place);
        viewHolder.imgShare.setTag(place);
        
    }

    private View checkAndGetView(View convertView) {
        if(convertView == null){
            convertView = getNewPlaceView() ;
        }
        return convertView;
    }

    private View getNewPlaceView(){
        View rowView = View.inflate(mContext, R.layout.places_list_row, null);
        setViewHolder(rowView);
        return rowView ;
    }

    private void setViewHolder(View rowView) {
        ViewHolder rowViewHolder = new ViewHolder();
        rowViewHolder.txtTitle = (TextView) rowView.findViewById(R.id.txtTitle);
        rowViewHolder.txtMessage = (TextView) rowView.findViewById(R.id.txtMessage);
        rowViewHolder.txtAddress = (TextView) rowView.findViewById(R.id.txtAddress);
        rowViewHolder.imgShare = (ImageView) rowView.findViewById(R.id.imgShare);
        rowViewHolder.imgMark = (ImageView) rowView.findViewById(R.id.imgMark);
        rowViewHolder.imgMark.setOnClickListener(mOnClickListener);
        rowViewHolder.imgShare.setOnClickListener(mOnClickListener);
        rowView.setTag(rowViewHolder);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mOnPlacesActionListener.onRowItemClick(position, (MyPlace) getItem(position));
        
    }
    
}
