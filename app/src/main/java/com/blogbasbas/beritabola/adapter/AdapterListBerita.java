package com.blogbasbas.beritabola.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogbasbas.beritabola.R;
import com.blogbasbas.beritabola.activity.DetailActivity;
import com.blogbasbas.beritabola.model.BeritaItem;
import com.blogbasbas.beritabola.network.InitRetrofit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 15/07/2018.
 */

public class AdapterListBerita extends RecyclerView.Adapter<AdapterListBerita.MyViewHolder> {
    //buat varaibel global
    Context context;
    List<BeritaItem> itemList ;

    //constructor utk penghubung di kelas yang akan memakai adpaterlistberita
    public AdapterListBerita(Context context, List<BeritaItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    //method onCreateviewholder utk tempelating layout
    @Override
    public AdapterListBerita.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_berita,parent,false);
             MyViewHolder myViewHolder = new MyViewHolder(view);
             return myViewHolder;

    }

    //utk set item yang akan ditampilkan di list Recyclerview
    @Override
    public void onBindViewHolder(AdapterListBerita.MyViewHolder holder, final int position) {
        //bsa ngecek pakai log
        Log.e("Tag","Hasil Listadapter "+itemList.get(position).getJudulBerita());

        //set item di list Recycler
        holder.tvJudulBerita.setText(itemList.get(position).getJudulBerita());
        holder.tvTanggalBerita.setText(itemList.get(position).getTanggalPosting());
        //get gambar
        Picasso.get().load(InitRetrofit.BASE_URL+"images/"+itemList.get(position).getFoto()).into(holder.imgGambarBerita);

        //buat item dari recylerview bisa Di Klik
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kirim data ke detail activity
                Intent sendData = new Intent(context, DetailActivity.class);
                sendData.putExtra("JUDUL",itemList.get(position).getJudulBerita());
                sendData.putExtra("ISI",itemList.get(position).getIsiBerita());
                sendData.putExtra("PENULIS",itemList.get(position).getPenulis());
                sendData.putExtra("TGL",itemList.get(position).getTanggalPosting());
                sendData.putExtra("FOTO",InitRetrofit.BASE_URL+"images/"+itemList.get(position).getFoto());
                context.startActivity(sendData);

            }
        });



    }

    //set panajang dari list recyclerview
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    //utk inisialisasi Id pada layout item_berita
    public class MyViewHolder extends RecyclerView.ViewHolder {
        //buat Varibel utk inisialisasi id pada layout item_berita
        TextView tvJudulBerita, tvTanggalBerita;
        ImageView imgGambarBerita;
        public MyViewHolder(View itemView) {
            super(itemView);
            //inisisalisasi variabel dan id di layout
            tvJudulBerita = (TextView)itemView.findViewById(R.id.tv_judul_list_item);
            tvTanggalBerita=(TextView)itemView.findViewById(R.id.tv_tanggal_list_item);
            imgGambarBerita=(ImageView)itemView.findViewById(R.id.img_list_item);

        }
    }

   public void setFilter(ArrayList<BeritaItem> filterList){
       itemList = new ArrayList<>();
        itemList.addAll(filterList);
        notifyDataSetChanged();
    }
}
