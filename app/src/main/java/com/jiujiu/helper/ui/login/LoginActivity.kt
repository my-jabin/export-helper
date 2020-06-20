package com.jiujiu.helper.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.*
import com.jiujiu.helper.R
import com.jiujiu.helper.ui.main.MainActivity
import com.jiujiu.helper.util.afterTextChanged
import com.jiujiu.helper.util.isEmail
import com.jiujiu.helper.util.isValidPassword
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger

class LoginActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var mAuth: FirebaseAuth
    private var progress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        checkAuthentication()
        setupView()
        // todo: allow user to reset the pwd by sending email
        // todo: login with google
    }

    private fun setupView() {
        et_login_email.afterTextChanged {
            if (!it.isEmail()) {
                showEmailErrorMessage(R.string.error_email_invalid, false)
            } else til_login_email.apply {
                error = null
                isErrorEnabled = false
            }
        }

        et_login_password.afterTextChanged {
            if (!it.isValidPassword()) {
                showPwdErrorMessage(R.string.error_pwd_invalid, false)
            } else {
                til_login_password.apply {
                    error = null
                    isErrorEnabled = false
                }
            }
        }
    }

    private fun checkAuthentication() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun isEmailAndPwdValid(): Boolean {
        if (et_login_email.text.isNullOrBlank()) {
            showEmailErrorMessage(R.string.error_email_invalid, false)
            return false
        }
        if (et_login_password.text.isNullOrBlank()) {
            showPwdErrorMessage(R.string.error_pwd_invalid, false)
            return false
        }
        return if (et_login_email.text.toString().isEmail() && et_login_password.text.toString().isValidPassword()) {
            btn_login.requestFocus()
            showProgress()
            true
        } else {
            hideProgress()
            if (til_login_email.error != null) {
                et_login_email.requestFocus()
            } else if (til_login_password.error != null) {
                et_login_password.requestFocus()
            }
            false
        }
    }

    fun loginWithEmail(view: View) {
        if (isEmailAndPwdValid()) {
            val email = et_login_email.text.toString()
            val pwd = et_login_password.text.toString()
            mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    hideProgress()
                    when (task.exception) {
                        is FirebaseAuthInvalidUserException -> showEmailErrorMessage(R.string.error_email_not_exist)
                        is FirebaseAuthInvalidCredentialsException -> showPwdErrorMessage(R.string.error_pwd_invalid)
                        else -> Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun registerWithEmail(view: View) {
        if (isEmailAndPwdValid()) {
            val email = et_login_email.text.toString()
            val pwd = et_login_password.text.toString()
            mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    when (task.exception) {
                        is FirebaseAuthWeakPasswordException -> showPwdErrorMessage(R.string.error_pwd_weak)
                        is FirebaseAuthInvalidCredentialsException -> showEmailErrorMessage(R.string.error_email_invalid)
                        is FirebaseAuthUserCollisionException -> showEmailErrorMessage(R.string.error_email_exist)
                    }

                }
            }
        }
    }

    private fun showEmailErrorMessage(@StringRes resId: Int, requestFocus: Boolean = true) {
        til_login_email.isErrorEnabled = true
        til_login_email.error = getString(resId)
        if (requestFocus) et_login_email.requestFocus()
    }

    private fun showPwdErrorMessage(@StringRes resId: Int, requestFocus: Boolean = true) {
        til_login_password.isErrorEnabled = true
        til_login_password.error = getString(resId)
        if (requestFocus) til_login_password.requestFocus()
    }

    private fun showProgress() {
        progress = true
        progressBar.visibility = View.VISIBLE
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        view_disable_cover.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress = false
        progressBar.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        view_disable_cover.visibility = View.GONE
    }

    override fun onBackPressed() {
        if (progress) {
            hideProgress()
        } else {
            super.onBackPressed()
        }
    }


}
