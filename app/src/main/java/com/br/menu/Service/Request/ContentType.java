package com.br.menu.Service.Request;

import android.content.Context;

import com.br.menu.Model.ItemModel;

import org.json.JSONException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public interface ContentType {
    public ArrayList<ItemModel> handle(Context context) throws IOException, SAXException, ParserConfigurationException, JSONException;
}
