
package com.shadiinvitationproject.network.Retrofit.ResponseModels;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("md5")
    private String mMd5;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("salt")
    private String mSalt;
    @SerializedName("sha1")
    private String mSha1;
    @SerializedName("sha256")
    private String mSha256;
    @SerializedName("username")
    private String mUsername;
    @SerializedName("uuid")
    private String mUuid;

    public String getMd5() {
        return mMd5;
    }

    public void setMd5(String md5) {
        mMd5 = md5;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getSalt() {
        return mSalt;
    }

    public void setSalt(String salt) {
        mSalt = salt;
    }

    public String getSha1() {
        return mSha1;
    }

    public void setSha1(String sha1) {
        mSha1 = sha1;
    }

    public String getSha256() {
        return mSha256;
    }

    public void setSha256(String sha256) {
        mSha256 = sha256;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String uuid) {
        mUuid = uuid;
    }

}
