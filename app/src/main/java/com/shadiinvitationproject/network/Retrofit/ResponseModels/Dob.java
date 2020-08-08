
package com.shadiinvitationproject.network.Retrofit.ResponseModels;

import com.google.gson.annotations.SerializedName;

public class Dob {

    @SerializedName("age")
    private Long mAge;
    @SerializedName("date")
    private String mDate;

    public Long getAge() {
        return mAge;
    }

    public void setAge(Long age) {
        mAge = age;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

}
