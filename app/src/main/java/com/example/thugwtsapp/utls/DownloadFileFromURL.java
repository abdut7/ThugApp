package com.example.thugwtsapp.utls;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.thugwtsapp.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Background Async Task to download file
 */
public class DownloadFileFromURL extends AsyncTask<String, String, String> {

    Context context;
    ImageButton play;

    public DownloadFileFromURL(Context context, ImageButton play) {
        this.context = context;
        this.play = play;
    }

    /**
     * Before starting background thread
     * Show Progress Bar Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        play.setImageResource(R.drawable.ic_file_download_black_24dp);
        play.setEnabled(false);
        Toast.makeText(context, "Downloading.........", Toast.LENGTH_LONG).show();
        Log.e("AsyncTask: ", "PRE");
    }

    /**
     * Downloading file in background thread
     */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            String root = Environment.getExternalStorageDirectory().toString() + "/.thugApp";
           //Create folder
            File f = new File(root);
            f.mkdir();
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream to write file

            OutputStream output = new FileOutputStream(root + "/" + f_url[1] + ".mp3");
            byte data[] = new byte[1024];

            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();
            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return null;
    }

    /**
     * Updating progress bar
     */
    protected void onProgressUpdate(String... progress) {
        Log.e("AsyncTask: ", "PROGRESS");
    }

    /**
     * After completing background task
     * Dismiss the progress dialog
     **/
    @Override
    protected void onPostExecute(String file_url) {
        play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        play.setEnabled(true);
        Toast.makeText(context, "Download Completed!!", Toast.LENGTH_SHORT).show();
        Log.e("AsyncTask: ", "POST");
    }

}
