package neua_th.qcar.rmutl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rmutl.R;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private EditText phone;
    private EditText password;
    private Button login;
    private Button crecate;
    private String url;
    private String urllogin;
    private CheckBox remember;
    String mphone;
    String mpassword;
    boolean mremember;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        phone=(EditText)findViewById(R.id.phone);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        crecate=(Button)findViewById(R.id.create);
        remember = (CheckBox)findViewById(R.id.remember);
        bundle = getIntent().getExtras();
        if (bundle!=null){
            mPreferences.edit().clear().apply();
            Intent i = getBaseContext().getPackageManager().
                    getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }

        //ลิงค์เชื่อต่อ
        url=getString(R.string.url);
        urllogin=getString(R.string.login);
        final String urlcreate=getString(R.string.create);
        getPreference();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loginnow();
            }
        });
        crecate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog dialog=new ProgressDialog(MainActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("รอสักครู่...");
                dialog.setIndeterminate(true);
                dialog.show();
                Ion.with(MainActivity.this)
                        .load(url+urlcreate)
                        .setBodyParameter("phone",phone.getText().toString())
                        .setBodyParameter("pass",password.getText().toString())
                        .setBodyParameter("type","3")
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                dialog.dismiss();
                                if (result!=null){
                                    String member_id=result.get("member_id").getAsString();
                                    String member_phone=result.get("member_phone").getAsString();
                                    Intent intent=new Intent(MainActivity.this,profile.class);
                                    intent.putExtra("member_id",member_id);
                                    intent.putExtra("member_phone",member_phone);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(MainActivity.this,"ไม่สามารถเข้าสู่ระบบได้",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }
    private void putPreference(){

        String mphone=phone.getText().toString();
        String mpassword=password.getText().toString();
        boolean isChecked=remember.isChecked();
        SharedPreferences.Editor editor=mPreferences.edit();
        editor.putString("p_phone",mphone);
        editor.putString("p_pssword",mpassword);
        editor.putBoolean("p_save",isChecked);
        editor.apply();
        //Toast.makeText(getBaseContext(),"บันทึก"+editor,Toast.LENGTH_LONG).show();

    }
    private void getPreference(){

        if (mPreferences.contains("p_phone")){
            mphone = mPreferences.getString("p_phone",null);
            phone.setText(mphone);
        }
        if (mPreferences.contains("p_pssword")){
            mpassword = mPreferences.getString("p_pssword",null);
            password.setText(mpassword);
        }
        if (mPreferences.contains("p_save")){
            mremember = mPreferences.getBoolean("p_save",false);
            remember.setChecked(mremember);
        }
        if (mphone!=null){
            if (mpassword!=null){
                loginnow();
            }
        }

    }
    private void loginnow(){
        final ProgressDialog dialog=new ProgressDialog(MainActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("รอสักครู่...");
        dialog.setIndeterminate(true);
        dialog.show();

        if (remember.isChecked()){
            putPreference();
        }else {
            mPreferences.edit().clear().apply();
            //Toast.makeText(MainActivity.this,"ไม่บันทึก",Toast.LENGTH_LONG).show();

        }
        Ion.with(MainActivity.this)
                .load(url+urllogin)
                .setBodyParameter("phone",phone.getText().toString())
                .setBodyParameter("pass",password.getText().toString())
                .setBodyParameter("type","3")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        dialog.dismiss();
                        if (result!=null){
                            String member_id=result.get("member_id").getAsString();
                            Intent intent=new Intent(MainActivity.this,Menu.class);
                            intent.putExtra("member_id",member_id);
                            finish();
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(MainActivity.this,"ไม่สามารถเข้าสู่ระบบได้",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}