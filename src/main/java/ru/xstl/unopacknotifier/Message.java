package ru.xstl.unopacknotifier;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Message {
   public final int id;
   public final HashMap<String, String> text;

    public Message(int id, HashMap<String, String> text) {
        this.id = id;
        this.text = text;
    }

    public static Message getMessage() throws IOException {
        URL url = new URL("https://storage.yandexcloud.net/unopack/notify/notify.json");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        Gson gson = new Gson();
        return gson.fromJson(String.valueOf(content), Message.class);
    }
}
