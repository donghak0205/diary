package fastcampus.aop.part2.chapter03

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        //val findViewById(R.layout.activity_diary)
        val diaryEditText = findViewById<EditText>(R.id.diaryEditText)
        val diaryPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEditText.setText(diaryPreferences.getString("detail",""))

        val runnable = Runnable {
            diaryPreferences.edit {
                putString("detail",diaryEditText.text.toString())
            }
        }

        diaryEditText.addTextChangedListener{
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable,500)
        }

    }
}