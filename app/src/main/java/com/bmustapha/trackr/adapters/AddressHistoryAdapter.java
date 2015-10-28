package com.bmustapha.trackr.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bmustapha.trackr.R;
import com.bmustapha.trackr.models.AddressHistory;

import java.util.ArrayList;

/**
 * Created by andela on 10/20/15.
 */
public class AddressHistoryAdapter extends BaseAdapter {

    private final LayoutInflater addressHistoryInflater;
    private ArrayList<AddressHistory> addressHistories;

    public AddressHistoryAdapter(Context context, ArrayList<AddressHistory> addressHistories) {
        this.addressHistories = addressHistories;
        this.addressHistoryInflater = LayoutInflater.from(context);
    }

    public class ViewHolder {
        TextView address;
        TextView addressCount;
        TextView timeSpent;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = addressHistoryInflater.inflate(R.layout.single_address_history_item, parent, false);
            holder = new ViewHolder();
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.addressCount = (TextView) convertView.findViewById(R.id.address_count);
            holder.timeSpent = (TextView) convertView.findViewById(R.id.time_spent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AddressHistory addressHistory = addressHistories.get(position);
        String locationCount = (addressHistory.getCount() > 1) ? addressHistory.getCount() + " records" : addressHistory.getCount() + " record";
        holder.address.setText(addressHistory.getAddress());
        holder.addressCount.setText(locationCount);
        String timeSpentLabel = "Time spent: " + getRelativeTime(Long.valueOf(addressHistory.getTime()));
        holder.timeSpent.setText(timeSpentLabel);

        return convertView;
    }

    private String getRelativeTime(long time) {
        final int SECOND = 1000;
        final int MINUTE = 60 * SECOND;
        final int HOUR = 60 * MINUTE;
        final int DAY = 24 * HOUR;

        StringBuilder text = new StringBuilder("");
        if (time > DAY) {
            text.append(time / DAY).append(" days ");
            time %= DAY;
        }
        if (time > HOUR) {
            text.append(time / HOUR).append(" hours ");
            time %= HOUR;
        }
        if (time > MINUTE) {
            text.append(time / MINUTE).append(" minutes ");
            time %= MINUTE;
        }
        if (time > SECOND) {
            text.append(time / SECOND).append(" seconds ");
            time %= SECOND;
        }
        text.append(time).append(" ms");
        return text.toString();
    }

    @Override
    public int getCount() {
        return addressHistories.size();
    }

    @Override
    public AddressHistory getItem(int i) {
        return addressHistories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
