package com.example.highmusicapp.AdapterController;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.highmusicapp.Dao.CartDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<Product> productList;

    private CartListener cartListener;
    private CartDAO cartDAO;

    private int cartID;
    private HighMusicDatabase highMusicDatabase;

    public CartAdapter(Context context, int cartID, CartListener cartListener){
        this.context = context;
        productList = new ArrayList<>();
        this.cartListener = cartListener;
        this.cartID = cartID;
    }

    public void addProduct(Product product) {
        productList.add(product);
        notifyDataSetChanged();
    }

    public void clearProduct() {
        productList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position){
        Product product = productList.get(position);
        holder.productName.setText(product.getProductName()+"");
        holder.productPrice.setText("Price: "+product.getPrice());
        holder.productQuantity.setText("Quantity: "+product.getQuantity());
        holder.productMaterial.setText("Material: "+product.getMaterial());
        highMusicDatabase = HighMusicDatabase.getInstance(context);
        cartDAO = highMusicDatabase.getCartDAO();

        String imageUrl = product.getImage();
        Glide
                .with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholderimage)
                .into(holder.productImage);

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartListener.onRemoveProduct(cartID, product.getProductID());
            }
        });

        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartListener.onIncrease(cartID, product.getProductID());
            }
        });

        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartListener.onDecrease(cartID, product.getProductID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView
                productName,
                productQuantity,
                productPrice,
                productMaterial;


        private ImageView productImage;
        private Button btnRemove, btnIncrease, btnDecrease;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.row_cartName);
            productQuantity = (TextView) itemView.findViewById(R.id.row_cartQuantity);
            productPrice = (TextView) itemView.findViewById(R.id.row_cartPrice);
            productMaterial = (TextView) itemView.findViewById(R.id.row_cartMaterial);
            productImage = (ImageView) itemView.findViewById(R.id.row_cartImage);
            btnRemove = (Button) itemView.findViewById(R.id.btnRemoveFromCart);
            btnIncrease = (Button)  itemView.findViewById(R.id.btnIncrease);
            btnDecrease = (Button)  itemView.findViewById(R.id.btnDecrease);
        }
    }
}
