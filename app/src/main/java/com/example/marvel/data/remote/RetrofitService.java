package com.example.marvel.data.remote;

import com.example.marvel.util.Criptografia;
import com.facebook.stetho.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.marvel.viewmodel.ComicViewModel.PRIVATE_KEY;
import static javax.crypto.Cipher.PUBLIC_KEY;

public class RetrofitService {

    private static  final String BASE_URL = "https://gateway.marvel.com:443/v1/public/";
    private static Retrofit retrofit;


    private static Retrofit getRetrofit() {

        if (retrofit == null) {

            // Configuração de parametros de conexão
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(30, TimeUnit.SECONDS);
            httpClient.connectTimeout(30, TimeUnit.SECONDS);
            httpClient.writeTimeout(30, TimeUnit.SECONDS);

            // Se estivermos em modo DEBUG habilitamos os logs
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(httpLoggingInterceptor);
                httpClient.addNetworkInterceptor(new StethoInterceptor());
            }

            // inicializamos o retrofit com as configurações
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return retrofit;
    }

    // Retornamos a api criada com o retrofit
    public static API getApiService() {
        return getRetrofit().create(API.class);
    }

    public  static String getTimeStamp() {
        Long ts = Calendar.getInstance().getTimeInMillis() / 1000;

        return ts.toString();
    }

    public static String getHash(){
        return Criptografia.md5( getTimeStamp() + PRIVATE_KEY + PUBLIC_KEY);

    }
}


