package neua_th.qcar.rmutl

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.rmutl.R

class hindlogo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hindlogo)
        Handler().postDelayed({
            val check_login = getSharedPreferences("CHECK_LOGIN", MODE_PRIVATE)
            val check = check_login.getBoolean("login_status", false)
            val member_id = check_login.getString("member_id", null)
            val intent: Intent
            if (check == true && member_id != null) {
                intent = Intent(this@hindlogo, Menu::class.java)
                intent.putExtra("member_id", member_id)
            } else {
                intent = Intent(this@hindlogo, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 3000)
    }
}