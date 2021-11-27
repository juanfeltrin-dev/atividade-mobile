package com.br.menu.Service.Request;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.br.menu.Model.ItemModel;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class StoreImageRequestService {
    private ItemModel item;
    private final Context context;

    public StoreImageRequestService(ItemModel item, Context context) {
        this.item = item;
        this.context = context;
    }

    public void get() {
        byte[] content = null;

        try {
            URL url = new URL(this.item.getPhotoUrl());
            HttpURLConnection con = null;
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            InputStream is = con.getInputStream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = is.read(buffer, 0, buffer.length)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            content = os.toByteArray();

            Bitmap bitmap = BitmapFactory.decodeByteArray(content , 0, content.length);
            this.item.setPhotoBitmap(bitmap);
        } catch(Exception e) {
            e.printStackTrace();
        }

        this.store(content);
    }

    public void store(byte[] result) {
        File f = new File(
        this.context.getExternalFilesDir(null).getAbsolutePath() +
                "/" +
                this.item.getId() + ".png"
        );

        try {
            FileOutputStream fos = new FileOutputStream(f);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.write(result);
            dos.close();
            fos.close();

            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);
            this.item.setPhoto(f.getAbsolutePath());
            Log.i("File on disk size:", String.valueOf(dis.available()));
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
