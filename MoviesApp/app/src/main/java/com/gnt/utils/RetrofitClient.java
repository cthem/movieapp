package com.gnt.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.cert.CertificateException;
import java.time.LocalDate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by sissy on 12/5/17.
 */

public class RetrofitClient {
    public static final String BASE_URL = "https://192.168.1.2:8080/Gnt-Movies/rest/";

    private static Retrofit retrofit = null;
    private static Retrofit stringRetrofit = null;

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {

                @Override
                public LocalDate deserialize(JsonElement json, Type type,
                                             JsonDeserializationContext jsonDeserializationContext) {
                    String s = json.getAsJsonPrimitive().getAsString();
                    if (s.length() > 9)
                        return LocalDate.parse(s);
                    else
                        return null;
                }

            }).create();

    /** RestClient for HTTP GET requests, where token is provided for each call **/
    public static Retrofit getClient(String token) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getUnsafeOkHttpClient(token))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
    /** RestClient used when user signs in the app **/
    public static Retrofit getStringClient() {
        if (stringRetrofit==null) {
            stringRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getUnsafeOkHttpClient(null))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return stringRetrofit;
    }

    /** OkHttp **/
    public static OkHttpClient getUnsafeOkHttpClient(final String token) {
        try {
            /** Create a trust manager that does not validate certificate chains **/
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException
                        {

                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException
                        {

                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            /*Add authentication header*/
            if (token != null) {
                builder.addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request newRequest = chain.request().newBuilder()
                                        .addHeader("Authorization", token)
                                        .build();
                                return chain.proceed(newRequest);
                            }
                        }
                );
            }

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
