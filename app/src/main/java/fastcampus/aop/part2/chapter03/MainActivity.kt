package fastcampus.aop.part2.chapter03

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val firstNumberPicker:NumberPicker by lazy{
        findViewById<NumberPicker>(R.id.firstNumberPicker)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val secondsNumberPicker:NumberPicker by lazy{
        findViewById<NumberPicker>(R.id.secondsNumberPicker)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val thirdNumberPicker:NumberPicker by lazy{
        findViewById<NumberPicker>(R.id.thirdNumberPicker)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton: AppCompatButton by lazy{
        findViewById(R.id.openButton)
    }

    private val changePasswordButton: AppCompatButton by lazy{
        findViewById(R.id.changePasswordButton)
    }

    private var changeMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstNumberPicker
        secondsNumberPicker
        thirdNumberPicker

        openButton.setOnClickListener{

            if(changeMode){
                Toast.makeText(this,"비번 변경완료 후 시도 해주세요!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val passwordPreference = getSharedPreferences("password", Context.MODE_PRIVATE);
            val passwordFromUser = "${firstNumberPicker.value}${secondsNumberPicker.value}${thirdNumberPicker.value}"

            if(passwordPreference.getString("password","000").equals(passwordFromUser)){
                startActivity(Intent(this, DiaryActivity::class.java))
            } else {
                showErrorAlertDialog()
            }
        }

        //changePasswordButton
        changePasswordButton.setOnClickListener{

            val passwordPreference = getSharedPreferences("password", Context.MODE_PRIVATE);
            val passwordFromUser = "${firstNumberPicker.value}${secondsNumberPicker.value}${thirdNumberPicker.value}"

            //changeMode-> True라는게 비번이 맞는게 성립이 되었다.
            if(changeMode){
                passwordPreference.edit(true) {
                    putString("password",passwordFromUser)
                }

                changePasswordButton.setBackgroundColor(Color.BLACK)
                changeMode = false

            } else {
                if(passwordPreference.getString("password","000").equals(passwordFromUser)){
                    Toast.makeText(this,"변경할 비번을 입력해주세요!", Toast.LENGTH_LONG).show()

                    changeMode = true
                    changePasswordButton.setBackgroundColor(Color.RED)

                } else {
                    showErrorAlertDialog()
               }

            }
        }
    }

    private fun showErrorAlertDialog(){
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비번이 틀렸습니다.")
            .setPositiveButton("확인"){_,_->}
            .create()
            .show()
    }
}