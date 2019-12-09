package org.turings.turings.index.gw.GrideBooks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.turings.turings.R;

import java.util.ArrayList;
import java.util.List;

public class GrideBooks extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<GrideBook> data = new ArrayList<>();
    private SmartRefreshLayout srl;
    private GrideBookAdapter beautyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gw_gride_books);

        srl = findViewById(R.id.booksrl);
        srl.setReboundDuration(1000);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //使用瀑布流布局,第一个参数 spanCount 列数,第二个参数 orentation 排列方向
        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        initData();
        beautyAdapter= new GrideBookAdapter(data, this);
        //设置adapter
        recyclerView.setAdapter(beautyAdapter);

        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshBooks();
                srl.finishRefresh();
                Toast.makeText(getApplicationContext(),"刷新完成",Toast.LENGTH_SHORT).show();
            }
        });

        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(data.size() > 20){
                    srl.finishLoadMoreWithNoMoreData();
                    Toast.makeText(getApplicationContext(),
                            "加载数据完毕",
                            Toast.LENGTH_SHORT).show();
                }else {
                    loadMoreBooks();
                    srl.finishLoadMore();
                    Toast.makeText(getApplicationContext(),
                            "加载5条数据",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void loadMoreBooks() {
        GrideBook beauty1;
        GrideBook beauty2;
        GrideBook beauty3;
        GrideBook beauty4;
        for (int i = 0; i < 2; i++) {
            beauty1 = new GrideBook("羊脂球","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3808118474,463269679&fm=26&gp=0.jpg");
            beauty2 = new GrideBook("羊脂球","https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2781711160,2537312162&fm=26&gp=0.jpg");
            beauty3 = new GrideBook("鬼谷子","https://tse3-mm.cn.bing.net/th?id=OIP.GS3ycUZFCONM5R49k8fSNwAAAA&w=179&h=193&c=7&o=5&dpr=1.25&pid=1.7");
            beauty4 = new GrideBook("巴黎圣母院","https://tse1-mm.cn.bing.net/th?id=OIP.2nTlLVoT_8cTM_3-JhCz-AAAAA&w=143&h=190&c=7&o=5&dpr=1.25&pid=1.7");
            data.add(beauty1);
            data.add(beauty2);
            data.add(beauty3);
            data.add(beauty4);
        }
        beautyAdapter.notifyDataSetChanged();
    }

    private void refreshBooks() {
        data.clear();
        GrideBook beauty1;
        GrideBook beauty2;
        GrideBook beauty3;
        GrideBook beauty4;
        GrideBook beauty;
        for (int i = 0; i < 2; i++) {
            beauty1 = new GrideBook("浮生六记", "http://img5.imgtn.bdimg.com/it/u=2271333855,1088329262&fm=26&gp=0.jpg");
            beauty2 = new GrideBook("巴黎圣母院", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2647296849,1766062156&fm=26&gp=0.jpg");
            beauty3 = new GrideBook("人间词话", "https://tse2-mm.cn.bing.net/th?id=OIP.oNNTN0y5Nggx8_6_HyPvRwAAAA&w=203&h=203&c=7&o=5&dpr=1.25&pid=1.7");
            beauty4 = new GrideBook("辞海", "https://tse3-mm.cn.bing.net/th?id=OIP.NriYLrrzC-wgnnhhyuGSSQAAAA&w=187&h=194&c=7&o=5&dpr=1.25&pid=1.7");
            data.add(beauty1);
            data.add(beauty2);
            data.add(beauty3);
            data.add(beauty4);
        }
        beautyAdapter.notifyDataSetChanged();
    }

    /**
     * 生成一些数据添加到集合中
     */
    private void initData() {
        GrideBook beauty1;
        GrideBook beauty2;
        GrideBook beauty3;
        GrideBook beauty4;
        GrideBook beauty;
        for (int i = 0; i < 2; i++) {
            beauty1 = new GrideBook("人间词话", "https://tse2-mm.cn.bing.net/th?id=OIP.oNNTN0y5Nggx8_6_HyPvRwAAAA&w=203&h=203&c=7&o=5&dpr=1.25&pid=1.7");
            beauty2 = new GrideBook("辞海", "https://tse3-mm.cn.bing.net/th?id=OIP.NriYLrrzC-wgnnhhyuGSSQAAAA&w=187&h=194&c=7&o=5&dpr=1.25&pid=1.7");
            beauty3 = new GrideBook("浮生六记", "http://img5.imgtn.bdimg.com/it/u=2271333855,1088329262&fm=26&gp=0.jpg");
            beauty4 = new GrideBook("巴黎圣母院", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2647296849,1766062156&fm=26&gp=0.jpg");
            data.add(beauty1);
            data.add(beauty2);
            data.add(beauty3);
            data.add(beauty4);
        }
    }
}