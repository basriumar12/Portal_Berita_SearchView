package com.blogbasbas.beritabola.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.blogbasbas.beritabola.R;
import com.blogbasbas.beritabola.adapter.AdapterListBerita;
import com.blogbasbas.beritabola.model.BeritaItem;
import com.blogbasbas.beritabola.model.ResponseBerita;
import com.blogbasbas.beritabola.network.InitRetrofit;
import com.blogbasbas.beritabola.network.ServiceRetrofit;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.rv_list_berita)
    RecyclerView rvListBerita;
    List<BeritaItem> hasil;
    AdapterListBerita listBerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //inisialisasi
        //utk 1 kolom di kasih spanCount : 1,
        // jika ingin lebh dari 1 kolom, spancount diisi > 1
        rvListBerita.setLayoutManager(new GridLayoutManager(this,1));


        hasil = new ArrayList<>();
        //method utk get data
        getDataBrita();

    }

    private void getDataBrita() {
        //1 cetak object dari kelas Serviceretrofit berdasarkan fungsi dalam kelas initrerofit yaitu method getinstance
        ServiceRetrofit serviceRetrofit = InitRetrofit.getInstance();
        //2 cetak object yang memamnggil dari model ResponBerita berdasarkan object yang telah dibuat dalam kelas serviceRetrofit
        Call<ResponseBerita> beritaCall = serviceRetrofit.getData();
        //3 panggil request yang telah dibuat, untuk mendapatkan repon dari data yang telah di request, yang mana ada dua method yang automatis terbuat yaitu Onrespon dan Onfailure
        beritaCall.enqueue(new Callback<ResponseBerita>() {
            @Override
            //method onResponse utk hasil request data
            public void onResponse(Call<ResponseBerita> call, Response<ResponseBerita> response) {
                // cara cek hasil dari berita
                 hasil = response.body().getBerita();
                //tampilkan dalam bentuk log
                Log.e("TAG", "Hasil Response :" + new Gson().toJson(hasil));
                //set adapter
                listBerita = new AdapterListBerita(MainActivity.this, hasil);
                Log.e("Tag", "Cek adapter " + listBerita);
                if (response.body().isStatus()== true){
                //set object Recylerview ke adapter
                rvListBerita.setAdapter(listBerita);
                } else {
                    Log.e("TAG","Gagal Request");
                }





            }

            //method onFailure menampilkan error dari respon request
            @Override
            public void onFailure(Call<ResponseBerita> call, Throwable t) {
                //cetak gagal request
                Log.e("Tag", "Hasil Error " + t.getMessage());

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem searchIem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchIem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<BeritaItem> dataFilter = new ArrayList<>();
                for (BeritaItem beritaItem : hasil){
                    String nameBerita = beritaItem.getJudulBerita().toLowerCase();
                    if (nameBerita.contains(newText)){
                        dataFilter.add(beritaItem);

                    }

                }
                listBerita.setFilter(dataFilter);

                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
