
package com.shadiinvitationproject.network.Retrofit.ResponseModels;

import com.google.gson.annotations.SerializedName;

public class Timezone {

    @SerializedName("description")
    private String mDescription;
    @SerializedName("offset")
    private String mOffset;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getOffset() {
        return mOffset;
    }

    public void setOffset(String offset) {
        mOffset = offset;
    }

}
