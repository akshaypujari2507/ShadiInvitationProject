
package com.shadiinvitationproject.network.Retrofit.ResponseModels;

import com.google.gson.annotations.SerializedName;

public class Id {

    @SerializedName("name")
    private String mName;
    @SerializedName("value")
    private Object mValue;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Object getValue() {
        return mValue;
    }

    public void setValue(Object value) {
        mValue = value;
    }

}
