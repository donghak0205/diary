package fastcampus.aop.part2.chapter03

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity_ori : AppCompatActivity() {

    private val firstNumberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.firstNumberPicker)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val secondsNumberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.secondsNumberPicker)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val thirdNumberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.thirdNumberPicker)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }

    private val changePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }

    private var changePasswordMode = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*        firstNumberPicker
        secondsNumberPicker
        thirdNumberPicker*/

        openButton.setOnClickListener {
            if (changePasswordMode) {
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser =
                "${firstNumberPicker.value}${secondsNumberPicker.value}${thirdNumberPicker.value}"

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {

            } else {
                showErrorAlertDialog()
            }
        }

        changePasswordButton.setOnClickListener {

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser =
                "${firstNumberPicker.value}${secondsNumberPicker.value}${thirdNumberPicker.value}"

            if (changePasswordMode) {

                //설정한 번호를 저장한다. 즉, 비밀번호를 변경한다.
                passwordPreferences.edit(true) {
                    putString("password", passwordFromUser)
                }

                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)

            } else {
                //changePasswordMode를 활성화
                //1. 비밀번호가 맞는지 체크
                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                    changePasswordMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()
                    //return@setOnClickListener
                    changePasswordButton.setBackgroundColor(Color.RED)
                } else {
                    showErrorAlertDialog()
                }

            }
        }
    }

    private fun showErrorAlertDialog(){
        AlertDialog.Builder(this)
            .setTitle("실패!!!")
            .setMessage("비밀번호가 잘되었습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}