package org.turings.turings.index.gw.CharRobort;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.turings.turings.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CharRobort extends Activity implements View.OnClickListener {

    private Button sendButton;
    private EditText message;
    private ListView listView;
    private List<dataTransfer> data = new ArrayList<dataTransfer>();
    private ListViewAdapter dataAdapter;													//数据适配器

    private String userStr;
    private String robotStr;

    private RobotMessage rM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gw_activity_char_robort);

        initView();

        dataAdapter = new ListViewAdapter(this, data);
        listView.setAdapter(dataAdapter);
        listView.setSelection(listView.getCount() - 1);

        updateRobotData();
    }

    static Handler dataHandler;

    public void updateRobotData()
    {
        dataHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                if (what == 3) {
                    if(robotStr.length() > 0)
                    {
                        dataTransfer data0 = new dataTransfer();
                        data0.setWord(robotStr);
                        data0.setName("小T");
                        data0.setRobotUser(0);
                        data.add(data0);

                        dataAdapter.notifyDataSetChanged();

                        listView.setSelection(listView.getCount() - 1);
                    }
                }
                super.handleMessage(msg);
            }

        };
    }

    public void initView()
    {
        sendButton = (Button) findViewById(R.id.send);
        sendButton.setOnClickListener(this);
        message = (EditText) findViewById(R.id.et_sendmessage);
        listView = (ListView) findViewById(R.id.listView1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

        switch(arg0.getId())
        {
            case R.id.send:
                try {
                    send();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
        }
    }

    public void robotSend() throws IOException
    {
        rM = new RobotMessage();
        rM.main(userStr);
        robotStr = rM.getString();

        Message msg1 = new Message();
        msg1 = Message.obtain(this.dataHandler, 3);
        this.dataHandler.sendMessage(msg1);
    }

    public void send() throws IOException
    {
        userStr = message.getText().toString();
        if(userStr.length() > 0)
        {
            dataTransfer data0 = new dataTransfer();
            data0.setWord(userStr);
            data0.setName("pig");
            data0.setRobotUser(1);
            data.add(data0);

            dataAdapter.notifyDataSetChanged();
            message.setText("");

            listView.setSelection(listView.getCount() - 1);
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    robotSend();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
