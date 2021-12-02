package com.br.menu.Service.Request;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.br.menu.Model.ItemModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Xml implements ContentType {
    @Override
    public ArrayList<ItemModel> handle(Context context) throws IOException, SAXException, ParserConfigurationException, RuntimeException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse("http://localhost/api.xml");
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("item");
        ArrayList<ItemModel> items = new ArrayList<>();

        for (int i = 0; i < nList.getLength(); i++) {
            NodeList childrenTags = nList.item(i).getChildNodes();
            int childrenTagsCount = childrenTags.getLength();
            ItemModel item = new ItemModel();

            for(byte j=0;j<childrenTagsCount;j++) {
                switch (childrenTags.item(j).getNodeName()) {
                    case "id":
                        item.setId(
                            Integer.parseInt(
                                childrenTags.item(j).getTextContent()
                            )
                        );
                        break;
                    case "photo":
                        item.setPhoto(
                            childrenTags.item(j).getTextContent()
                        );
                        break;
                    case "title":
                        item.setTitle(
                            childrenTags.item(j).getTextContent()
                        );
                        break;
                    case "description":
                        item.setDescription(
                            childrenTags.item(j).getTextContent()
                        );
                        break;
                    case "gluten":
                        item.setGluten(
                            Boolean.parseBoolean(
                                childrenTags.item(j).getTextContent()
                            )
                        );
                        break;
                    case "calorie":
                        item.setCalorie(
                            Integer.parseInt(
                                childrenTags.item(j).getTextContent()
                            )
                        );
                        break;
                    case "price":
                        item.setPrice(
                            childrenTags.item(j).getTextContent()
                        );
                        break;
                }
            }

            items.add(item);
        }

        return items;
    }
}