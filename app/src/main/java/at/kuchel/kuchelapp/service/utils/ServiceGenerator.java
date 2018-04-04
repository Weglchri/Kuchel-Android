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

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static OkHttpClient.Builder httpTimeoutClient = new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS).readTimeout(3, TimeUnit.SECONDS);

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonHelper.customGson));

    private static Retrofit retrofit = builder.build();


    public static <S> S createService(Class<S> serviceClass, boolean withTimeout) {
        return createService(serviceClass, null, withTimeout);
    }

    public static <S> S createService(
            Class<S> serviceClass, String username, String password, boolean withTimeout) {
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken, withTimeout);
        }

        return createService(serviceClass, null, withTimeout);
    }

    private static <S> S createService(
            Class<S> serviceClass, final String authToken, final boolean withTimeout) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            OkHttpClient.Builder client = withTimeout ? httpTimeoutClient : httpClient;

            if (!client.interceptors().contains(interceptor)) {
                client.addInterceptor(interceptor);

                builder.client(client.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}
