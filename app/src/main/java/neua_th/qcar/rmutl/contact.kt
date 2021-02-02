package neua_th.qcar.rmutl


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.rmutl.R


class contact : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        val button=findViewById<Button>(R.id.logout)

        button.setOnClickListener(View.OnClickListener {
            val savelogin = getSharedPreferences("CHECK_LOGIN", MODE_PRIVATE)
            val editor = savelogin.edit()
            editor.putBoolean("login_status", false)
            editor.putString("member_id", null)
            editor.commit()
            finish()
            val i = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
            i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            i!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finish()
        })

    }

}