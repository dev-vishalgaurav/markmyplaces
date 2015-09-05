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

public class GeoFencedPlacesListAdapter extends BaseAdapter implements OnItemClickListener {

    public interface OnMarkedPlacesActionListener {
        public void onClickDelete(MyPlace place);

        public void onRowItemClick(int position, MyPlace place);
    }

    static class ViewHolder {
        private TextView txtTitle;

        private TextView txtAddress;

        private TextView txtRadius;

        private ImageView imgDelete;
    }

    private OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgDelete:
                    onClickDelete(v);
                    break;
                default:
                    break;
            }

        }
    };

    private Context mContext = null;

    private List<MyPlace> mPlacesList;

    private OnMarkedPlacesActionListener mOnMarkedPlacesListener = null;

    public GeoFencedPlacesListAdapter(Context mContext, List<MyPlace> mPlacesList, OnMarkedPlacesActionListener onMarkedPlacesListener) {
        super();
        this.mContext = mContext;
        this.mPlacesList = mPlacesList;
        this.mOnMarkedPlacesListener = onMarkedPlacesListener;
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
        updateView(position, convertView);
        return convertView;
    }

    private void updateView(int position, View convertView) {
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        MyPlace place = (MyPlace) getItem(position);
        viewHolder.txtTitle.setText(place.getTitle());
        viewHolder.txtAddress.setText(place.getAddress());
        viewHolder.txtRadius.setText("" + place.getRadius());
        updatecallBacks(viewHolder, position, place);

    }

    private void onClickDelete(View v) {

        mOnMarkedPlacesListener.onClickDelete((MyPlace) v.getTag());

    }

    private void updatecallBacks(ViewHolder viewHolder, int position, MyPlace place) {
        viewHolder.imgDelete.setTag(place);
        viewHolder.imgDelete.setOnClickListener(mOnClickListener);
    }

    private View checkAndGetView(View convertView) {
        if (convertView == null) {
            convertView = getNewPlaceView();
        }
        return convertView;
    }

    private View getNewPlaceView() {
        View rowView = View.inflate(mContext, R.layout.places_marked_row, null);
        setViewHolder(rowView);
        return rowView;
    }

    private void setViewHolder(View rowView) {
        ViewHolder rowViewHolder = new ViewHolder();
        rowViewHolder.txtTitle = (TextView) rowView.findViewById(R.id.txtTitle);
        rowViewHolder.txtAddress = (TextView) rowView.findViewById(R.id.txtAddress);
        rowViewHolder.txtRadius = (TextView) rowView.findViewById(R.id.txtRadius);
        rowViewHolder.imgDelete = (ImageView) rowView.findViewById(R.id.imgDelete);
        rowView.setTag(rowViewHolder);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mOnMarkedPlacesListener.onRowItemClick(position, (MyPlace) getItem(position));

    }

}
