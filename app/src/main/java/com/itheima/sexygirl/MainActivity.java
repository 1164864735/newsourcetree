package com.itheima.sexygirl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private List<SexyGirlBean.NewslistBean> mDataList;

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
            mDataList = response.getNewslist();
            mListView.setAdapter(mBaseAdapter);
        }
    };

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    private BaseAdapter mBaseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if (view == null) {
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item_view, null);
                vh = new ViewHolder(view);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            SexyGirlBean.NewslistBean data = mDataList.get(i);
            //刷新标题
            vh.mTitle.setText(data.getTitle());
            //刷新图片
            Glide.with(MainActivity.this).load(data.getPicUrl())
                    .bitmapTransform(new CropCircleTransformation(MainActivity.this))
                    .into(vh.mImageView);
            return view;
        }
    };

    public class ViewHolder {

        @BindView(R.id.list_image)
        public ImageView mImageView;

        @BindView(R.id.list_title)
        public TextView mTitle;

        public ViewHolder(View root) {
            ButterKnife.bind(this, root);
        }

    }

}
