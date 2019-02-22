package freelancer.gcsnuoc.detail;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.entities.DetailProxy;
import freelancer.gcsnuoc.utils.Common;

import static freelancer.gcsnuoc.entities.CustomerItem.STATUS_Customer.NON_WRITING;
import static freelancer.gcsnuoc.entities.CustomerItem.STATUS_Customer.UPLOADED;
import static freelancer.gcsnuoc.entities.CustomerItem.STATUS_Customer.WRITED;
import static freelancer.gcsnuoc.utils.Common.TIME_DELAY_ANIM;
import static freelancer.gcsnuoc.utils.Common.getKeysFromValue;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private static Drawable icon1, icon2;
    private Context mContext;
    private ICustomerAdapterCallback mICustomerAdapterCallback;
    private List<DetailProxy> mList = new ArrayList<>();

    public CustomerAdapter(Context context, @NonNull ICustomerAdapterCallback CustomerAdapterCallback) {
        mContext = context;
        mICustomerAdapterCallback = CustomerAdapterCallback;
        if (icon1 == null)
            icon1 = context.getResources().getDrawable(R.drawable.xml_border_full_type5, null);

        if (icon2 == null)
            icon2 = context.getResources().getDrawable(R.drawable.xml_border_full_type8, null);
    }

    public void setList(List<DetailProxy> list) {
        mList.clear();
        mList = new ArrayList<DetailProxy>(list);
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
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.item_rv_customer_adapter, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomerViewHolder holder, final int position) {
        int ID_TBL_CUSTOMER_real = mList.get(position).getIDOfTBL_CUSTOMER();

        List<Object> objects = getKeysFromValue(DetailActivity.mIntegerIntegerHashMap, ID_TBL_CUSTOMER_real);
        if (objects.size() != 1)
            return;
        int indexReal = (int) objects.get(0);

        holder.tvIndex.setText(indexReal + "");

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

        boolean isFocus = detailProxy.isFocusOfTBL_CUSTOMER();
        holder.mView.setBackground(isFocus ? icon1 : icon2);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CustomerViewHolder extends ViewHolder {
        public TextView tvStatus, tvNameCustomer, tvAddressCustomer, tvIndex;
        public LinearLayout mView;

        public CustomerViewHolder(final View itemView) {
            super(itemView);

            tvIndex = (TextView) itemView.findViewById(R.id.item_rv_customer_adapter_tv_index);
            tvStatus = (TextView) itemView.findViewById(R.id.item_rv_customer_adapter_tv_status);
            tvNameCustomer = (TextView) itemView.findViewById(R.id.item_rv_customer_adapter_tv_name_customer);
            tvAddressCustomer = (TextView) itemView.findViewById(R.id.item_rv_customer_adapter_tv_address_customer);
            mView = (LinearLayout) itemView.findViewById(R.id.item_rv_customer_adapter_rl_row);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position >= mList.size())
                        return;
                    mICustomerAdapterCallback.clickItem(position, mList.get(position).getIDOfTBL_CUSTOMER());
                }
            });
        }
    }

    public int DpToPx(Activity activity, int dp) {
        Resources r = activity.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;

    }

    public interface ICustomerAdapterCallback {
        void clickItem(int pos, int ID_TBL_CUSTOMER);
    }
}
