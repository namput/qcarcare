package neua_th.qcar.rmutl


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.example.rmutl.R


class contact : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        val imageButton=findViewById<ImageButton>(R.id.facebook)
        val button=findViewById<Button>(R.id.logout)

        imageButton.setOnClickListener(View.OnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/a.aek1998"))
            startActivity(i) })
        button.setOnClickListener(View.OnClickListener {
            val message="0"
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("status","0")
            }
            startActivity(intent)
            finish()
        })

    }

}