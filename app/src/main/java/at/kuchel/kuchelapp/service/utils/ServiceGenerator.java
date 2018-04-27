package at.kuchel.kuchelapp.service.utils;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import at.kuchel.kuchelapp.converter.GsonHelper;
import at.kuchel.kuchelapp.interceptor.AuthenticationInterceptor;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static at.kuchel.kuchelapp.Constants.BASE_URL;

/**
 * Created by bernhard on 30.03.2018.
 */

public class ServiceGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(4, TimeUnit.SECONDS);

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonHelper.customGson));

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, String username, String password) {
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken);
        }
        return createService(serviceClass, null);
    }

    private static <S> S createService(
            Class<S> serviceClass, final String authToken) {

        OkHttpClient.Builder client = httpClient;

        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!client.interceptors().contains(interceptor)) {
                client.addInterceptor(interceptor);
            }
        }
        builder.client(client.build());
        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }
}
