package neua_th.qcar.rmutl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmutl.R;
import com.google.firebase.iid.FirebaseInstanceId;
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
    AlertDialog.Builder dialogBuilder;
    View layoutView;
    AlertDialog alertDialog;
    Button getout;
    TextView content;
    EditText reviewcustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        phone=(EditText)findViewById(R.id.phone);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        crecate=(Button)findViewById(R.id.create);

        reviewcustomer = (EditText)findViewById(R.id.reviewcustomer);
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);

        //ลิงค์เชื่อต่อ
        url=getString(R.string.url);
        urllogin=getString(R.string.login);
        final String urlcreate=getString(R.string.create);

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
                                    Intent intent=new Intent(MainActivity.this,ConfigActivity.class);
                                    intent.putExtra("member_id",member_id);
                                    intent.putExtra("member_phone",member_phone);
                                    startActivity(intent);
                                    finish();
                                }
                                else {

                                    layoutView = getLayoutInflater().inflate(R.layout.dialog_create_fail, null);
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

    private void loginnow(){
        final ProgressDialog dialog=new ProgressDialog(MainActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("รอสักครู่...");
        dialog.setIndeterminate(true);
        dialog.show();
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
                            final String urlgettoken = getString(R.string.gettoken);
                            String token = FirebaseInstanceId.getInstance().getToken();
                            Ion.with(MainActivity.this)
                                    .load(url+urlgettoken)
                                    .setBodyParameter("token",token)
                                    .setBodyParameter("member_id",member_id)
                                    .asString()
                                    .setCallback(new FutureCallback<String>() {
                                        @Override
                                        public void onCompleted(Exception e, String result) {

                                        }
                                    });
                            SharedPreferences savelogin = getSharedPreferences("CHECK_LOGIN", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = savelogin.edit();
                            editor.putBoolean("login_status",true);
                            editor.putString("member_id",member_id);
                            editor.commit();
                            finish();
                            Intent intent=new Intent(MainActivity.this,Menu.class);
                            intent.putExtra("member_id",member_id);
                            finish();
                            startActivity(intent);
                        }
                        else {

                            layoutView = getLayoutInflater().inflate(R.layout.dialog_login_fail, null);
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
}