package com.bmustapha.trackr.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bmustapha.trackr.R;
import com.bmustapha.trackr.models.DateHistory;

import java.util.ArrayList;

/**
 * Created by tunde on 10/17/15.
 */
public class DateHistoryAdapter extends BaseAdapter {

    private final LayoutInflater dateHistoryInflater;
    private ArrayList<DateHistory> dateHistories;

    public DateHistoryAdapter(Context context, ArrayList<DateHistory> dateHistories) {
        this.dateHistories = dateHistories;
        this.dateHistoryInflater = LayoutInflater.from(context);
    }

    public class ViewHolder {
        TextView date;
        TextView locationCount;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = dateHistoryInflater.inflate(R.layout.single_date_history_item, parent, false);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.locationCount = (TextView) convertView.findViewById(R.id.location_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //get song using position
        DateHistory dateHistory = dateHistories.get(position);
        String locationCount = (dateHistory.getCount() > 1) ? dateHistory.getCount() + " locations" : dateHistory.getCount() + " location";
        holder.date.setText(dateHistory.getDate());
        holder.locationCount.setText(locationCount);

        return convertView;
    }

    @Override
    public int getCount() {
        return dateHistories.size();
    }

    @Override
    public DateHistory getItem(int position) {
        return dateHistories.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
