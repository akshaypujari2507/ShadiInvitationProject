
package com.shadiinvitationproject.network.Retrofit.ResponseModels;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("city")
    private String mCity;
    @SerializedName("coordinates")
    private Coordinates mCoordinates;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("postcode")
    private String mPostcode;
    @SerializedName("state")
    private String mState;
    @SerializedName("street")
    private Street mStreet;
    @SerializedName("timezone")
    private Timezone mTimezone;

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public Coordinates getCoordinates() {
        return mCoordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        mCoordinates = coordinates;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getPostcode() {
        return mPostcode;
    }

    public void setPostcode(String postcode) {
        mPostcode = postcode;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public Street getStreet() {
        return mStreet;
    }

    public void setStreet(Street street) {
        mStreet = street;
    }

    public Timezone getTimezone() {
        return mTimezone;
    }

    public void setTimezone(Timezone timezone) {
        mTimezone = timezone;
    }

}
