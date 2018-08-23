package com.blogbasbas.beritabola.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogbasbas.beritabola.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.img_berita_detail)
    ImageView imgBeritaDetail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tvTglTerbit)
    TextView tvTglTerbit;
    @BindView(R.id.tvPenulis)
    TextView tvPenulis;
    @BindView(R.id.wvKontenBerita)
    WebView wvKontenBerita;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // get Data Yang Dikrim dari List Recyclerview
        String judulBeritaDetail = getIntent().getStringExtra("JUDUL");
        String tanggalBeritaDetail = getIntent().getStringExtra("TGL");
        String penulisBeritaDetail = getIntent().getStringExtra("PENULIS");
        String isiBeritaDetail = getIntent().getStringExtra("ISI");
        String fotoBeritaDetail = getIntent().getStringExtra("FOTO");

        //set hasil dari get data intent
        getSupportActionBar().setTitle(judulBeritaDetail);
        tvTglTerbit.setText(tanggalBeritaDetail);
        tvPenulis.setText(penulisBeritaDetail);
        Picasso.get().load(fotoBeritaDetail).placeholder(R.mipmap.ic_launcher).into(imgBeritaDetail);
        //set hasil dari isi berita ke widget webview, (karena di database isi berita di kasih tag html agar lebih mudah mengatur tampilanya)
        wvKontenBerita.getSettings().setJavaScriptEnabled(true);
        wvKontenBerita.loadData(isiBeritaDetail,"text/html; charset=utf-8","UTF-8");




    }
}
