package com.example.imageswitch;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imageswitch.weight.ImageWatcher;
import com.example.imageswitch.weight.ImageWatcherHelper;
import com.example.imageswitch.weight.MessagePicturesLayout;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MessagePicturesLayout picturesLayout;
    private ImageWatcherHelper iwHelper;
    private String[] datas = {"https://t7.baidu.com/it/u=3616242789,1098670747&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1590312700&t=b678e23cbcea1d7f1bdad5a845e97703",
            "https://t8.baidu.com/it/u=1484500186,1503043093&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1590312700&t=c947f50c674f56dc5262802f72f25a63",
            "https://t7.baidu.com/it/u=3204887199,3790688592&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1590312700&t=9c59917657557c67108269d48f72f51e",
            "https://t9.baidu.com/it/u=1307125826,3433407105&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1590312700&t=9f8720505a0c724258a27668863c568e",
            "https://t9.baidu.com/it/u=4169540006,4220376401&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1590312700&t=b06ce05b59aaf0674844a10709d1dca4",
            "https://t8.baidu.com/it/u=3064799386,2095288843&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1590312700&t=8fea8bd52df063ac3a14e95242dd9c93",
            "https://t7.baidu.com/it/u=2704272957,1194893808&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1590312700&t=ad73709d1359083b0e1b46d131076173",
            "https://t7.baidu.com/it/u=2336214222,3541748819&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1590312700&t=b7ac048b81106e3230467c5c27544be6",
            "https://t9.baidu.com/it/u=2236363868,3488383685&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1590312700&t=557bba8337640704cc81cf9d75434a16"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TAG","onCreate");
        picturesLayout = findViewById(R.id.picture_layout);
        picturesLayout.setCallback(layoutCallback);
        initImage();
        initData();
    }

    private MessagePicturesLayout.Callback layoutCallback = new MessagePicturesLayout.Callback() {
        @Override
        public void onThumbPictureClick(ImageView i, SparseArray<ImageView> imageGroupList, List<String> urlList) {
            iwHelper.show(i,imageGroupList,urlList);
        }
    };

    private void initImage(){
        iwHelper = ImageWatcherHelper.with(this, null) // 一般来讲， ImageWatcher 需要占据全屏的位置
//                .setTranslucentStatus(!isTranslucentStatus ? Utils.calcStatusBarHeight(this) : 0) // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
//                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
//                .setOnPictureLongPressListener(this)//长安监听，并不一定要调用这个API
                .setOnStateChangedListener(new ImageWatcher.OnStateChangedListener() {
                    @Override
                    public void onStateChangeUpdate(ImageWatcher imageWatcher, ImageView clicked, int position, String uri, float animatedValue, int actionTag) {
                        Log.e("IW", "onStateChangeUpdate [" + position + "][" + uri + "][" + animatedValue + "][" + actionTag + "]");
                    }

                    @Override
                    public void onStateChanged(ImageWatcher imageWatcher, int position, String uri, int actionTag) {
                        if (actionTag == ImageWatcher.STATE_ENTER_DISPLAYING) {
                            Toast.makeText(getApplicationContext(), "点击了图片 [" + position + "]" + uri + "", Toast.LENGTH_SHORT).show();
                        } else if (actionTag == ImageWatcher.STATE_EXIT_HIDING) {
                            Toast.makeText(getApplicationContext(), "退出了查看大图", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                .setIndexProvider(new CustomDotIndexProvider())//自定义页码指示器（默认数字），并不一定要调用这个API
//                .setLoadingUIProvider(new CustomLoadingUIProvider()); // 自定义LoadingUI，并不一定要调用这个API
    }

    private void initData(){
        picturesLayout.setData(Arrays.asList(datas));
    }
}
