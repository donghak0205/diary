package fastcampus.aop.part2.chapter03

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity_ori:AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        Log.d("DiaryActivity","Taramasalata")
        val diaryEditText = findViewById<EditText>(R.id.diaryEditText)
        val detailPreference = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEditText.setText(detailPreference.getString("detail",""))

        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit{
                putString("detail", diaryEditText.text.toString())
            }
        }

        diaryEditText.addTextChangedListener{
            handler.removeCallbacks(runnable) //아직 pending되어있는 쓰레드를 삭제해주기 위해서
            handler.postDelayed(runnable, 500)
        }
    }
}