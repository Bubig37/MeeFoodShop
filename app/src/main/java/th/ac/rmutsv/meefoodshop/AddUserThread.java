package th.ac.rmutsv.meefoodshop;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class AddUserThread extends AsyncTask<String, Void, String> {

    private Context context;

    public AddUserThread(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... strings) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();

//            Create Packet
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("Name", strings[0])
                    .add("Gender", strings[1])
                    .add("User", strings[2])
                    .add("Password", strings[3])
                    .build();

//            Create Request
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(strings[4]).post(requestBody).build();

//            Create Response
            Response response = okHttpClient.newCall(request).execute();
            return  response.body().string();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }



    }
}