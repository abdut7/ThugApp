package com.example.thugwtsapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thugwtsapp.utls.DownloadFileFromURL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {

    private ArrayList<User> userArrayList;
    private ArrayList<User> userArrayListCopy;
    private Context context;

    MediaPlayer mp = new MediaPlayer();
    ViewHolder mpHolder = null;
    private Handler mHandler = new Handler();

    public MyAdapter(Context context, ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
        userArrayListCopy = new ArrayList<>(userArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.ViewHolder holder, final int position) {
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String filname = Environment.getExternalStorageDirectory().toString() + "/.thugApp/" + userArrayList.get(position).getName() + ".mp3";
                File f = new File(filname);
                if (f.exists()) {
                    Uri uri = Uri.parse(Environment.getExternalStorageDirectory().toString() + "/.thugApp/" + userArrayList.get(position).getName() + ".mp3");
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("audio/mp3");
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    try {
                        context.startActivity(waIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(context, "Please, install Whatsapp", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //Downloading
                    new DownloadFileFromURL(context, holder.share).execute(userArrayList.get(position).getMp3url(), userArrayList.get(position).getName());
                }
            }
        });
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filname = Environment.getExternalStorageDirectory().toString() + "/.thugApp/" + userArrayList.get(position).getName() + ".mp3";
                File f = new File(filname);
                if (f.exists()) {
                    try {
                        if (mpHolder != null) {
                            mpHolder.play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                            mpHolder.seekBar.setVisibility(View.INVISIBLE);
                        }
                        holder.seekBar.setProgress(0);
                        if (mp.isPlaying()) {
                            holder.seekBar.setVisibility(View.INVISIBLE);
                            mp.pause();
                            holder.play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                        } else {
                            // Reset mediaplayer
                            holder.seekBar.setVisibility(View.VISIBLE);
                            mp.reset();
                            mp.setDataSource(filname);//Write your location here
                            mp.prepare();
                            holder.seekBar.setMax(mp.getDuration());
                            mp.start();
                            holder.play.setImageResource(R.drawable.ic_pause_black_24dp);
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    if (mp != null) {
                                        int mCurrentPosition = mp.getCurrentPosition() / 1000;
                                        holder.seekBar.setProgress(mCurrentPosition);
                                    }
                                    mHandler.postDelayed(this, 1000);
                                }
                            };
                            holder.seekBar.postDelayed(runnable, 50);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mpHolder = holder;
                } else {
                    //Downloading
                    new DownloadFileFromURL(context, holder.play).execute(userArrayList.get(position).getMp3url(), userArrayList.get(position).getName());
                }
            }
        });
        holder.textView.setText(userArrayList.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<User> filterResults = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filterResults.addAll(userArrayListCopy);
            } else {
                String value = constraint.toString().toLowerCase().trim();

                for (User user : userArrayListCopy) {
                    if (user.getName().toLowerCase().contains(value)) {
                        filterResults.add(user);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterResults;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            userArrayList.clear();
            userArrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageButton play, share;
        TextView textView;
        ProgressBar progressBar;
        SeekBar seekBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //progressBar = itemView.findViewById(R.id.progress_bar);
            seekBar = itemView.findViewById(R.id.seekbar);
            play = itemView.findViewById(R.id.play_button);
            share = itemView.findViewById(R.id.share_button);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.content);
        }
    }
}
