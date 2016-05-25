package com.example.yys.music;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.yys.music.bean.Post;
import com.example.yys.music.core.Utils;
import com.example.yys.music.core.ZhuanLanApi;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by xuebin on 15/10/15.
 */
public class StoryActivity extends Activity {

    public static final String KEY_POST = "_post";
    public static final String ENCODING_UTF_8 = "UTF-8";
    public static final String MIME_TYPE = "text/html";

    @BindView(R.id.web_view) WebView mWebView;
    @BindView(R.id.iv_avatar) SimpleDraweeView mAvatarView;
    @BindView(R.id.iv_article_header) SimpleDraweeView headerImageView;
    @BindView(R.id.tv_title) TextView titleView;
    @BindView(R.id.tv_author) TextView authorTextView;

    private Post mPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.bind(this);
        mPost = getIntent().getParcelableExtra(KEY_POST);
        if (mPost == null) {
            finish();
            return;
        }
        setTitle(mPost.getTitle());

         if (mPost == null) {
           finish();
            return;
        }
        mWebView.setWebViewClient(new WebViewClient());

 //        mWebView.loadDataWithBaseURL(null, CSS_STYLE + mPost.getContent(), MIME_TYPE, ENCODING_UTF_8, null);
        setStory();



    }



    private void setStory() {
        loadHtmlContent(mPost.getContent());
        titleView.setText(mPost.getTitle());
        headerImageView.setImageURI(Uri.parse(mPost.getImageUrl()));
        authorTextView.setText(mPost.getAuthorName() + ", " + Utils.convertPublishTime(mPost.getPublishedTime()));

        String id = mPost.getAuthor().getAvatar().getId();
        String picUrl = Utils.getAuthorAvatarUrl(mPost.getAuthor().getAvatar().getTemplate(),
                id, ZhuanLanApi.PIC_SIZE_XS);
        mAvatarView.setImageURI(Uri.parse(picUrl));
    }


    private void loadHtmlContent(String section) {
         mWebView.loadDataWithBaseURL(null, section, MIME_TYPE, ENCODING_UTF_8, null);
    }




}
