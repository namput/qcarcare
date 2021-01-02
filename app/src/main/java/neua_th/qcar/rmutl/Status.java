package neua_th.qcar.rmutl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmutl.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Status extends AppCompatActivity {
    String morderqueue;
    String mstatus;
    int mqueue;
    int mprogress;
    int mtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Button button_send =(Button)findViewById(R.id.button_send);
        String url = getString(R.string.url);
        String urlqueue = getString(R.string.queue);
        JsonArray itemArray = new JsonArray();
        TextView orderqueue = (TextView)findViewById(R.id.qorder);
        TextView status = (TextView)findViewById(R.id.status);
        TextView queue = (TextView)findViewById(R.id.queue);
        TextView progress = (TextView)findViewById(R.id.progress);
        TextView time = (TextView)findViewById(R.id.time);

        Date date = new Date();
        String stringDate = DateFormat.getDateInstance().format(date);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            String id = bundle.getString("id");
            String cmid = bundle.getString("cmid");
            String cid = bundle.getString("cid");
            ArrayList<Integer> attrid =bundle.getIntegerArrayList("getdata");


            JsonObject jsonoject = new JsonObject();
            jsonoject.addProperty("id",id);
            jsonoject.addProperty("cmid",cmid);
            jsonoject.addProperty("cid",cid);
            jsonoject.addProperty("createdate",stringDate);

            itemArray.add(jsonoject);
            for (int i=0;i<attrid.size();i++){
                JsonObject jsObject = new JsonObject();
                jsObject.addProperty("attribute",attrid.get(i));
                itemArray.add(jsObject);

            }

            Ion.with(Status.this)
                    .load(url+urlqueue)
                    .setJsonArrayBody(itemArray)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {
                            if (result!=null){
                                JsonObject item = (JsonObject)result.get(0);
                                morderqueue = item.get("queue_id").getAsString();
                                mstatus = item.get("status_name").getAsString();
                                mqueue = item.get("queue").getAsInt();
                                mprogress = item.get("progress").getAsInt();
                                mtime = item.get("all_time").getAsInt();
                                String order= item.get("queue_order").getAsString();

                                status.setText("สถานะ "+mstatus);
                                orderqueue.setText("ลำดับคิว "+morderqueue);
                                queue.setText("เหลือ "+mqueue+" คิว");
                                progress.setText("กำลังดำเนินการ "+mprogress+" รายการ");
                                time.setText("เวลาที่ใช้ในการล้างรถโดยประมาณ "+mtime+" นาที");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Status.this, "จองคิวแล้ว",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Status.this,Menu.class);
                                        intent.putExtra("member_id",id);
                                        finish();
                                        startActivity(intent);
                                        finish();
                                    }
                                },1);
                            }

                        }
                    });
        }

}

    }
