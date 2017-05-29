package com.asworks.hrmobileapp_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mehmetugurgural on 28/05/2017.
 */

public class EmployeeImage {
    @SerializedName("ImageURL")
    @Expose
    private String imageUrl;
    @SerializedName("ImageType")
    @Expose
    private String imageType;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
