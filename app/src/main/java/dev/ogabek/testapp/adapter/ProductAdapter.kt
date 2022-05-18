package dev.ogabek.testapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.ogabek.testapp.R
import dev.ogabek.testapp.activity.DetailsActivity
import dev.ogabek.testapp.databinding.ItemProductBinding
import dev.ogabek.testapp.model.Product

class ProductAdapter(val context: Context, private val products: ArrayList<Product>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = products[position]

        if (holder is ProductViewHolder) {
            Glide.with(context).load(product.photoUrl).centerCrop().placeholder(R.drawable.ic_launcher_background).into(holder.binding.ivItemPhoto)
            holder.binding.tvItemName.text = product.name
            holder.binding.tvItemPrice.text = product.price.toString()
            holder.binding.itemProduct.setOnClickListener {
                callDetailsActivity(product)
            }
        }
    }

    private fun callDetailsActivity(product: Product) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("id", product.id.toString())
        context.startActivity(intent)
    }

    override fun getItemCount() = products.size
}