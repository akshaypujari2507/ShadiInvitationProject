
package com.shadiinvitationproject.network.Retrofit.ResponseModels;

import com.google.gson.annotations.SerializedName;

public class Street {

    @SerializedName("name")
    private String mName;
    @SerializedName("number")
    private Long mNumber;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getNumber() {
        return mNumber;
    }

    public void setNumber(Long number) {
        mNumber = number;
    }

}
