package freelancer.gcsnuoc.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.entities.DetailProxy;

import static freelancer.gcsnuoc.entities.CustomerItem.STATUS_Customer.NON_WRITING;
import static freelancer.gcsnuoc.entities.CustomerItem.STATUS_Customer.UPLOADED;
import static freelancer.gcsnuoc.entities.CustomerItem.STATUS_Customer.WRITED;

public class Customer2Adapter extends RecyclerView.Adapter<Customer2Adapter.CustomerViewHolder> {

    private Context mContext;
    private ICustomerAdapterCallback2 mICustomerAdapterCallback2;
    private List<DetailProxy> mList = new ArrayList<>();
    private static Drawable icon;

    public Customer2Adapter(Context context, @NonNull ICustomerAdapterCallback2 CustomerAdapterCallback) {
        mContext = context;
        mICustomerAdapterCallback2 = CustomerAdapterCallback;

        if (icon == null)
            icon = context.getResources().getDrawable(R.drawable.ic_photo_default, null);
    }

    public void setList(List<DetailProxy> list) {
        mList.clear();
        mList.addAll(list);
    }

    public List<DetailProxy> getList() {
        return mList;
    }

    public void updateList(List<DetailProxy> congToProxies) {
        if (congToProxies == null)
            return;
        setList(congToProxies);
        notifyDataSetChanged();
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.item_rv_customer_adapter2, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomerViewHolder holder, final int position) {
        int ID_TBL_CUSTOMER_real = mList.get(position).getIDOfTBL_CUSTOMER();
        int indexReal = DetailActivity.mIntegerIntegerHashMap.get(ID_TBL_CUSTOMER_real);
        holder.tvIndex.setText(indexReal);

        DetailProxy detailProxy = mList.get(position);
        holder.tvNameCustomer.setText(detailProxy.getCustomerNameOfTBL_CUSTOMER());
        holder.tvAddressCustomer.setText(detailProxy.getCustomerAddressOfTBL_CUSTOMER());
        switch (detailProxy.getStatusCustomerOfTBL_CUSTOMER()) {
            case UPLOADED:
                holder.tvStatus.setText(UPLOADED.getStatus());
                break;
            case WRITED:
                holder.tvStatus.setText(WRITED.getStatus());
                break;
            case NON_WRITING:
                holder.tvStatus.setText(NON_WRITING.getStatus());
                break;
        }

        holder.tvIndexOld.setText(detailProxy.getOLD_INDEXOfTBL_CUSTOMER() + "");
        holder.tvIndexNew.setText(detailProxy.getNEW_INDEXOfTBL_CUSTOMER() + "");
        holder.itemView.setBackgroundColor(detailProxy.isFocusOfTBL_CUSTOMER() ? ContextCompat.getColor(mContext, R.color.photoBackgroundColor) : ContextCompat.getColor(mContext, R.color.colorTransparentBg));
        Bitmap bitmap = detailProxy.getBitmap();
        Drawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
        holder.mImageView.setImageDrawable(bitmap == null ? icon : bitmapDrawable);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CustomerViewHolder extends ViewHolder {
        public TextView tvStatus, tvNameCustomer, tvAddressCustomer, tvIndexOld, tvIndexNew;
        public RelativeLayout mView;
        public ImageView mImageView;
        public TextView tvIndex;

        public CustomerViewHolder(final View itemView) {
            super(itemView);
            tvStatus = (TextView) itemView.findViewById(R.id.item_rv_customer_adapter2_tv_status);
            tvIndex = (TextView) itemView.findViewById(R.id.item_rv_customer_adapter2_tv_index);
            tvNameCustomer = (TextView) itemView.findViewById(R.id.item_rv_customer_adapter2_tv_name_customer);
            tvAddressCustomer = (TextView) itemView.findViewById(R.id.item_rv_customer_adapter2_tv_address_customer);

            tvIndexOld = (TextView) itemView.findViewById(R.id.item_rv_customer_adapter2_tv_old_index);
            tvIndexNew = (TextView) itemView.findViewById(R.id.item_rv_customer_adapter2_tv_new_index);

            mView = (RelativeLayout) itemView.findViewById(R.id.item_rv_customer_adapter2_rl_item);
            mImageView = (ImageView) itemView.findViewById(R.id.item_rv_customer_adapter2_iv_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Common.runAnimationClickView(itemView, R.anim.twinking_view, TIME_DELAY_ANIM);
                    mICustomerAdapterCallback2.clickItem(getAdapterPosition(), mList.get(getAdapterPosition()).getIDOfTBL_CUSTOMER());
                }
            });
        }


    }

    public interface ICustomerAdapterCallback2 {
        void clickItem(int pos, int ID_TBL_CUSTOMER);
    }

}
