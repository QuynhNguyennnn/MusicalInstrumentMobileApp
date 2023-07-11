package com.example.highmusicapp.AdapterController;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Bill;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;

import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    private Context context;
    private List<Bill> billList;
    private BillListener billListener;

    public BillAdapter(Context context, BillListener billListener) {
        this.context = context;
        billList = new ArrayList<>();
        this.billListener = billListener;
    }

    public void addBill(Bill bill){
        billList.add(bill);
        notifyDataSetChanged();
    }

    public void clearBill(){
        billList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BillAdapter.BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_row, parent, false);
        return new BillAdapter.BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.BillViewHolder holder, int position) {
        Bill bill = billList.get(position);
        holder.txtPurchaseDate.setText("Purchase Date: "+bill.getPurchaseDate() + "");
        holder.txtTotal.setText("Total: " + bill.getTotal());

        holder.btnBillDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billListener.onViewBillDetail(bill.getBillID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {
        private TextView
                txtPurchaseDate,
                txtTotal;

        private Button btnBillDetail;

        public BillViewHolder(@NonNull View itemView) {
                super(itemView);
                txtPurchaseDate = (TextView) itemView.findViewById(R.id.txtPurchaseDate);
            txtTotal = (TextView) itemView.findViewById(R.id.txtTotalBill);
            btnBillDetail = (Button) itemView.findViewById(R.id.btnViewBillDetail);
        }
    }
}
