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

public class Carmember extends AppCompatActivity {
    String url;
    String urllistcarmember;
    int cid;
    String mid;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carmember);
        url =getString(R.string.url);
        urllistcarmember = getString(R.string.listcarmember);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            cid = bundle.getInt("carcareid");
            mid = bundle.getString("mid");
        }
        Ion.with(Carmember.this)
                .load(url+urllistcarmember)
                .setBodyParameter("id",mid)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (result!=null){
                            ArrayList<CMCustomITem> itemArray=new ArrayList<>();
                            if (result!=null){
                                int len =result.size();
                                for (int i=0;i<len;i++){
                                    JsonObject item = (JsonObject)result.get(i);
                                    int id = item.get("id").getAsInt();
                                    String name = item.get("sername").getAsString();
                                    String color = item.get("color").getAsString();
                                    String carnumber = item.get("carnumber").getAsString();
                                    String brand = item.get("brand").getAsString();
                                    itemArray.add(new CMCustomITem(id,name,color,carnumber,brand));

                                    CMCustomAdapter adapter = new CMCustomAdapter(getBaseContext(),itemArray);
                                    listView=(ListView)findViewById(R.id.listView2);
                                    listView.setAdapter(adapter);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            CMCustomITem item = itemArray.get(position);
                                            int carmemberid =item.mid;
                                            Intent intent = new Intent(Carmember.this,listproduct.class);
                                            intent.putExtra("carcareid", cid);
                                            intent.putExtra("carmemberid", carmemberid);
                                            intent.putExtra("mid",mid);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }
                        }

                    }
                });

    }
}