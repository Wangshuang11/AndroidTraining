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
        GrideBook beauty;
        for (int i = 0; i < 8; i++) {
//            beauty = new Book("老人与海", "https://tse1-mm.cn.bing.net/th?id=OIP.DgnAyxjjaxlKiWZXD5wZjAHaHa&w=221&h=207&c=7&o=5&dpr=1.25&pid=1.7");
//            data.add(beauty);
            beauty = new GrideBook("你的孤独 虽败犹荣","https://tse4-mm.cn.bing.net/th?id=OIP.pQMszCMxsSx4frCb8BHwYAAAAA&w=161&h=214&c=7&o=5&dpr=1.25&pid=1.7");
            data.add(beauty);
        }
        beautyAdapter.notifyDataSetChanged();
    }

    private void refreshBooks() {
        GrideBook beauty;
        data.clear();
        for (int i = 0; i <8; i++) {
            beauty = new GrideBook("老人与海", "https://tse1-mm.cn.bing.net/th?id=OIP.DgnAyxjjaxlKiWZXD5wZjAHaHa&w=221&h=207&c=7&o=5&dpr=1.25&pid=1.7");
            data.add(beauty);
//            beauty = new Book("你的孤独 虽败犹荣","https://tse4-mm.cn.bing.net/th?id=OIP.pQMszCMxsSx4frCb8BHwYAAAAA&w=161&h=214&c=7&o=5&dpr=1.25&pid=1.7");
//            data.add(beauty);
        }
        beautyAdapter.notifyDataSetChanged();
    }

    /**
     * 生成一些数据添加到集合中
     */
    private void initData() {
        GrideBook beauty;
        for (int i = 0; i < 8; i++) {
            beauty = new GrideBook("人间词话", "https://tse2-mm.cn.bing.net/th?id=OIP.oNNTN0y5Nggx8_6_HyPvRwAAAA&w=203&h=203&c=7&o=5&dpr=1.25&pid=1.7");
            data.add(beauty);
//            beauty = new Book("老人与海", "https://tse1-mm.cn.bing.net/th?id=OIP.DgnAyxjjaxlKiWZXD5wZjAHaHa&w=221&h=207&c=7&o=5&dpr=1.25&pid=1.7");
//            data.add(beauty);
//            beauty = new Book("你的孤独 虽败犹荣","https://tse4-mm.cn.bing.net/th?id=OIP.pQMszCMxsSx4frCb8BHwYAAAAA&w=161&h=214&c=7&o=5&dpr=1.25&pid=1.7");
//            data.add(beauty);
        }
    }
}