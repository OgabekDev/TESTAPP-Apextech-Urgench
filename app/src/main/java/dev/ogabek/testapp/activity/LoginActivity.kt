package dev.ogabek.testapp.activity

import android.os.Bundle
import android.util.Log
import dev.ogabek.testapp.databinding.ActivityLoginBinding
import dev.ogabek.testapp.manager.PrefsManager
import dev.ogabek.testapp.model.login.LoginRespond
import dev.ogabek.testapp.model.login.LoginSend
import dev.ogabek.testapp.networking.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        initViews()

    }

    private fun initViews() {
        loginBinding.btnLogin.setOnClickListener {
            val login: String = loginBinding.etLogin.text.toString()
            val password: String = loginBinding.etPassword.text.toString()

            Log.d("Login Password", "$login $password")
            loginViaServer(login, password)
        }

    }

    private fun loginViaServer(login: String, password: String) {
        showLoading(this)
        RetrofitHttp.productService.auth(LoginSend(login, password))
            .enqueue(object : Callback<LoginRespond> {
                override fun onResponse(call: Call<LoginRespond>, response: Response<LoginRespond>) {
                    dismissLoading()
                    Log.d("Login Respond Code", response.code().toString() + response.message())
                    if (response.body() != null) {
                        dataReceived(response.body()!!)
                    } else {
                        if (response.code() == 400) {
                            showDialogMessage(response.message(), "login/password should not be empty")
                        }
                        if (response.code() == 404) {
                            showDialogMessage(response.message(), "Invalid login or password")
                        }
                    }
                }

                override fun onFailure(call: Call<LoginRespond>, t: Throwable) {
                    dismissLoading()
                    showDialogMessage(t.message.toString(), t.localizedMessage.toString())
                }

            })
    }

    private fun dataReceived(respond: LoginRespond) {
        saveTokenToStore(respond.token)
        callMainActivity(this, respond)
    }

    private fun saveTokenToStore(token: String?) {
        PrefsManager.getInstance(this)!!.saveData("token", token)
    }


}