package com.todo.behtarinhotel.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter;
import com.todo.behtarinhotel.R;
import com.todo.behtarinhotel.adapters.MainActivityMainListAdapter;
import com.todo.behtarinhotel.simpleobjects.SearchParamsSO;
import com.todo.behtarinhotel.simpleobjects.SearchResultSO;
import com.todo.behtarinhotel.supportclasses.AppState;
import com.todo.behtarinhotel.supportclasses.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private static final String API_KEY = "7tuermyqnaf66ujk2dk3rkfk";
    private static final String CID = "55505";

    String url;
    String apiKey = "&apiKey=";
    String cid = "&cid=";
    String locale = "&locale=enUS";
    String customerSessionID = "&customerSessionID=1";
    String customerIpAddress = "&customerIpAddress=193.93.219.63";
    String arrivalDate = "&arrivalDate=";
    String currencyCode = "&currencyCode=USD";
    String departureDate = "&departureDate=";
    String city = "&destinationString=";
    String sig = "&sig=" + AppState.getMD5EncryptedString(apiKey + "RyqEsq69" + System.currentTimeMillis() / 1000L);
    String customerUserAgent = "&customerUserAgent=TravelWizard/1.0(iOS 10_10_3)MOBILE_APP";
    String minorRev = "&minorRev=30";
    String room = "&room1=";

    GsonBuilder gsonBuilder;
    Gson gson;


    SearchParamsSO searchParams;
    ArrayList<SearchResultSO> searchResultSOArrayList;
    ListView listView;
    String arrival;
    String departure;
    SlideExpandableListAdapter slideExpandableListAdapter;

    ButtonRectangle btnTryAgain;
    ProgressBarCircularIndeterminate pbLoadingHotels;
    TextView tvError;
    LinearLayout hotelLoadingScreen;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.lv_main_list_main_activity);
        btnTryAgain = (ButtonRectangle) rootView.findViewById(R.id.btnTryAgain);
        pbLoadingHotels = (ProgressBarCircularIndeterminate) rootView.findViewById(R.id.pbLoadingHotels);
        tvError = (TextView) rootView.findViewById(R.id.tvError);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataFromExpedia();
            }
        });

        hotelLoadingScreen = (LinearLayout) rootView.findViewById(R.id.hotelLoadingScreen);
        loadDataFromExpedia();

        return rootView;
    }


    public void setSearchParams(SearchParamsSO searchParams){
        this.searchParams = searchParams;
    }


    public void loadDataFromExpedia(){
        showLoadingScreen();

        if (searchParams != null) {
            url = "http://api.ean.com/ean-services/rs/hotel/v3/list?" +
                    apiKey + API_KEY +
                    cid + CID +
                    sig +
                    customerIpAddress +
                    //customerUserAgent +
                    currencyCode +
                    customerSessionID +
                    minorRev +
                    locale +
                    city + searchParams.getCity() +
                    arrival + searchParams.getArrivalDate() +
                    departure + searchParams.getDepartureDate() +
                    room + "1";


            gsonBuilder = new GsonBuilder();
            gson = gsonBuilder.create();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            JSONArray arr = null;
                            try {
                                arr = response.getJSONObject("HotelListResponse").getJSONObject("HotelList").getJSONArray("HotelSummary");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Type listOfTestObject = new TypeToken<ArrayList<SearchResultSO>>() {
                            }.getType();

                            if (arr != null) {
                                searchResultSOArrayList = gson.fromJson(arr.toString(), listOfTestObject);
                                MainActivityMainListAdapter adapter = new MainActivityMainListAdapter(getActivity(), searchResultSOArrayList, arrival, departure);
                                slideExpandableListAdapter = new SlideExpandableListAdapter(adapter, R.id.hotel_layout, R.id.expandableLayout);
                                listView.setAdapter(slideExpandableListAdapter);
                                clearLoadingScreen();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                    showError(error.getMessage());
                }
            }

            );
            VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
        }
    }

    private void showError(String errorMessage){
        hotelLoadingScreen.setVisibility(View.VISIBLE);
        pbLoadingHotels.setVisibility(View.GONE);
        btnTryAgain.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(errorMessage);
    }

    private void showLoadingScreen(){
        hotelLoadingScreen.setVisibility(View.VISIBLE);
        pbLoadingHotels.setVisibility(View.VISIBLE);
        btnTryAgain.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
    }

    private void clearLoadingScreen(){
        hotelLoadingScreen.setVisibility(View.GONE);
        tvError.setText("");
    }

}