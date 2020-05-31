package org.turings.turings.near.Location;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rance.lvlibrary.ButtonData;
import com.rance.lvlibrary.ButtonEventListener;
import com.rance.lvlibrary.SectorMenuButton;

import org.turings.turings.R;
import org.turings.turings.near.comment.CommentActivity;
import org.turings.turings.near.comment.JumpActivity;

import java.util.ArrayList;
import java.util.List;

public class SharedThingsActivity extends AppCompatActivity {
    private ImageView btnBack_lyh;
    private TextView tvShareTitle;
    private TextView tvShareContent;
    private MyLinstener linstener;
    private Resources resources;
    private RelativeLayout rlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_things);

        resources = getResources();
        getViews();
        registFonts();
        initTopSectorMenuButton();
        registLinstener();
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String background = intent.getStringExtra("background");
        int id = resources.getIdentifier(background,"drawable",SharedThingsActivity.this.getPackageName());
        tvShareTitle.setText(title);
        tvShareContent.setText(content);
        rlContent.setBackgroundResource(id);
    }

    private void registFonts() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "FZSJ-LGHXTJW.TTF");
        tvShareContent.setTypeface(typeface);
    }

    private void registLinstener() {
        linstener = new MyLinstener();
        btnBack_lyh.setOnClickListener(linstener);

    }

    private void getViews() {
        btnBack_lyh = findViewById(R.id.btnBack_lyh);
        tvShareTitle = findViewById(R.id.tvShareTitle);
        tvShareContent = findViewById(R.id.tvShareContent);
        rlContent = findViewById(R.id.rlContent);
    }


    private void initTopSectorMenuButton() {
        SectorMenuButton sectorMenuButton = (SectorMenuButton) findViewById(R.id.top_sector_menu);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.mipmap.menu, R.mipmap.shoucang,
                R.mipmap.pinglun, R.mipmap.woyeyaoxie};
        for (int i = 0; i < 4; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            buttonData.setBackgroundColorId(this, R.color.colorPink);
            buttonDatas.add(buttonData);
        }
        sectorMenuButton.setButtonDatas(buttonDatas);
        setListener(sectorMenuButton);
    }

    private void setListener(final SectorMenuButton button) {
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                switch (index){
                    case 1:
                        showToast("收藏成功");
                        break;
                    case 2:
                        Intent intent1 = new Intent(SharedThingsActivity.this, CommentActivity.class);
                        startActivity(intent1);
                        break;
                    case 3:
                        Intent intent = new Intent(SharedThingsActivity.this, WriteActivity.class);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onExpand() {

            }

            @Override
            public void onCollapse() {
            }
        });
    }

    private void showToast(String text) {
        Toast.makeText(SharedThingsActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    class MyLinstener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case  R.id.btnBack_lyh:
                    finish();
                    break;
            }
        }
    }
}
