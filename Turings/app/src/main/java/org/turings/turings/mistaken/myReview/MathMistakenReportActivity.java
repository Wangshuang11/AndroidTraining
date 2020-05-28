package org.turings.turings.mistaken.myReview;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.turings.turings.R;

import java.util.ArrayList;
import java.util.List;


public class MathMistakenReportActivity extends AppCompatActivity {
    private ImageView ivBack_ws;//返回
    private CustomOnclickListener listener;//事件监听器
    private PieChart mPieChart;//难度分析的饼状图
    private BarChart mBarChart;//掌握程度的柱状图
    private LineChart lineChart;//错误原因的折线图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_mistaken_report);

        //初始化控件
        initController();

        //绑定事件监听器
        registerListener();

        //形成难度分析的饼状图
        formDifficultyPieChart();

        //形成掌握程度柱状图
        formMasteryBarChart();

        //形成错误原因折线图
        formReasonLineChart();
    }

    //初始化控件
    private void initController() {
        ivBack_ws=findViewById(R.id.ivBack_ws);
        mPieChart = findViewById(R.id.mPieChart);
        mBarChart = findViewById(R.id.mBarChart);
        lineChart = findViewById(R.id.mLineChart);
    }

    //绑定事件
    private void registerListener() {
        listener = new CustomOnclickListener();
        ivBack_ws.setOnClickListener(listener);
    }

    //形成饼状图
    private void formDifficultyPieChart(){
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(50f);
        mPieChart.setTransparentCircleRadius(60f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(20, "未标记"));
        entries.add(new PieEntry(10, "易"));
        entries.add(new PieEntry(30, "中等"));
        entries.add(new PieEntry(35, "较难"));
        entries.add(new PieEntry(5, "难"));

        //设置数据
        setData(entries);
        mPieChart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(12f);
        mPieChart.animateXY(1400, 1400);
        mPieChart.spin(1000, mPieChart.getRotationAngle(), mPieChart.getRotationAngle() + 360, Easing.EaseInCubic);
    }

    //(饼状图)设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //不显示百分比
        for (IDataSet<?> set : mPieChart.getData().getDataSets()){
            set.setDrawValues(!set.isDrawValuesEnabled());
        }
        //刷新
        mPieChart.invalidate();
    }

    //形成掌握程度条形图
    private void formMasteryBarChart(){
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getDescription().setEnabled(false);
        //不显示表格颜色
        mBarChart.setDrawGridBackground(false);
        //不显示图例
        mBarChart.getLegend().setEnabled(false);
        //隐藏右侧Y轴
        mBarChart.getAxisRight().setEnabled(false);

        //X轴设置
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        //设置x轴上的标签个数
        xAxis.setLabelCount(4);
        // x轴上标签的大小
        xAxis.setTextSize(10f);
        final String labelName[] = {"未标记", "未掌握", "已掌握", "熟练"};
        // 设置x轴显示的值的格式
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if ((int) value < labelName.length) {
                    return labelName[(int) value];
                } else {
                    return "";
                }
            }
        });
        //设置标签对x轴的偏移量，垂直方向
        xAxis.setYOffset(4);
        xAxis.setTextColor(Color.parseColor("#8F8E94"));

        //Y轴设置:自动设置数据
        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setLabelCount(6, true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(15f);
//        leftAxis.setAxisMinimum(0f);
//        leftAxis.setAxisMaximum(50f);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setTextColor(Color.parseColor("#8F8E94"));


        //模拟数据
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        yVals1.add(new BarEntry(0,3));
        yVals1.add(new BarEntry(1,8));
        yVals1.add(new BarEntry(2,2));
        yVals1.add(new BarEntry(3,6));
        setData1(yVals1);

        //XY轴动画
        mBarChart.animateXY(3000, 3000);
    }

    //(掌握程度条形图)设置数据
    private void setData1(ArrayList yVals1) {
        BarDataSet set1;
        if (mBarChart.getData() != null && mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, null);
            //设置柱子颜色(JOYFUL_COLORS也可)
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.5f);
            //设置数据
            mBarChart.setData(data);
        }
    }

    //形成错误原因折线图
    private void formReasonLineChart(){
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        // 取消缩放
        lineChart.setScaleEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        //隐藏右侧Y轴
        lineChart.getAxisRight().setEnabled(false);
        //不显示表格颜色
        lineChart.setDrawGridBackground(false);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        // 强制显示Y轴6个坐标
        yAxisLeft.setLabelCount(6, true);
        yAxisLeft.setSpaceTop(15f);
//        yAxisLeft.setAxisMinimum(0f);
//        yAxisLeft.setAxisMaximum(50f);
        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setTextColor(Color.parseColor("#8F8E94"));

        // 设置x轴的数据
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.parseColor("#8F8E94"));
        xAxis.setTextSize(10);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(5);
        // x轴上标签的大小
        xAxis.setTextSize(10f);
        final String labelName[] = {"未标记", "概念模糊", "思路错误", "运算错误","审题错误"};
        // 设置x轴显示的值的格式
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if ((int) value < labelName.length) {
                    return labelName[(int) value];
                } else {
                    return "";
                }
            }
        });
        //设置标签对x轴的偏移量，垂直方向
        xAxis.setYOffset(4);
        //x轴最左多出空n个坐标
        xAxis.setSpaceMax(0.7f);
        // 让左侧x轴不从0点开始
        xAxis.setSpaceMin(0.7f);

        // 获取的坐标数据集合
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0,3));
        entries.add(new Entry(1,7));
        entries.add(new Entry(2,5));
        entries.add(new Entry(3,8));
        entries.add(new Entry(4,2));
        //设置数据
        setData2(entries);

        //XY轴动画
        lineChart.animateY(3000);

    }

    //(错误原因折线图)设置数据
    private void setData2(List<Entry> entries) {
        // 创建数据的包装类
        LineDataSet lineDataSet = new LineDataSet(entries, null);
        lineDataSet.setDrawHighlightIndicators(true);
        // 设置折线的颜色
        lineDataSet.setColor(Color.parseColor("#FC863E"));
        // 填充颜色(渐变色)
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillDrawable(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{Color.parseColor("#31FF5A00"), Color.parseColor("#00FA5544")}));
        lineDataSet.setLineWidth(2f);
        lineDataSet.setDrawValues(true);
        lineDataSet.setValueTextSize(10f);
        LineData data = new LineData(lineDataSet);
        lineChart.setData(data);
    }

    //点击事件
    public class CustomOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ivBack_ws://点击返回
                    Intent intent = new Intent(getApplicationContext(), MyReviewActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }
}
