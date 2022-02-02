package com.example.howlstagram_f16

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        email_login_button.setOnClickListener{
            signinAndSignup()
        }
    }

    // 로그인 or 계정생성
    fun signinAndSignup(){
        auth?.createUserWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
            ?.addOnCompleteListener {
            task ->
                if(task.isSuccessful){ // 계정 생성에 성공
                    // 계정 생성에 성공했을때, 메인페이지로 이동
                    moveMainPage(task.result.user)
                } else if(!(task.exception?.message.isNullOrEmpty())){ // 로그인 에러가 났을 때 에러메세지 출력
                    // 에러 메세지 띄우기
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                } else{
                    // 계정이 있는 상태임
                    // 로그인하기
                    signinEmail()
                }
       }
    }

    // 이메일로 로그인하기
    fun signinEmail() {
        auth?.signInWithEmailAndPassword(
            email_edittext.text.toString(),
            password_edittext.text.toString()
        )
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) { // id와 pw가 맞았을 때, 로그인 성공
                    // 로그인 성공 후, 메인 페이지로 이동
                    moveMainPage(task.result.user)

                } else { // id와 pw가 틀렸을 때
                    // 에러 메세지 띄우기
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    // 로그인 성공 후 다음 페이지로 넘어가기기
    fun moveMainPage(user:FirebaseUser?){
        if(user != null){
            startActivity(Intent(this, MainActivity::class.java)) // MainActivity 호출
        }
    }



}