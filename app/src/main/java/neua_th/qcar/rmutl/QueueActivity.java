package neua_th.qcar.rmutl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rmutl.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class QueueActivity extends AppCompatActivity {

    String url;
    String urllistcarcare;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        url= getString(R.string.url);
        urllistcarcare= getString(R.string.listcarcare);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            String mid = bundle.getString("member_id");
            Ion.with(QueueActivity.this)
                    .load(url+urllistcarcare)
                    .asJsonArray()
                   .setCallback(new FutureCallback<JsonArray>() {
                       @Override
                       public void onCompleted(Exception e, JsonArray result) {
                           ArrayList<QCustomITem> itemArray=new ArrayList<>();
                           if (result!=null){
                               int len =result.size();
                               for (int i=0;i<len;i++){
                                   JsonObject item = (JsonObject)result.get(i);
                                   int id = item.get("id").getAsInt();
                                   String name = item.get("name").getAsString();
                                   int num = item.get("num").getAsInt();
                                   int score = item.get("score").getAsInt();
                                   int alltime = item.get("alltime").getAsInt();
                                   itemArray.add(new QCustomITem(id,name,num,score,alltime));

                               }
                           }
                           QCustomAdapter adapter = new QCustomAdapter(getBaseContext(),itemArray);
                           listView=(ListView)findViewById(R.id.listView);
                           listView.setAdapter(adapter);

                           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                               @Override
                               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                   QCustomITem item = itemArray.get(position);
                                   int cid =item.mid;
                                   Intent intent = new Intent(QueueActivity.this, Carmember.class);
                                   intent.putExtra("carcareid", cid);
                                   intent.putExtra("mid", mid);
                                   startActivity(intent);
                               }
                           });


                       }
                   });
        }

    }

}