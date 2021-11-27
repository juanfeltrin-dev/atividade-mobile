package com.br.menu.Service.Request;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.br.menu.Adapter.ItemAdapter;
import com.br.menu.Model.ItemModel;
import com.br.menu.Service.SQLite.SQLiteService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RequestService extends AsyncTask<ContentType, Void, ArrayList<ItemModel>> {
    private final RecyclerView recyclerView;
    private final AppCompatActivity activity;
    private final ProgressDialog progressDialog;
    private final Context context;

    public RequestService(
            RecyclerView recyclerView,
            AppCompatActivity activity,
            ProgressDialog progressDialog,
            Context context
    ) {
        this.recyclerView = recyclerView;
        this.activity = activity;
        this.progressDialog = progressDialog;
        this.context = context;
    }

    public static String get(String uri) {
        BufferedReader bufferReader = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection  httpURLConnection = (HttpURLConnection) url.openConnection();

            StringBuilder stringBuilder = new StringBuilder();
            bufferReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String linha;

            while((linha = bufferReader.readLine()) != null){
                stringBuilder.append(linha+"\n");
            }

            return stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();

            return null;
        } finally {
            if(bufferReader != null){
                try {
                    bufferReader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected ArrayList<ItemModel> doInBackground(ContentType[] contentType) {
        try {
            return contentType[0].handle(this.context);
        } catch (Exception exception) {
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(ArrayList<ItemModel> items){
        SQLiteService sqLiteService = new SQLiteService(this.activity);

        if (items.isEmpty()) {
            items = sqLiteService.all();
        } else {
            sqLiteService.insert(items);
        }

        ItemAdapter itemAdapter = new ItemAdapter(items, true);

        this.recyclerView.setAdapter(itemAdapter);
        progressDialog.hide();
    }
}
