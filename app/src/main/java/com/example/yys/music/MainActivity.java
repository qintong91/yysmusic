package com.example.yys.music;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yys.music.bean.Post;
import com.example.yys.music.core.Api;
import com.example.yys.music.core.ZhuanLanApi;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mRecyclerAdapter;
     private boolean mIsFreshing;
    private boolean mIsLoadingMore;
    private static final int PAGE_SIZE = 10;
     List<Post> mList ;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         ButterKnife.bind(this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerAdapter = new MainRecyclerViewAdapter();
        mRecyclerview.setAdapter(mRecyclerAdapter);
        mRecyclerview.addItemDecoration(new DividerItemDecoration());
         mList = new ArrayList<Post>();
         refresh();
         mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 Log.d("aa", "invoke onRefresh...");
                 mIsFreshing = true;
                 refresh();
               /* Call<Articles> call = Net.getmApi().mainArticles(1, PAGE_SIZE);
                mIsFreshing = true;
                call.enqueue(new ArticleListCallback());*/
             }
         });

    }
    public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {
        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.music_item, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
             viewHolder.mImageView.setImageURI(Uri.parse(mList.get(position).getImageUrl()));
            viewHolder.mSongTextView.setText(mList.get(position).getAuthor().getName());
            viewHolder.mArtistsView.setText(mList.get(position).getTitle());

        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            //return mArticles == null ? 0:mArticles.ArticleList.size();
            return mList.size() ;
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            @BindView(R.id.image)
            SimpleDraweeView mImageView;
            @BindView(R.id.text_song)
            TextView mSongTextView;
            @BindView(R.id.text_artists)
            TextView mArtistsView;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, StoryActivity.class);
                i.putExtra(StoryActivity.KEY_POST, mList.get(getAdapterPosition()));
                startActivity(i);

            }
        }
    }
    private class DividerItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            c.drawColor(getResources().getColor(R.color.colorPrimary));
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            onDrawOver(c, parent);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, 8);
        }
    }
    private void setNetRequestFailure() {
        Toast.makeText(MainActivity.this, "网络请求失败", Toast.LENGTH_LONG).show();
        if (mIsFreshing) {
            mSwipeRefreshLayout.setRefreshing(false);
            mIsFreshing = false;
        }
        if (mIsLoadingMore)
            mIsLoadingMore = false;
    }
    private void refresh() {
        Api api = ZhuanLanApi.getZhuanlanApi();
        api.getPosts("limiao", ZhuanLanApi.DEFAULT_COUNT, 0).enqueue(new IfRefreshingCallback());
    }
    private final class IfRefreshingCallback implements Callback<List<Post>> {

        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            mList.addAll(response.body());
            mRecyclerAdapter.notifyDataSetChanged();
            if (mIsFreshing) {
                mSwipeRefreshLayout.setRefreshing(false);
                mIsFreshing = false;
            }
        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            setNetRequestFailure();
            Log.i("aa", t.getMessage());
            if (mIsFreshing) {
                mSwipeRefreshLayout.setRefreshing(false);
                mIsFreshing = false;
            }
        }
    }

}
