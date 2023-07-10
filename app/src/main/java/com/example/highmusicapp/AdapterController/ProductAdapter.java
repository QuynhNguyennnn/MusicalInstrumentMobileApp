package com.example.highmusicapp.AdapterController;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.highmusicapp.ActivityController.ViewProductActivity;
import com.example.highmusicapp.Dao.CategoryDAO;
import com.example.highmusicapp.Dao.ProductDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Category;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;

    private ProductListener productListener;

    private HighMusicDatabase highMusicDatabase;
    private CategoryDAO categoryDAO;

    private List<Category> categoryList;

    public ProductAdapter(Context context, ProductListener listener) {
        this.context = context;
        productList = new ArrayList<>();
        this.productListener = listener;
        categoryList = new ArrayList<>();
    }

    public List<Product> getProductList() {
        return productList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }


    public void addProduct(Product product) {
        productList.add(product);
        notifyDataSetChanged();
    }

    public void removeProduct(int position) {
        productList.remove(position);
        notifyDataSetChanged();
    }

    public void clearProduct() {
        productList.clear();
        notifyDataSetChanged();
    }

    public void filterProduct(List<Product> filterlist) {
        productList = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getProductName() + "");
        holder.productPrice.setText("Price: " + product.getPrice());
        holder.productMaterial.setText("Material: " + product.getMaterial());

        //Set Category Name by using category id of foreign key
        highMusicDatabase = HighMusicDatabase.getInstance(context);
        categoryDAO = highMusicDatabase.getCategoryDAO();
        String categoryNamebyId = categoryDAO.getCategoryNameById(product.getCategoryID());
        holder.productCategoryID.setText("Category: " + categoryNamebyId);

        // Loading image view with gilde
        String imageUrl = product.getImage();
        Glide
                .with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholderimage)
                .into(holder.productImage);

        holder.productDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productListener.onViewDetailProduct(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView
                productName,
                productCategoryID,
                productPrice,
                productMaterial;


        private ImageView productImage;
        private Button productDetailBtn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.row_productName);
            productCategoryID = (TextView) itemView.findViewById(R.id.row_productCategoryID);
            productPrice = (TextView) itemView.findViewById(R.id.row_productPrice);
            productMaterial = (TextView) itemView.findViewById(R.id.row_productMaterial);
            productImage = (ImageView) itemView.findViewById(R.id.row_productImage);
            productDetailBtn = (Button) itemView.findViewById(R.id.row_productDetailBtn);
        }
    }
}
