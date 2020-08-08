package com.shadiinvitationproject.network.Retrofit;

import com.shadiinvitationproject.network.Retrofit.ResponseModels.MatchResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RFInterface {

    @POST("api/")
    Call<MatchResponse> getMatchResponse(@Query("results") int results);

}