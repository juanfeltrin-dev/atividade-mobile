package com.br.menu.Service.Request;

import android.content.Context;

import com.br.menu.Model.ItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Json implements ContentType {
    @Override
    public ArrayList<ItemModel> handle(Context context) throws JSONException {
        String content = RequestService.get("http://172.31.160.1/api.json");
        ArrayList<ItemModel> items = new ArrayList<>();
        JSONArray jsonArray = null;

        jsonArray = new JSONArray(content);

        for (int i = 0; i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            ItemModel item = new ItemModel();
            item.setId(jsonObject.getInt("id"));
            item.setPhotoUrl(jsonObject.getString("photo"));
            item.setTitle(jsonObject.getString("title"));
            item.setDescription(jsonObject.getString("description"));
            item.setGluten(jsonObject.getBoolean("gluten"));
            item.setCalorie(jsonObject.getInt("calorie"));
            item.setPrice(jsonObject.getString("price"));

            StoreImageRequestService storeImageRequestService = new StoreImageRequestService(
                item,
                context
            );

            storeImageRequestService.get();

            items.add(item);
        }

        return items;
    }
}
