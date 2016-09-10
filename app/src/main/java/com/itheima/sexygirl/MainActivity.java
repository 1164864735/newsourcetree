package com.itheima.sexygirl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.list_view)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadDatafromServer();
    }

    private void loadDatafromServer() {
        String url = "http://apis.baidu.com/txapi/mvtp/meinv?num=30";
        SexyGirlRequest<SexyGirlBean> request = new SexyGirlRequest<>(url, SexyGirlBean.class, mSexyGirlBeanListener, mErrorListener );
        NetworkManager.sendRequest(request);
    }

    private Response.Listener<SexyGirlBean> mSexyGirlBeanListener = new Response.Listener<SexyGirlBean>() {
        @Override
        public void onResponse(SexyGirlBean response) {
            Log.d(TAG, "onResponse: " + response.getNewslist().get(0).getTitle());
        }
    };

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

}
