package com.stickercamera.app.ui.mainfeed;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ashwinchandlapur on 04/10/17.
 */

public interface RequestInterface {

//    @GET("android/jsonandroid")
    @GET("/AshwinChandlapur/ImgLoader/gh-pages/example.json")
    //    https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/example.json

    Call<JSONResponse> getJSON();
}
