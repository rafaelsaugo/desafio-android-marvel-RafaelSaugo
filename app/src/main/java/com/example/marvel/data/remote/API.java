package com.example.marvel.data.remote;

import com.example.marvel.model.ComicResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("comics?")
    Observable<ComicResponse> getComics(
            @Query("format") String format,
            @Query("formatType") String formatType,
            @Query("noVariants") boolean noVariants,
            @Query("ts") String ts,
            @Query("hash") String hash,
            @Query("apikey") String apikey);
}

