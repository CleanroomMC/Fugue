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

// Redirect for Mo'Bends, Use ConnectionHelperInternal
public class ConnectionHelper
{
    public static ConnectionHelper INSTANCE = new ConnectionHelper();
   
    private ConnectionHelper(){}

    public Gson getGson() {
        return ConnectionHelperInternal.INSTANCE.getGson();
    }

    public static <T> T sendGetRequest(URL url, Map<String, String> params, Class<T> responseClass) throws IOException, URISyntaxException, ParseException {
        return ConnectionHelperInternal.sendGetRequest(url, params, responseClass);
    }

    public static <T> T sendPostRequest(URL url, JsonObject body, Class<T> responseClass) throws IOException
    {
        return ConnectionHelperInternal.sendPostRequest(url, body, responseClass);
    }
}
