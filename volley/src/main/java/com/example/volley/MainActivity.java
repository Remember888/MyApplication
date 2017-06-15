package com.example.volley;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final String PULLXML = "http://176.121.33.54:9080/pull.xml";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sendRequestWithHttpClient();
    }

    private void sendRequestWithHttpClient() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Person> list1 = new ArrayList<>();
                    URL url = new URL(PULLXML);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setDoInput(true);
                    conn.connect();

                    int statusCode = conn.getResponseCode();
                    if (statusCode == 200) {
                        InputStream in = conn.getInputStream();
                        list1 = parseXMLWithPull(in);
                        Log.d("LAG", list1.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private List<Person> parseXMLWithPull(InputStream in) {
        List<Person> list = null;
        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            int eventType = parser.getEventType();
            Person person = null;


            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        list = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        if (name.equalsIgnoreCase("person")) {
                            person = new Person();
                            person.setId(new Integer(parser.getAttributeValue(null, "id")));
                        } else if (person != null) {
                            if (name.equalsIgnoreCase("name")) {
                                person.setName(parser.nextText());
                            } else if (name.equalsIgnoreCase("age")) {
                                person.setAgge(new Integer(parser.nextText()));
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equalsIgnoreCase("person") && person != null) {
                            list.add(person);
                            person = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
