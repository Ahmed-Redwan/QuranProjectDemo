package com.example.quranprojectdemo.fireBase;

import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PushNotification {

    final static private String keyServiceId = "AAAAkRwHS54:APA91bEn3p73H7TmuMHzCQwiqlBrtD99NnHYAytBSHL6iC0bjgTXIBosoES0Qg8u5p0SdSsW6ZKEbp611nSgH6iaqMQQ7Ih3HZQfRaCbu7XhaDy5S2Q9pRAncWut0J8qeiF9D9acgdwM";


    public static void sendNotification(final String token, final String title, final String body) {
        Log.d("sssss", token);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    URL url = new URL("https://fcm.googleapis.com/fcm/send");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setUseCaches(false);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization", "key=" + keyServiceId);
                    conn.setRequestProperty("Content-Type", "application/json");

                    JSONObject json = new JSONObject();

                    json.put("to", token);

                    JSONObject info = new JSONObject();
                    info.put("title", title);   // Notification title
                    info.put("body", body); // Notification body

                    json.put("notification", info);

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(json.toString());
                    wr.flush();
                    conn.getInputStream();

                } catch (Exception e) {
                    Log.d("Error", "" + e);
                }


            }
        }).start();

    }


}
