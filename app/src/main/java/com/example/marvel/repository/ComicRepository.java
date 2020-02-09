package com.example.marvel.repository;

import com.example.marvel.model.ComicResponse;

import io.reactivex.Observable;

import static com.example.marvel.data.remote.RetrofitService.getApiService;

public class ComicRepository {
    public static final String PUBLIC_KEY​ = "e2c7f5bac15d65dab2bdc20a2e54d443";

    public Observable<ComicResponse> getComics(){
        return getApiService().getComics("magazine", "comic",
                true, "1551213595", "87d266cda07786b8e39e77f1c30d0b7e",
                "9c5165ef2c6bd235956b348ede63345658d3d7d8");

    }
}
