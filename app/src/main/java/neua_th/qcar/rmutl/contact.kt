package neua_th.qcar.rmutl


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.rmutl.R

class contact : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        val imageButton=findViewById<ImageButton>(R.id.facebook)


        imageButton.setOnClickListener(View.OnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/a.aek1998"))
            startActivity(i) })
    }

}