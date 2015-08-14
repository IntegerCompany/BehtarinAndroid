package com.todo.behtarinhotel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gc.materialdesign.views.ButtonFlat;
import com.todo.behtarinhotel.R;
import com.todo.behtarinhotel.simpleobjects.BookedRoomSO;
import com.todo.behtarinhotel.supportclasses.AppState;

import java.util.ArrayList;

/**
 * Created by Andriy on 13.08.2015.
 */
public class HistoryAdapter extends BaseAdapter {

    ArrayList<BookedRoomSO> history;
    Context context;
    LayoutInflater inflater;

    public HistoryAdapter(Context context){
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        history = AppState.getHistory();
    }

    @Override
    public int getCount() {
        return history.size();
    }

    @Override
    public Object getItem(int position) {
        return history.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null){
            view = convertView;
        }else{
            view = inflater.inflate(R.layout.history_item, null, false);
        }
        TextView tvHotelName = (TextView) view.findViewById(R.id.tvHotelName);
        TextView tvHotelAddress = (TextView) view.findViewById(R.id.tvHotelAddress);
        TextView tvRoomPrice = (TextView) view.findViewById(R.id.tvRoomPrice);
        TextView tvArrival = (TextView) view.findViewById(R.id.tvArrival);
        TextView tvDeparture = (TextView) view.findViewById(R.id.tvDeparture);
        ButtonFlat btnState = (ButtonFlat) view.findViewById(R.id.btnState);
        ImageView image = (ImageView) view.findViewById(R.id.imageRoom);

        BookedRoomSO historyRoom = history.get(position);

        tvHotelName.setText(historyRoom.getHotelName());
        tvHotelAddress.setText(historyRoom.getHotelAddress());
        tvRoomPrice.setText(historyRoom.getRoomPrice() + " $");
        tvArrival.setText(historyRoom.getArrivalDate());
        tvDeparture.setText(historyRoom.getDepartureDate());

        Glide.with(context)
                .load(historyRoom.getPhotoUrl())
                .into(image);

        switch (historyRoom.getOrderState()){
            case BookedRoomSO.BOOKED:
                btnState.setText("Booked");
                break;
            case BookedRoomSO.ACTIVE:
                btnState.setText("Active");
                break;
            case BookedRoomSO.OUT_OF_DATE:
                btnState.setText("Passed");
                break;
            case BookedRoomSO.CANCELLED:
                btnState.setText("Cancelled");
                break;
        }


        return view;
    }
}