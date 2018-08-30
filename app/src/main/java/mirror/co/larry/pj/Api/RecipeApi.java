package mirror.co.larry.pj.Api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeApi {
    private static Retrofit retrofit=null;

    private static OkHttpClient buildClient(){

        return new OkHttpClient
                .Builder()
//                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .client(buildClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://d17h27t6h515a5.cloudfront.net")
                    .build();
        }

        return retrofit;
    }
}
