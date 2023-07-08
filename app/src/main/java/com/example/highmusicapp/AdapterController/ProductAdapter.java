package com.example.highmusicapp.AdapterController;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;

    private ProductListener productListener;

    public ProductAdapter(Context context, ProductListener listener) {
        this.context = context;
        productList = new ArrayList<>();
        this.productListener = listener;
    }

    public List<Product> getProductList() {
        return productList;
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
        holder.productID.setText(product.getProductID() + "");
        holder.productName.setText(product.getProductName() + "");
        holder.productCategoryID.setText(product.getCategoryID() + "");
        holder.productPrice.setText(product.getPrice() + "");
        holder.productMaterial.setText(product.getMaterial() + "");
        holder.productQuantity.setText(product.getQuantity() + "");
        holder.productDescription.setText(product.getDescription() + "");
        holder.productYearOfManufacture.setText(product.getYearOfManufacture() + "");
        holder.productImage.setText(product.getImage() + "");
        holder.productStatus.setText(product.isStatus() + "");

        holder.updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productListener.onUpdateProduct(product);
            }
        });

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productListener.onDeleteProduct(product.getProductID(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView
                productID,
                productName,
                productCategoryID,
                productPrice,
                productMaterial,
                productQuantity,
                productDescription,
                productYearOfManufacture,
                productImage,
                productStatus;

        private ImageView updateProduct, deleteProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productID = (TextView) itemView.findViewById(R.id.row_productID);
            productName = (TextView) itemView.findViewById(R.id.row_productName);
            productCategoryID = (TextView) itemView.findViewById(R.id.row_productCategoryID);
            productPrice = (TextView) itemView.findViewById(R.id.row_productPrice);
            productMaterial = (TextView) itemView.findViewById(R.id.row_productMaterial);
            productQuantity = (TextView) itemView.findViewById(R.id.row_productQuantity);
            productDescription = (TextView) itemView.findViewById(R.id.row_productDescription);
            productYearOfManufacture = (TextView) itemView.findViewById(R.id.row_productYearOfManufacture);
            productImage = (TextView) itemView.findViewById(R.id.row_productImage);
            productStatus = (TextView) itemView.findViewById(R.id.row_productStatus);
            updateProduct = (ImageView) itemView.findViewById(R.id.row_updateProduct);
            deleteProduct = (ImageView) itemView.findViewById(R.id.row_deleteProduct);
        }
    }
}
