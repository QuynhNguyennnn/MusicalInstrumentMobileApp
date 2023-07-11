package com.example.highmusicapp.AdapterController;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.highmusicapp.Dao.CategoryDAO;
import com.example.highmusicapp.Dao.ProductDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Bill_Product;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;

import java.util.ArrayList;
import java.util.List;

public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.BillDetailViewHolder> {
    private Context context;
    private List<Bill_Product> list;
    private List<Product> productList;
    private HighMusicDatabase highMusicDatabase;
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    public BillDetailAdapter(Context context){
        this.context = context;
        list = new ArrayList<>();
    }

    public void addBillDetail(Bill_Product billProduct){
        list.add(billProduct);
        notifyDataSetChanged();
    }

    public void clearBillDetail(){
        list.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BillDetailAdapter.BillDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_detail_row, parent, false);
        return new BillDetailAdapter.BillDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillDetailViewHolder holder, int position){
        Bill_Product bill_product = list.get(position);
        highMusicDatabase = HighMusicDatabase.getInstance(context);
        productDAO = highMusicDatabase.getProductDAO();
        categoryDAO = highMusicDatabase.getCategoryDAO();
        Product product = productDAO.getProductByID(bill_product.getProductID());
        holder.txtName.setText("Name: "+product.getProductName());
        holder.txtCategory.setText("Category: "+ categoryDAO.getCategoryNameById(product.getCategoryID()));
        holder.txtMaterial.setText("Material: "+ product.getMaterial());
        holder.txtPrice.setText("Price: "+product.getPrice());

        String imgURL = product.getImage();

        Glide
                .with(context)
                .load(imgURL)
                .centerCrop()
                .placeholder(R.drawable.placeholderimage)
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BillDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView
                txtName,
                txtPrice,
                txtCategory,
                txtMaterial;
        private ImageView productImage;
        public BillDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtNameBill);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPriceBill);
            txtMaterial = (TextView) itemView.findViewById(R.id.txtMaterialBill);
            txtCategory = (TextView) itemView.findViewById(R.id.txtCategoryBill);
            productImage = (ImageView) itemView.findViewById(R.id.imgBill);
        }
    }
}
