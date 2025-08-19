package com.cleanroommc.fugue.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import goblinbob.mobends.core.asset.AssetLocation;
import goblinbob.mobends.core.supporters.BindPoint;
import goblinbob.mobends.core.util.Color;
import goblinbob.mobends.core.util.ColorAdapter;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;
import java.net.MalformedURLException;
import java.net.URI;

public class ConnectionHelper
{
    public static ConnectionHelper INSTANCE = new ConnectionHelper();
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final Gson gson;

    /**
     * Makes it so we can't instantiate this class.
     */
    private ConnectionHelper()
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(Color.class, new ColorAdapter());
        builder.registerTypeAdapter(BindPoint.class, new BindPoint.Adapter());
        builder.registerTypeAdapter(AssetLocation.class, new AssetLocation.Adapter());
        this.gson = builder.create();
    }

    public Gson getGson()
    {
        return gson;
    }

    public static String sendGetRequest(String url, String... args) throws IOException, URISyntaxException, ParseException, throws MalformedURLException, URISyntaxException 
    {
        HashMap<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; i = i + 2) {
            str.put(args[i], args[i+1]);
        }
        return sendGetRequest(url, params);
    }

    public static String sendGetRequest(String url, Map<String, String> params) throws IOException, URISyntaxException, ParseException, throws MalformedURLException, URISyntaxException 
    {
        return sendGetRequest(new URI(url).toURL(), params);
    }

    public static String sendGetRequest(URL url, Map<String, String> params) throws IOException, URISyntaxException, ParseException
    {
        URIBuilder uriBuilder = new URIBuilder(url.toURI());
        for (Map.Entry<String, String> entry : params.entrySet())
        {
            uriBuilder.addParameter(entry.getKey(), entry.getValue());
        }

        HttpGet request = new HttpGet(uriBuilder.build());

        try (CloseableHttpResponse response = INSTANCE.httpClient.execute(request))
        {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // return it as a String
                return EntityUtils.toString(entity);
            }
        }

        return null;
    }

    public static <T> T sendGetRequest(URL url, Map<String, String> params, Class<T> responseClass) throws IOException, URISyntaxException, ParseException {
        String result = sendGetRequest(url, params);
        if (result != null) {
            return INSTANCE.gson.fromJson(EntityUtils.toString(entity), responseClass);
        } else return null;
    }

    public static <T> T sendPostRequest(URL url, JsonObject body, Class<T> responseClass) throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        byte[] out = (new Gson()).toJson(body).getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        connection.setFixedLengthStreamingMode(length);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.connect();

        try (OutputStream os = connection.getOutputStream())
        {
            os.write(out);
        }

        // Response
        BufferedReader json = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        return INSTANCE.gson.fromJson(json, responseClass);
    }
}
