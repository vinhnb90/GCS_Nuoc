package freelancer.gcsnuoc.bookmanager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.entities.BookItem;
import freelancer.gcsnuoc.entities.BookItemProxy;
import freelancer.gcsnuoc.utils.Common;

import static freelancer.gcsnuoc.entities.BookItem.STATUS_BOOK.NON_WRITING;
import static freelancer.gcsnuoc.entities.BookItem.STATUS_BOOK.UPLOADED;
import static freelancer.gcsnuoc.entities.BookItem.STATUS_BOOK.WRITED;
import static freelancer.gcsnuoc.utils.Common.TIME_DELAY_ANIM;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private Context mContext;
    private IBookAdapterCallback mIBookAdapterCallback;
    private List<BookItemProxy> mList = new ArrayList<>();
    private static Drawable sDrawableUnChecked, sDrawableChecked;

    public BooksAdapter(Context context, @NonNull IBookAdapterCallback bookAdapterCallback) {
        mContext = context;
        mIBookAdapterCallback = bookAdapterCallback;

        if (sDrawableUnChecked == null) {
            sDrawableUnChecked = ContextCompat.getDrawable(mContext, R.drawable.xml_button_cricle_checkbox);
        }
        if (sDrawableChecked == null) {
            sDrawableChecked = ContextCompat.getDrawable(mContext, R.drawable.xml_button_cricle_checkbox_2);
        }
    }

    public void setList(List<BookItemProxy> list) {
        mList.clear();
        mList.addAll(list);
    }

    public List<BookItemProxy> getList() {
        return mList;
    }

    public void updateList(List<BookItemProxy> congToProxies) {
        if (congToProxies == null)
            return;
        setList(congToProxies);
        notifyDataSetChanged();

    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.item_rv_book_adapter, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookViewHolder holder, final int position) {
        BookItemProxy bookItemProxy = mList.get(position);
        holder.tvNameBook.setText(bookItemProxy.getBookName());
        holder.ibtnChoose.setVisibility(View.INVISIBLE);
        holder.ibtnChoose.setImageResource(bookItemProxy.isChoose() ? R.drawable.xml_button_cricle_checkbox_2 : R.drawable.xml_button_cricle_checkbox);
        switch (bookItemProxy.getStatusBook()) {
            case UPLOADED:
                holder.tvStatus.setText(UPLOADED.getStatus());
                break;
            case WRITED:
                if (Common.isChooseUpload)
                    holder.ibtnChoose.setVisibility(View.VISIBLE);
                holder.tvStatus.setText(WRITED.getStatus());
                break;
            case NON_WRITING:
                if (Common.isChooseUpload)
                    holder.ibtnChoose.setVisibility(View.VISIBLE);
                holder.tvStatus.setText(NON_WRITING.getStatus());
                break;
        }

        holder.mView1.setVisibility(bookItemProxy.isFocus() ? View.GONE : View.VISIBLE);
        holder.mView2.setVisibility(bookItemProxy.isFocus() ? View.GONE : View.VISIBLE);

        holder.tvWriteOk.setText(bookItemProxy.getCustomerWrited() + "");
        holder.tvNotWriteOK.setText(bookItemProxy.getCustomerNotWrite() + "");
        holder.tvUploaded.setText(bookItemProxy.getCustomerUploaded() + "");
        holder.tvPeriod.setText("Kỳ " + bookItemProxy.getTerm_book() + " Tháng " + bookItemProxy.getMonth_book() + "/" + bookItemProxy.getYear_book());
        holder.tvMaSo.setText(bookItemProxy.getCODE());

        //trigger pos to scroll
        if (bookItemProxy.isFocus())
            mIBookAdapterCallback.scrollToPosition(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class BookViewHolder extends ViewHolder {
        private final TextView tvPeriod;
        private final TextView tvMaSo;
        public TextView tvStatus, tvNameBook, tvWriteOk, tvNotWriteOK, tvUploaded;
        public ImageButton ibtnChoose;
        public RelativeLayout mView;
        public View mView1;
        public View mView2;


        public BookViewHolder(View itemView) {
            super(itemView);
            tvStatus = (TextView) itemView.findViewById(R.id.item_rv_book_adapter_tv_status);
            ibtnChoose = (ImageButton) itemView.findViewById(R.id.item_rv_book_adapter_ibtn_choose);
            tvNameBook = (TextView) itemView.findViewById(R.id.item_rv_book_adapter_tv_name_book);
            tvWriteOk = (TextView) itemView.findViewById(R.id.item_rv_book_adapter_tv_write_ok);
            tvNotWriteOK = (TextView) itemView.findViewById(R.id.item_rv_book_adapter_tv_not_write);
            tvUploaded= (TextView) itemView.findViewById(R.id.item_rv_book_adapter_tv_uploaded);
            tvPeriod = (TextView) itemView.findViewById(R.id.item_rv_book_adapter_tv_period);
            tvMaSo = (TextView) itemView.findViewById(R.id.ac_book_manager_rv_item_tv_maso);

            mView = (RelativeLayout) itemView.findViewById(R.id.item_rv_book_adapter_rl_row);
            mView1 = (View) itemView.findViewById(R.id.item_rv_book_adapter_v_1);
            mView2 = (View) itemView.findViewById(R.id.item_rv_book_adapter_v_2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIBookAdapterCallback.clickItem(getAdapterPosition());
                    Common.runAnimationClickView(mView, R.anim.twinking_view, TIME_DELAY_ANIM);
                }
            });

            ibtnChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ibtnChoose.getVisibility() == View.VISIBLE) {
                        BookItemProxy bookItemProxy = mList.get(getAdapterPosition());
                        mIBookAdapterCallback.clickCbChoose(getAdapterPosition(), !bookItemProxy.isChoose());
                    }
                }
            });
        }


    }

    public interface IBookAdapterCallback {
        void clickCbChoose(int pos, boolean isChecked);

        void clickItem(int pos);

        void scrollToPosition(int pos);

    }

}
