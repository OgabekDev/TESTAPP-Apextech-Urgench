package dev.ogabek.testapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dev.ogabek.testapp.R
import dev.ogabek.testapp.adapter.ProductAdapter
import dev.ogabek.testapp.databinding.ActivityDelailsBinding
import dev.ogabek.testapp.manager.PrefsManager
import dev.ogabek.testapp.model.Product
import dev.ogabek.testapp.networking.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : BaseActivity() {

    lateinit var detailBinding: ActivityDelailsBinding

    private val token = "Token " + PrefsManager.getInstance(this)!!.getData("token") as String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDelailsBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val id = intent.getStringExtra("id")!!.toInt()

        getProduct(id)

    }

    private fun getProduct(id: Int) {
        showLoading(this)
        RetrofitHttp.productService.getProduct(token, id).enqueue(object :
            Callback<Product> {
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
                dismissLoading()
                if (response.body() != null) {
                    setData(response.body()!!)
                } else {
                    if (response.code() == 400) {
                        showDialogMessage(response.message(), "token should not be empty")
                    }
                    if (response.code() == 404) {
                        showDialogMessage(response.message(), "Product not found")
                    }
                    if (response.code() == 401) {
                        showDialogMessage(response.message(), "Customer not found")
                    }
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                dismissLoading()
                showDialogMessage(t.message.toString(), t.localizedMessage.toString())
            }

        })
    }

    private fun setData(product: Product) {
        Glide.with(context).load(product.photoUrl).centerCrop().placeholder(R.drawable.ic_launcher_background).into(detailBinding.ivPhoto)
        detailBinding.tvName.text = product.name
        detailBinding.tvDescription.text = product.description
    }

}