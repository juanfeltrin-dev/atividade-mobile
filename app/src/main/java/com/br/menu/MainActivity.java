package com.br.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.br.menu.Adapter.ItemAdapter;
import com.br.menu.Model.ItemModel;
import com.br.menu.Service.Network.NetworkService;
import com.br.menu.Service.Request.Json;
import com.br.menu.Service.Request.RequestService;
import com.br.menu.Service.Request.Xml;
import com.br.menu.Service.SQLite.SQLiteService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewItem = findViewById(R.id.recyclerView_items);
        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));

        ProgressDialog progressDialog = ProgressDialog.show(
            this,
            "Carregando",
            "Aguarde..."
        );

        if (NetworkService.isConnected(this)) {
            RequestService request = new RequestService(
                    recyclerViewItem,
                    this,
                    progressDialog,
                    getApplicationContext()
            );

            request.execute(new Json());
        } else {
            SQLiteService sqLiteService = new SQLiteService(this);

            ArrayList<ItemModel> items = sqLiteService.all();

            ItemAdapter itemAdapter = new ItemAdapter(items, false);

            recyclerViewItem.setAdapter(itemAdapter);
            progressDialog.hide();
        }
    }
}