package com.todo.behtarinhotel.simpleobjects;

import java.util.ArrayList;

/**
 * Created by maxvitruk on 07.07.15.
 */
public class SearchParamsSO {
    String city;
    String arrivalDate = "10/10/2015";
    String departureDate = "10/12/2015";
    ArrayList<SearchRoomSO> rooms;
    int minStar;

    public SearchParamsSO(String city, String arrivalDate, String departureDate, ArrayList<SearchRoomSO> rooms, int minStar) {
        this.city = city;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.rooms = rooms;
        this.minStar = minStar;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public ArrayList<SearchRoomSO> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<SearchRoomSO> rooms) {
        this.rooms = rooms;
    }

    public int getMinStar() {
        return minStar;
    }

    public void setMinStar(int minStar) {
        this.minStar = minStar;
    }
}
