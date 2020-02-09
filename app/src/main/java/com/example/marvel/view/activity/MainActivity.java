package com.example.marvel.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.marvel.R;
import com.example.marvel.model.Result;
import com.example.marvel.view.adapter.RecyclerViewHomeAdapter;
import com.example.marvel.view.interfaces.OnClickItem;
import com.example.marvel.viewmodel.ComicViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickItem {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RecyclerViewHomeAdapter adapter;
    private List<Result> resultList = new ArrayList<>();
    private ComicViewModel viewModel;

    public static final String RESULT_KEY = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();
        setupToolbar();
        setupRecyclerView();
        comunicacaoViewModel();

    }

    public void initView(){
        recyclerView = findViewById(R.id.recyclerHome);
        progressBar = findViewById(R.id.progress_bar);
        adapter = new RecyclerViewHomeAdapter(resultList,this);
        viewModel = ViewModelProviders.of(this).get(ComicViewModel.class);
    }

    private void setupRecyclerView() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
    }

    private void setupToolbar(){
        //Configurações Toolbar
        toolbar = findViewById(R.id.toolbar_home);
        toolbar.setTitle("MARVEL");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setTitleMarginStart(8);
        setSupportActionBar(toolbar);
    }

    private void comunicacaoViewModel(){
        viewModel.getComics();

        viewModel.getListaMarvel().observe(this,results -> {
            adapter.atualizalista(results);
        });

        viewModel.loading().observe(this, loading -> {
            if (loading){
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void OnClick(Result result) {
        Intent intent = new Intent(this, DetalhesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(RESULT_KEY, result);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
