package neua_th.qcar.rmutl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    AlertDialog.Builder dialogBuilder;
    View layoutView;
    AlertDialog alertDialog;
    Button getout;
    TextView content;



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


        dialogBuilder = new AlertDialog.Builder(profile.this);

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


                                    if (result!=null){
                                        String member_name=result.get("member_name").getAsString();
                                        mname.setText(member_name);

                                        layoutView = getLayoutInflater().inflate(R.layout.dialog_success, null);
                                        getout=layoutView.findViewById(R.id.getout);
                                        content =layoutView.findViewById(R.id.contact);
                                        dialogBuilder.setView(layoutView);
                                        alertDialog = dialogBuilder.create();
                                        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        alertDialog.show();
                                        getout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alertDialog.dismiss();

                                            }
                                        });

                                    }
                                    else {
                                        layoutView = getLayoutInflater().inflate(R.layout.dialog_fail, null);
                                        getout=layoutView.findViewById(R.id.getout);
                                        content =layoutView.findViewById(R.id.contact);
                                        dialogBuilder.setView(layoutView);
                                        alertDialog = dialogBuilder.create();
                                        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        alertDialog.show();
                                        getout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alertDialog.dismiss();

                                            }
                                        });
                                    }
                                }
                            });
                }
            });

            savecar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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

                                    switch (result){
                                       case "ok":
                                           layoutView = getLayoutInflater().inflate(R.layout.dialog_success, null);
                                           getout=layoutView.findViewById(R.id.getout);
                                           content =layoutView.findViewById(R.id.contact);
                                           dialogBuilder.setView(layoutView);
                                           alertDialog = dialogBuilder.create();
                                           alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                           alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                           alertDialog.show();
                                           getout.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   alertDialog.dismiss();

                                               }
                                           });
                                        break;
                                        default:
                                            layoutView = getLayoutInflater().inflate(R.layout.dialog_fail, null);
                                            getout=layoutView.findViewById(R.id.getout);
                                            content =layoutView.findViewById(R.id.contact);
                                            dialogBuilder.setView(layoutView);
                                            alertDialog = dialogBuilder.create();
                                            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            alertDialog.show();
                                            getout.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    alertDialog.dismiss();

                                                }
                                            });
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


