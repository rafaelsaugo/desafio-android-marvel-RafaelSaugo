package com.example.marvel.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marvel.R;
import com.example.marvel.model.Result;
import com.example.marvel.view.interfaces.OnClickImage;
import com.squareup.picasso.Picasso;

import static com.example.marvel.view.activity.MainActivity.RESULT_KEY;

public class DetalhesActivity extends AppCompatActivity implements OnClickImage {
    private ImageView ivDetalheFundo, ivDetalheFrente;
    private TextView tvDetalheTitulo, tvDetalheDescricao, tvDetalhePublicacao, tvDetalhePreco, tvDetalhePagina;
    private ImageButton btnBack;

    public static final String DETAILSIMAGE_KEY = "imagedetalhe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        initView();
        updateElementos();
        clickBack();
    }

    public void initView() {

        ivDetalheFundo = findViewById(R.id.imagemDetalhe);
        ivDetalheFrente = findViewById(R.id.image_click);
        tvDetalheTitulo = findViewById(R.id.text_titulo_detalhes);
        tvDetalheDescricao = findViewById(R.id.text_descricao_detalhes);
        tvDetalhePublicacao = findViewById(R.id.text_data_publicacao);
        tvDetalhePreco = findViewById(R.id.text_preco);
        tvDetalhePagina = findViewById(R.id.text_paginas);
        btnBack = findViewById(R.id.back_detalhe);

    }

    private void clickBack(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    private void updateElementos(){

        if (getIntent() != null && getIntent().getExtras() != null) {

            Result result = getIntent().getParcelableExtra(RESULT_KEY);

            Picasso.get().load(result.getThumbnail().getPath() + ".jpg").into(ivDetalheFrente);
            Picasso.get().load(result.getThumbnail().getPath() + ".jpg").into(ivDetalheFundo);

            tvDetalheTitulo.setText(result.getTitle());
            tvDetalheDescricao.setText(result.getDescription());

            String dataForm = result.getDates().get(0).getDate().split("T")[0];
            String[] listDate = dataForm.split("-");
            String dataPublicacao = listDate[2] + "/" + listDate[1] + "/" + listDate[0];
            tvDetalhePublicacao.setText(dataPublicacao);

            tvDetalhePreco.setText("$ " +result.getPrices().get(0).getPrice());

            tvDetalhePagina.setText(result.getPageCount().toString());

            ivDetalheFrente.setOnClickListener(v -> {
                Intent intent = new Intent(DetalhesActivity.this, DetalheImageActivity.class);
                intent.putExtra(DETAILSIMAGE_KEY, result);
                startActivity(intent);
            });

        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void OnclickImage(Result result) {
        Intent intent = new Intent(this, DetalheImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(RESULT_KEY, result);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();

    }
}