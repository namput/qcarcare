package neua_th.qcar.rmutl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rmutl.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {

    String url;
    String showcolor;
    String urlupdatename;
    String urlshowcar;
    String urlcarmember;
    String mnumcar;
    String mbrand;
    Spinner spinner;
    Spinner spinnercolor;
    ArrayList<String> carlist;
    ArrayList<CustomItem> color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        url= getString(R.string.url);
        showcolor=getString(R.string.showcolor);
        urlupdatename=getString(R.string.updatename);
        urlshowcar=getString(R.string.showcar);
        urlcarmember=getString(R.string.carmember);

        EditText mname=(EditText)findViewById(R.id.name);
        Button savename=(Button)findViewById(R.id.savename);
        EditText numcar=(EditText)findViewById(R.id.number);
        EditText brand=(EditText)findViewById(R.id.brand);
        Button savecar=(Button)findViewById(R.id.savecar);
       

        carlist =new ArrayList<>();
        spinner =findViewById(R.id.carsize);
        color =new ArrayList<>();
        spinnercolor =findViewById(R.id.color);
        loadSpinnerData();
        loadSpinnerData1();

//เช็คว่ามีการส่งค่ามาไหม
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            String mid = bundle.getString("member_id");
            String member_name = bundle.getString("member_name");
            if (member_name==null){
                member_name="";
            }

            mname.setText(member_name);

            savename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog dialog=new ProgressDialog(profile.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setMessage("รอสักครู่...");
                    dialog.setIndeterminate(true);
                    dialog.show();
                    String changname=mname.getText().toString();

                    Ion.with(profile.this)
                            .load(url+urlupdatename)
                            .setBodyParameter("id",mid)
                            .setBodyParameter("name",changname)
                            .setBodyParameter("type","3")
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    dialog.dismiss();
                                    if (result!=null){
                                        String member_name=result.get("member_name").getAsString();
                                        mname.setText(member_name);
                                        Toast.makeText(profile.this,"เปลี่ยนชื่อสำเสร็จ",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(profile.this,"ไม่สามารถเปลี่ยนชื่อได้",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            });
            savecar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog dialog=new ProgressDialog(profile.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setMessage("รอสักครู่...");
                    dialog.setIndeterminate(true);
                    dialog.show();
                    Object selectedItem = spinner.getSelectedItemId();
                    String selectedText = selectedItem.toString ();
                    mnumcar=numcar.getText().toString();
                    mbrand=brand.getText().toString();

                    Object selectedItemcolor = spinnercolor.getSelectedItemId();
                    String selectedTextcolor = selectedItemcolor.toString ();

                    Ion.with(profile.this)
                            .load(url+urlcarmember)
                            .setBodyParameter("id",mid)
                            .setBodyParameter("car_service",selectedText)
                            .setBodyParameter("color",selectedTextcolor)
                            .setBodyParameter("car_number",mnumcar)
                            .setBodyParameter("brand",mbrand)
                            .setBodyParameter("type","3")
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {
                                    if (result!=null){
                                        Toast.makeText(profile.this,"เพื่มรายการรถแล้ว",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(profile.this,"ไม่สามารถบันทึกรายการได้",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            });

        }
        else {
            Intent intent=new Intent(profile.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        }

    private void loadSpinnerData(){
        Ion.with(getApplicationContext())
                .load(url+urlshowcar)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        List<KeyValuePair> keyValuePairList = new ArrayList<>();
                        String name ="เลือกประเภทรถ";
                        keyValuePairList.add(new KeyValuePair("0", name));
                        for (int i=0;i<result.size();i++){
                            JsonObject jsObject=(JsonObject)result.get(i);
                            String k =jsObject.get("car_service_id").getAsString();
                            name =jsObject.get("car_service_name").getAsString();
                            keyValuePairList.add(new KeyValuePair(k, name));
                            //Toast.makeText(profile.this,""+arrayITem,Toast.LENGTH_LONG).show();
                        }
                        spinner.setAdapter(new ArrayAdapter<>(profile.this, android.R.layout.simple_spinner_dropdown_item,keyValuePairList));
                    }
                });
    }

    private void loadSpinnerData1(){
        Ion.with(getApplicationContext())
                .load(url+showcolor)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        List<KeyValuePair> keyValuePairList = new ArrayList<>();
                        String name ="เลือกสีรถ";
                        keyValuePairList.add(new KeyValuePair("0", name));
                        for (int i=0;i<result.size();i++){
                            JsonObject jsObject=(JsonObject)result.get(i);
                            String k =jsObject.get("color_id").getAsString();
                            name =jsObject.get("color_name").getAsString();
                            keyValuePairList.add(new KeyValuePair(k, name));
                        }

                        spinnercolor.setAdapter(new ArrayAdapter<>(profile.this, android.R.layout.simple_spinner_dropdown_item,keyValuePairList));
                    }
                });
    }
}

class KeyValuePair {
    private String key;
    private String value;
    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }
}


