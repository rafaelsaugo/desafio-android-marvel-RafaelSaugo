package com.example.marvel.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.marvel.model.Result;
import com.example.marvel.repository.ComicRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ComicViewModel extends AndroidViewModel {
    private MutableLiveData<List<Result>> listaMarvel = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private ComicRepository repository = new ComicRepository();

    public static final String PRIVATE_KEY = "0dd0c16fedb8a02985977eafca66b49f5e6a526f";


    public ComicViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Result>> getListaMarvel(){
        return this.listaMarvel;
    }

    public MutableLiveData<Boolean> loading() {
        return this.loading;
    }

    public void getComics(){
        disposable.add(
                repository.getComics()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> loading.setValue(true))
                        .doOnTerminate(() -> loading.setValue(false))
                        .flatMap(comicsResponse -> Observable.just(comicsResponse.getData().getResults()))
                        .subscribe(resultlist ->
                                        listaMarvel.setValue(resultlist),
                                throwable -> {
                                    Log.i("LOG", "erro" + throwable.getMessage());
                                })
        );

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}

