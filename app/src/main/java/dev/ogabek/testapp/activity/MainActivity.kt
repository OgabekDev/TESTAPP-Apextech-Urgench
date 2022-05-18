package dev.ogabek.testapp.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ogabek.testapp.adapter.ProductAdapter
import dev.ogabek.testapp.databinding.ActivityMainBinding
import dev.ogabek.testapp.manager.PrefsManager
import dev.ogabek.testapp.model.Product
import dev.ogabek.testapp.model.login.LoginRespond
import dev.ogabek.testapp.networking.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var respond: LoginRespond

    private val products = ArrayList<Product>()

    var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        token = "Token " + PrefsManager.getInstance(this)!!.getData("token") as String

        getRespond()

        initViews()

        getListFromServer()

    }

    private fun initViews() {
        mainBinding.rvProductsList.adapter = ProductAdapter(this, products)
    }

    private fun getListFromServer() {
        showLoading(this)
        RetrofitHttp.productService.getProducts(token!!).enqueue(object : Callback<ArrayList<Product>> {
                override fun onResponse(
                    call: Call<ArrayList<Product>>,
                    response: Response<ArrayList<Product>>) {
                    dismissLoading()
                    if (response.body() != null) {
                        products.clear()
                        products.addAll(response.body()!!)
                        mainBinding.rvProductsList.adapter = ProductAdapter(this@MainActivity, products)
                        mainBinding.rvProductsList.layoutManager = LinearLayoutManager(this@MainActivity)
                    } else {
                        if (response.code() == 400) {
                            showDialogMessage(response.message(), "token should not be empty")
                        }
                        if (response.code() == 404) {
                            showDialogMessage(response.message(), "Customer not found")
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                    dismissLoading()
                    showDialogMessage(t.message.toString(), t.localizedMessage.toString())
                }

            })
    }

    private fun getRespond() {
        respond = intent.getSerializableExtra("respond") as LoginRespond
    }


}