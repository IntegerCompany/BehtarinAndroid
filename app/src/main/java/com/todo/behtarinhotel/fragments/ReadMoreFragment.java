package com.todo.behtarinhotel.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonFloat;
import com.todo.behtarinhotel.R;
import com.todo.behtarinhotel.simpleobjects.SearchResultSO;

import java.util.ArrayList;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadMoreFragment extends Fragment {
    public static final String PHOTO_URL_START = "http://images.travelnow.com";
    public static final String PHOTO_URL_END = "b.jpg";

    TextView tvHotelName, tvHotelAddress, tvHotelDescription, tvHotelPrice, tvHotelLikes;
    ImageView hotelImage, imageTripAdvisor, hotelStar1, hotelStar2, hotelStar3, hotelStar4, hotelStar5;
    ButtonFloat btnFloat;
    ButtonFlat btnCheckAvailability;
    View rootView;
    SearchResultSO searchResultSO;
    float rate;
    String arrival;
    String departure;

    ArrayList<ImageView> imageViews;


    public ReadMoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_read_more, container, false);
        initViewsById();
        fillWithData();


        return rootView;
    }

    private void initViewsById() {
        tvHotelName = (TextView) rootView.findViewById(R.id.tv_hotel_name);
        tvHotelAddress = (TextView) rootView.findViewById(R.id.tv_adress);
        tvHotelDescription = (TextView) rootView.findViewById(R.id.tv_hotel_description);
        tvHotelPrice = (TextView) rootView.findViewById(R.id.tv_hotel_price);
        tvHotelLikes = (TextView) rootView.findViewById(R.id.tv_hotel_likes);
        hotelImage = (ImageView) rootView.findViewById(R.id.hotel_image);
        imageTripAdvisor = (ImageView) rootView.findViewById(R.id.image_trip_advisor);
        hotelStar1 = (ImageView) rootView.findViewById(R.id.hotel_star_1);
        hotelStar2 = (ImageView) rootView.findViewById(R.id.hotel_star_2);
        hotelStar3 = (ImageView) rootView.findViewById(R.id.hotel_star_3);
        hotelStar4 = (ImageView) rootView.findViewById(R.id.hotel_star_4);
        hotelStar5 = (ImageView) rootView.findViewById(R.id.hotel_star_5);
        btnFloat = (ButtonFloat) rootView.findViewById(R.id.btn_float);
        btnCheckAvailability = (ButtonFlat) rootView.findViewById(R.id.btn_check_availability);
    }

    public void setHotelData(SearchResultSO searchResultSO, String arrival, String departure) {
        this.searchResultSO = searchResultSO;
        this.arrival = arrival;
        this.departure = departure;

    }

    private void fillWithData() {
        imageViews = new ArrayList<>();
        imageViews.add(hotelStar1);
        imageViews.add(hotelStar2);
        imageViews.add(hotelStar3);
        imageViews.add(hotelStar4);
        imageViews.add(hotelStar5);

        tvHotelName.setText(searchResultSO.getHotelName());
//        tvCity.setText(searchResultSOArrayList.get(position).getCity());
        tvHotelAddress.setText(searchResultSO.getAddress());
        tvHotelDescription.setText(Html.fromHtml(Html.fromHtml(searchResultSO.getLocationDescription()).toString()));
        tvHotelPrice.setText(searchResultSO.getMinPrice() + " $");
        tvHotelLikes.setText("" + searchResultSO.getLikeCounter());

        btnCheckAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAvailabilityFragment checkAvailabilityFragment = new CheckAvailabilityFragment();
                ((MaterialNavigationDrawer) getActivity()).setFragmentChild(checkAvailabilityFragment, getActivity().getString(R.string.fragment_checkavailablerooms));
                checkAvailabilityFragment.getData(searchResultSO.getHotelId(), arrival, departure);

            }
        });


        String temp = searchResultSO.getPhotoURL()
                .substring(0, searchResultSO.getPhotoURL().length() - 5);

        Glide.with(getActivity())
                .load(PHOTO_URL_START + temp + PHOTO_URL_END)
                .placeholder(R.mipmap.ic_hotel_placeholder)
                .error(R.drawable.empty)
                .into(hotelImage);

        Glide.with(getActivity())
                .load(searchResultSO.getTripAdvisorRatingURL())
                .placeholder(R.color.background_material_light)
                .error(R.mipmap.ic_launcher)
                .into(imageTripAdvisor);


        rate = searchResultSO.getStars();
        for (int i = 0; i < 5; i++) {
            if (rate >= 1) {
                rate--;
                imageViews.get(i).setImageDrawable(getResources().getDrawable(R.drawable.star_selected));
            } else if (rate == 0.5) {
                imageViews.get(i).setImageDrawable(getResources().getDrawable(R.drawable.star_half_selected));
                rate = 0;
            } else if (rate == 0) {
            }
        }
    }


}
