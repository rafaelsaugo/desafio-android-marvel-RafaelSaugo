package com.example.marvel.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.marvel.R;
import com.example.marvel.model.Result;
import com.squareup.picasso.Picasso;

import static com.example.marvel.view.activity.DetalhesActivity.DETAILSIMAGE_KEY;

public class DetalheImageActivity extends AppCompatActivity {

    private ImageView imagemDetalhe;
    private ImageButton btnClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_image);

        initViews();
        updateElementos();
        btnClose();
    }

    private void initViews(){
        imagemDetalhe = findViewById(R.id.img_detalhe_imagem);
        btnClose = findViewById(R.id.btn_close_detalhe);
    }

    private void updateElementos(){
        if (getIntent() != null && getIntent().getExtras() != null) {

            Result result = getIntent().getExtras().getParcelable(DETAILSIMAGE_KEY);
            Picasso.get().load(result.getThumbnail().getPath() + ".jpg").into(imagemDetalhe);

        }
    }

    private void btnClose(){
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }
}
