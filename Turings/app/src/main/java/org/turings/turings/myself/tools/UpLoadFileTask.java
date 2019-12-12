package org.turings.turings.myself.tools;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpLoadFileTask extends AsyncTask<String, Void, String> {
    private Context context;
    private String filePath;

    public UpLoadFileTask(Context context, String filePath) {
        this.context = context;
        this.filePath = filePath;
    }

    /**
     * 访问服务器，上传pdf文件，接收响应并返回
     */
    @Override
    protected String doInBackground(String... strings) {
        //1. 创建OKHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //2. 创建Request对象
        //2.1 得到pdf文件对应的mime类型
        MediaType type = MediaType.parse("image/jpeg");
        //2.2 创建RequestBody的对象
        File file = new File(filePath);
        RequestBody body = RequestBody.create(type, file);
            //2.3 创建请求对象
            Request request = new Request.Builder()
                    .url(strings[0])
                    .post(body)
                    .build();
            //3. 创建Call对象
            Call call = client.newCall(request);
            //4. 发起请求并接收响应
            Response response = null;
            try {
            response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
//        super.onPostExecute(s);


        Log.e("返回数据地址", "s");
        Toast.makeText(
                context,
                s,
                Toast.LENGTH_SHORT
        ).show();
    }
}
