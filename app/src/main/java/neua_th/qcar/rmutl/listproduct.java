  package neua_th.qcar.rmutl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rmutl.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

 public class listproduct extends AppCompatActivity {
     String url;
     String urlattribute;
     ListView listView;
     Button coform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listproduct);
        url =getString(R.string.url);
        urlattribute = getString(R.string.attribute);
        coform=(Button)findViewById(R.id.coform);
        Bundle bundle = getIntent().getExtras();
        if (bundle !=null){
            String carcareid = bundle.get("carcareid").toString();
            String carmemberid = bundle.get("carmemberid").toString();
            String memberid = bundle.getString("mid");

            Ion.with(listproduct.this)
                    .load(url+urlattribute)
                    .setBodyParameter("id",memberid)
                    .setBodyParameter("cmid",carmemberid)
                    .setBodyParameter("cid",carcareid)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {
                            ArrayList<AtCustomITem> itemArray=new ArrayList<>();
                            if (result!=null){
                                int len =result.size();
                                for (int i=0;i<len;i++){
                                    JsonObject item = (JsonObject)result.get(i);
                                    int aid = item.get("attribute_id").getAsInt();
                                    String name = item.get("attribute_name").getAsString();
                                    String size = item.get("size").getAsString();
                                    int time = item.get("time").getAsInt();
                                    itemArray.add(new AtCustomITem(aid,name,size,time,false));
//                                    Toast.makeText(listproduct.this,""+itemArray,Toast.LENGTH_SHORT).show();
                                }
                                AtCustomAdapter adapter = new AtCustomAdapter(getBaseContext(),itemArray);
                                listView=(ListView)findViewById(R.id.listView3);
                                listView.setAdapter(adapter);
                                coform.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ArrayList<Integer> gid=new ArrayList<>();
                                        for (int i=0;i<itemArray.size();i++){
                                            AtCustomITem item =itemArray.get(i);
                                            if (item.isChecked==true){
                                                gid.add(item.id);
                                            }
                                        }
                                        
                                        Toast.makeText(listproduct.this,""+gid,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    });


        }
    }
}