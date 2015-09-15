package com.todo.behtarinhotel.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gc.materialdesign.views.ButtonFlat;
import com.todo.behtarinhotel.R;
import com.todo.behtarinhotel.fragments.ReadMoreFragment;
import com.todo.behtarinhotel.fragments.SearchFragment;
import com.todo.behtarinhotel.simpleobjects.SearchResultSO;
import com.todo.behtarinhotel.supportclasses.AppState;
import com.todo.behtarinhotel.supportclasses.DataLoader;
import com.todo.behtarinhotel.supportclasses.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;


public class WishListAdapter extends BaseAdapter {

    public static final String PHOTO_URL_START = "http://images.travelnow.com";
    public static final String PHOTO_URL_END = "z.jpg";
    ImageLoader imageLoader;
    ImageView ivPhoto;
    ImageView ivStar1, ivStar2, ivStar3, ivStar4, ivStar5;
    ImageView ivTripAdvisorRate;
    ButtonFlat btnReadMore, btnCheckAvailability;
    TextView tvHotelName;

    TextView tvAddress;
    TextView tvPrice;
    TextView tvLocationDescription;
    ArrayList<ImageView> imageViews;
    float rate;
    Resources res;
    TextView tvLikeCounter;

    Activity activity;
    LayoutInflater lInflater;
    ArrayList<SearchResultSO> searchResultSOArrayList;
    String tripAdvisorWebURL;
    String tripAdvisorApiURL;

    int visibleItems = 20;
    int visibleItemsStep = 20;

    private int posForLoading = 19;
    CheckBox chbWishList;
    RequestListener listener;

    public WishListAdapter(final Activity activity, ArrayList<SearchResultSO> searchResultSOArrayList) {
        this.activity = activity;
        this.searchResultSOArrayList = searchResultSOArrayList;
        lInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = VolleySingleton.getInstance(activity).getImageLoader();
        res = activity.getResources();

        listener = new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                String str = model.toString();
                Glide.with(activity.getApplicationContext())
                        .load(str.substring(0, str.length() - 5) + "b.jpg")
                        .error(R.drawable.empty)
                        .placeholder(R.color.base_grey)
                        .fitCenter()
                        .into(target);
                return true;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        };
    }

    @Override
    public int getCount() {
        if (searchResultSOArrayList.size() < visibleItems)
            return searchResultSOArrayList.size();
        else{
            return visibleItems;
        }
    }

    @Override
    public Object getItem(int i) {
        return searchResultSOArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.hotel_item, viewGroup,false);
        }

        btnReadMore = (ButtonFlat) view.findViewById(R.id.btn_read_more);
        btnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadMoreFragment readMoreFragment = new ReadMoreFragment();
                ((MaterialNavigationDrawer) activity).setFragmentChild(readMoreFragment, "About Hotel");
                readMoreFragment.setHotelData(searchResultSOArrayList.get(position));
            }
        });
        btnCheckAvailability = (ButtonFlat) view.findViewById(R.id.btn_check_availability);
        btnCheckAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchFragment searchFragment = new SearchFragment();
                searchFragment.setParameters(((SearchResultSO) getItem(position)).getHotelId(),((SearchResultSO) getItem(position)).getHotelName());
                ((MaterialNavigationDrawer) activity).setFragmentChild(searchFragment, "Search");

            }
        });

        ivPhoto = (ImageView) view.findViewById(R.id.iv_photo_main_activity_main_list);

        ivStar1 = (ImageView) view.findViewById(R.id.iv_star1_main_activity_main_list);
        ivStar2 = (ImageView) view.findViewById(R.id.iv_star2_main_activity_main_list);
        ivStar3 = (ImageView) view.findViewById(R.id.iv_star3_main_activity_main_list);
        ivStar4 = (ImageView) view.findViewById(R.id.iv_star4_main_activity_main_list);
        ivStar5 = (ImageView) view.findViewById(R.id.iv_star5_main_activity_main_list);


        imageViews = new ArrayList<>();
        imageViews.add(ivStar1);
        imageViews.add(ivStar2);
        imageViews.add(ivStar3);
        imageViews.add(ivStar4);
        imageViews.add(ivStar5);

        ivTripAdvisorRate = (ImageView) view.findViewById(R.id.iv_tripadvisor_rate_main_activity_main_list);

        tvHotelName = (TextView) view.findViewById(R.id.tv_hotel_name_main_activity_main_list);
//        tvCity = (TextView) view.findViewById(R.id.tv_city_name_main_activity_main_list);
        tvAddress = (TextView) view.findViewById(R.id.tv_address_main_activity_main_list);
        tvPrice = (TextView) view.findViewById(R.id.tv_price_main_activity_main_list);
        tvLocationDescription = (TextView) view.findViewById(R.id.tv_location_description_main_activity_main_list);


        final SearchResultSO searchResultSO = searchResultSOArrayList.get(position);
        tvHotelName.setText(Html.fromHtml(searchResultSO.getHotelName()));
//        tvCity.setText(searchResultSOArrayList.get(position).getCity());
        tvAddress.setText(Html.fromHtml(searchResultSO.getAddress()));
        tvLocationDescription.setText(Html.fromHtml(Html.fromHtml(searchResultSO.getLocationDescription()).toString()));
        tvPrice.setText("$" + String.format("%.2f", searchResultSO.getMinPrice()));


        if (searchResultSO.getPhotoURL() != null && !searchResultSO.getPhotoURL().equals("")) {
            String temp;
            temp = searchResultSO.getPhotoURL()
                    .substring(0, searchResultSO.getPhotoURL().length() - 5);
            Glide.with(activity)
                    .load(PHOTO_URL_START + temp + PHOTO_URL_END)
                    .fitCenter()
                    .placeholder(R.color.base_grey)
                    .error(R.drawable.empty)
                    .listener(listener)
                    .into(ivPhoto);

            Glide.with(activity)
                    .load(searchResultSO.getTripAdvisorRatingURL())
                    .fitCenter()
                    .error(R.mipmap.ic_launcher)
                    .into(ivTripAdvisorRate);
        }

        rate = searchResultSO.getStars();
        for (int i = 0; i < 5; i++) {
            if (rate >= 1) {
                rate--;
                imageViews.get(i).setImageDrawable(res.getDrawable(R.drawable.star_selected));
            } else if (rate == 0.5) {
                imageViews.get(i).setImageDrawable(res.getDrawable(R.drawable.star_half_selected));
                rate = 0;
            } else if (rate == 0) {
                imageViews.get(i).setImageDrawable(null);
            }
        }

        tvLikeCounter = (TextView) view.findViewById(R.id.tv_like_counter_activity_main_main_list);
        tvLikeCounter.setText("" + searchResultSO.getLikeCounter());

        chbWishList = (CheckBox) view.findViewById(R.id.chb_wish_main_activity_main_list);
        chbWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!AppState.isInWishList(searchResultSO.getHotelId())){
                    AppState.addToWishList(searchResultSO.getHotelId());
                }else{
                    AppState.removeFromWishList(searchResultSO.getHotelId());
                }
            }
        });
        if(AppState.isInWishList(searchResultSO.getHotelId())){
            chbWishList.setChecked(true);
        }else{
            chbWishList.setChecked(false);
        }


        ivTripAdvisorRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tripAdvisorWebURL != null) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tripAdvisorWebURL));
                    activity.startActivity(browserIntent);
                    Log.d("MainListAdapter", "Open URL");
                }
            }
        });

        tripAdvisorApiURL = "http://api.tripadvisor.com/api/partner/2.0/map/" + searchResultSO.getLatitude() +
                "," + searchResultSO.getLongitude() + "/hotels?key=cc1fb67fbf9c4c4592a1b7071a926087";
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    JSONObject obj = data.getJSONObject(0);
                    tripAdvisorWebURL = obj.getString("web_url");
                    Log.d("MainListAdapter", tripAdvisorWebURL);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DataLoader.makeRequest(tripAdvisorApiURL,listener);

        if (position == posForLoading) {
            loadNextHotels();
            posForLoading += 20;
        }

        return view;
    }

    private void loadNextHotels() {
        visibleItems += visibleItemsStep;
        notifyDataSetChanged();

    }

}