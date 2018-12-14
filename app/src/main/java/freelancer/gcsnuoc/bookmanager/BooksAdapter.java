package freelancer.gcsnuoc.bookmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.entities.BookItem;
import freelancer.gcsnuoc.entities.BookItemProxy;
import freelancer.gcsnuoc.utils.Common;

import static freelancer.gcsnuoc.entities.BookItem.STATUS_BOOK.UPLOADED;
import static freelancer.gcsnuoc.utils.Common.TIME_DELAY_ANIM;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private Context mContext;
    private IBookAdapterCallback mIBookAdapterCallback;
    private List<BookItemProxy> mList = new ArrayList<>();

    public BooksAdapter(Context context, @NonNull IBookAdapterCallback bookAdapterCallback) {
        mContext = context;
        mIBookAdapterCallback = bookAdapterCallback;
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
        Common.runAnimationClickView(holder.mView, R.anim.twinking_view, TIME_DELAY_ANIM);
        holder.mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                BookItemProxy bookItemProxy = mList.get(position);
                holder.cbChoose.setVisibility(View.VISIBLE);
                holder.cbChoose.setChecked(bookItemProxy.isChoose());
                switch (bookItemProxy.getStatusBook()) {
                    case UPLOADED:
                        holder.tvStatus.setText(UPLOADED.getStatus());
                        holder.cbChoose.setVisibility(View.INVISIBLE);
                        break;
                    case WRITED:
                        holder.tvStatus.setText(BookItem.STATUS_BOOK.WRITED.getStatus());
                        break;
                    case NON_WRITING:
                        holder.tvStatus.setText(BookItem.STATUS_BOOK.NON_WRITING.getStatus());
                        holder.cbChoose.setVisibility(View.INVISIBLE);
                        break;
                }

                holder.tvWriteOk.setText(bookItemProxy.getCustomerWrited());
                holder.tvWriteOk.setText(bookItemProxy.getCustomerNotWrite());

                holder.itemView.setBackgroundColor(bookItemProxy.isFocus()? ContextCompat.getColor(mContext, R.color.rowBookColor): ContextCompat.getColor(mContext, R.color.colorTransparent));

                //trigger pos to scroll
                if(bookItemProxy.isFocus())
                    mIBookAdapterCallback.scrollToPosition(position);
            }
        }, TIME_DELAY_ANIM);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class BookViewHolder extends ViewHolder {
        public TextView tvStatus, tvNameBook, tvWriteOk, tvNotWriteOK;
        public CheckBox cbChoose;
        public RelativeLayout mView;

        public BookViewHolder(View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.item_rv_book_adapter_tv_status);
            cbChoose = itemView.findViewById(R.id.item_rv_book_adapter_cb_choose);
            tvNameBook = itemView.findViewById(R.id.item_rv_book_adapter_tv_name_book);
            tvWriteOk = itemView.findViewById(R.id.item_rv_book_adapter_tv_write_ok);
            tvNotWriteOK = itemView.findViewById(R.id.item_rv_book_adapter_tv_not_write);
            mView = itemView.findViewById(R.id.item_rv_book_adapter_rl_row);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIBookAdapterCallback.clickItem(getAdapterPosition());
                }
            });

            cbChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isPressed()) {
                        mIBookAdapterCallback.clickCbChoose(getAdapterPosition(), isChecked);
                    }
                }
            });
        }


    }

    public interface IBookAdapterCallback {
//        void clickBtnGhimRowCto(int pos, List<CongToGuiKDProxy> listCtoKD, List<CongToPBProxy> listCtoPB);

        void clickCbChoose(int pos, boolean isChecked);

        void clickItem(int pos);

        void scrollToPosition(int pos);

//        String interactionDataINFO_RESULT(int id, Common.TYPE_SESSION mTypeSessionHistory, String mDateSessionHistory);

//        void clickTvInfoResult(String infoResult);
    }

}
