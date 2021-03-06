package com.todo.behtarinhotel.simpleobjects;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Andriy on 13.07.2015.
 */
public class AvailableRoomsSO {


    @SerializedName("hotelId")
    private int hotelId;
    @SerializedName("hotelName")
    private String hotelName;
    @SerializedName("hotelAddress")
    private String hotelAddress;
    @SerializedName("hotelCountry")
    private String hotelCountry;
    //@SerializedName("HotelRoomResponse")
    private ArrayList<RoomSO> roomSO;
    @SerializedName("checkInInstructions")
    private String checkInInstruction;

    public String getCheckInInstruction() {
        return checkInInstruction;
    }

    public int getHotelId() {
        return hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public String getHotelCountry() {
        return hotelCountry;
    }

    public ArrayList<RoomSO> getRoomSO() {
        return roomSO;
    }

    public void setRoomSO(ArrayList<RoomSO> roomSO) {
        this.roomSO = roomSO;
    }

    // This inner classes used to parse their fucking response
    public class RoomSO {
        private float[] nightlyRates;
        @SerializedName("rateCode")
        private int rateCode;
        @SerializedName("roomTypeCode")
        private int roomTypeCode;
        @SerializedName("rateOccupancyPerRoom")
        private int rateOccupancyPerRoom;
        @SerializedName("quotedOccupancy")
        private int maxGuests;
        @SerializedName("RateInfos")
        private RateInfos rateInfos;
        @SerializedName("roomTypeDescription")
        private String description;
        @SerializedName("RoomImages")
        private RoomImages roomImages;
        @SerializedName("smokingPreferences")
        private String smokingPreference;

        private String rateKey;
        private ArrayList<Bed> beds;

        public float[] getNightlyRates() {
            return nightlyRates;
        }

        public void setNightlyRates(float[] nightlyRates) {
            this.nightlyRates = nightlyRates;
        }

        public String getRateKey() {
            return rateKey;
        }
        public void setRateKey(String rateKey) {
            this.rateKey = rateKey;
        }

        private String bedDescription;
        private int bedsQuantity;


        public int getRateCode() {
            return rateCode;
        }

        public void setRateCode(int rateCode) {
            this.rateCode = rateCode;
        }

        public int getRoomTypeCode() {
            return roomTypeCode;
        }

        public int getRateOccupancyPerRoom() {
            return rateOccupancyPerRoom;
        }

        public RateInfos getRateInfos() {
            return rateInfos;
        }

        public String getBedDescription() {
            return bedDescription;
        }

        public void setBedDescription(String descr) {
            bedDescription = descr;
        }

        public int getBedsQuantity() {
            return bedsQuantity;
        }

        public void setBedsQuantity(int bedsQuantity) {
            this.bedsQuantity = bedsQuantity;
        }


        public String getDescription() {
            return description;
        }

        public String getRoomImage() {
            if (roomImages != null && roomImages.getRoomImage() != null && roomImages.getRoomImage().getUrl() != null) {
                return roomImages.getRoomImage().getUrl();
            }
            return null;
        }

        public int getMaxGuests() {
            return maxGuests;
        }

        public float getAverageRate() {
            return rateInfos.getRateInfo().getChargeableRateInfo().getAverageRate();
        }

        public float getTotal() {
            return rateInfos.getRateInfo().getChargeableRateInfo().getTotal();
        }

        public float getNightlyRateTotal() {
            return rateInfos.getRateInfo().getChargeableRateInfo().getNightlyRateTotal();
        }

        public float getSurchargeTotal() {
            return rateInfos.getRateInfo().getChargeableRateInfo().getSurchargeTotal();
        }

        public float getOldPrice() {
            return rateInfos.getRateInfo().getChargeableRateInfo().getOldPrice();
        }


        public ArrayList<Bed> getBeds() {
            return beds;
        }

        public void setBed(ArrayList<Bed> beds) {
            this.beds = beds;
        }



        private class RoomImages {
            @SerializedName("RoomImage")
            private RoomImage roomImage;

            private RoomImage getRoomImage() {
                return roomImage;
            }

            private class RoomImage {
                @SerializedName("url")
                private String url;

                public String getUrl() {
                    return url;
                }
            }
        }

        private class RateInfos {
            @SerializedName("RateInfo")
            private RateInfo rateInfo;

            private RateInfo getRateInfo() {
                return rateInfo;
            }

            private class RateInfo {
                @SerializedName("ChargeableRateInfo")
                private ChargeableRateInfo chargeableRateInfo;
                @SerializedName("cancellationPolicy")
                private String cancellationPolicy;

                private ChargeableRateInfo getChargeableRateInfo() {
                    return chargeableRateInfo;
                }

                public String getCancellationPolicy() {
                    return cancellationPolicy;
                }

                private class ChargeableRateInfo {
                    @SerializedName("@averageRate")
                    private float averageRate;
                    @SerializedName("@averageBaseRate")
                    private float oldPrice;
                    @SerializedName("@nightlyRateTotal")
                    private float nightlyRateTotal;
                    @SerializedName("@surchargeTotal")
                    private float surchargeTotal;
                    @SerializedName("@total")
                    private float total;

                    private float getAverageRate() {
                        return averageRate;
                    }

                    private float getOldPrice() {
                        return oldPrice;
                    }

                    private float getNightlyRateTotal() {
                        return nightlyRateTotal;
                    }

                    private float getSurchargeTotal() {
                        return surchargeTotal;
                    }

                    private float getTotal() {
                        return total;
                    }
                }
            }
        }

        public String getSmokingPreference() {
            return smokingPreference;
        }

        public void setSmokingPreference(String smokingPreference) {
            this.smokingPreference = smokingPreference;
        }

        public String getCancellationPolicy() {
            return rateInfos.getRateInfo().getCancellationPolicy();
        }


    }

    public class Bed{
        private String bedDescript;
        private int id;

        public Bed() {
        }

        public String getBedDescript() {
            return bedDescript;
        }

        public void setBedDescript(String bedDescript) {
            this.bedDescript = bedDescript;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public Bed buildBedObject(){
        return new Bed();
    }


}
