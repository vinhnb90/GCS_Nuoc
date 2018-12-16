package freelancer.gcsnuoc.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.entities.CustomerItem;
import freelancer.gcsnuoc.entities.DetailProxy;
import freelancer.gcsnuoc.utils.Common;

import static freelancer.gcsnuoc.entities.CustomerItem.STATUS_Customer.NON_WRITING;
import static freelancer.gcsnuoc.entities.CustomerItem.STATUS_Customer.UPLOADED;
import static freelancer.gcsnuoc.entities.CustomerItem.STATUS_Customer.WRITED;
import static freelancer.gcsnuoc.utils.Common.TIME_DELAY_ANIM;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private Context mContext;
    private ICustomerAdapterCallback mICustomerAdapterCallback;
    private List<DetailProxy> mList = new ArrayList<>();

    public CustomerAdapter(Context context, @NonNull ICustomerAdapterCallback CustomerAdapterCallback) {
        mContext = context;
        mICustomerAdapterCallback = CustomerAdapterCallback;
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
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.item_rv_customer_adapter, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomerViewHolder holder, final int position) {
        Common.runAnimationClickView(holder.mView, R.anim.twinking_view, TIME_DELAY_ANIM);
        holder.mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                DetailProxy DetailProxy = mList.get(position);
                holder.tvNameCustomer.setText(DetailProxy.getCustomerNameOfTBL_CUSTOMER());
                holder.tvAddressCustomer.setText(DetailProxy.getCustomerAddressOfTBL_CUSTOMER());
                switch (DetailProxy.getStatusCustomerOfTBL_CUSTOMER()) {
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

                holder.tvIndexOld.setText(DetailProxy.getOLD_INDEXOfTBL_IMAGE());
                holder.tvIndexNew.setText(DetailProxy.getNEW_INDEXOfTBL_IMAGE());

                holder.itemView.setBackgroundColor(DetailProxy.isFocusOfTBL_CUSTOMER()? ContextCompat.getColor(mContext, R.color.rowBookColor): ContextCompat.getColor(mContext, R.color.colorTransparent));

                //trigger pos to scroll
                if(DetailProxy.isFocusOfTBL_CUSTOMER())
                    mICustomerAdapterCallback.scrollToPosition(position);
            }
        }, TIME_DELAY_ANIM);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CustomerViewHolder extends ViewHolder {
        public TextView tvStatus, tvNameCustomer, tvAddressCustomer, tvIndexOld, tvIndexNew;
        public RelativeLayout mView;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.item_rv_customer_adapter_tv_status);
            tvNameCustomer = itemView.findViewById(R.id.item_rv_customer_adapter_tv_name_customer);
            tvAddressCustomer = itemView.findViewById(R.id.item_rv_customer_adapter_tv_address_customer);
            tvIndexOld = itemView.findViewById(R.id.item_rv_customer_adapter_tv_write_ok);
            tvIndexNew = itemView.findViewById(R.id.item_rv_customer_adapter_tv_not_write);
            mView = itemView.findViewById(R.id.item_rv_customer_adapter_rl_row);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mICustomerAdapterCallback.clickItem(getAdapterPosition());
                }
            });
        }


    }

    public interface ICustomerAdapterCallback {
//        void clickBtnGhimRowCto(int pos, List<CongToGuiKDProxy> listCtoKD, List<CongToPBProxy> listCtoPB);

        void clickItem(int pos);

        void scrollToPosition(int pos);

//        String interactionDataINFO_RESULT(int id, Common.TYPE_SESSION mTypeSessionHistory, String mDateSessionHistory);

//        void clickTvInfoResult(String infoResult);
    }

}
