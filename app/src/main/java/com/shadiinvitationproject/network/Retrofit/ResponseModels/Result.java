
package com.shadiinvitationproject.network.Retrofit.ResponseModels;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("cell")
    private String mCell;
    @SerializedName("dob")
    private Dob mDob;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("gender")
    private String mGender;
    @SerializedName("id")
    private Id mId;
    @SerializedName("location")
    private Location mLocation;
    @SerializedName("login")
    private Login mLogin;
    @SerializedName("name")
    private Name mName;
    @SerializedName("nat")
    private String mNat;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("picture")
    private Picture mPicture;
    @SerializedName("registered")
    private Registered mRegistered;

    public String getCell() {
        return mCell;
    }

    public void setCell(String cell) {
        mCell = cell;
    }

    public Dob getDob() {
        return mDob;
    }

    public void setDob(Dob dob) {
        mDob = dob;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public Id getId() {
        return mId;
    }

    public void setId(Id id) {
        mId = id;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public Login getLogin() {
        return mLogin;
    }

    public void setLogin(Login login) {
        mLogin = login;
    }

    public Name getName() {
        return mName;
    }

    public void setName(Name name) {
        mName = name;
    }

    public String getNat() {
        return mNat;
    }

    public void setNat(String nat) {
        mNat = nat;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public Picture getPicture() {
        return mPicture;
    }

    public void setPicture(Picture picture) {
        mPicture = picture;
    }

    public Registered getRegistered() {
        return mRegistered;
    }

    public void setRegistered(Registered registered) {
        mRegistered = registered;
    }

}
