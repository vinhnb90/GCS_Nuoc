package freelancer.gcsnuoc.server.model.GetData;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
@SerializedName("IndexValue")
@Expose
private List<IndexValue> indexValue = null;
@SerializedName("DeliveryBook")
@Expose
private List<DeliveryBook> deliveryBook = null;
@SerializedName("BookAvailable")
@Expose
private List<BookAvailable> bookAvailable = null;

public List<IndexValue> getIndexValue() {
return indexValue;
}

public void setIndexValue(List<IndexValue> indexValue) {
this.indexValue = indexValue;
}

public List<DeliveryBook> getDeliveryBook() {
return deliveryBook;
}

public void setDeliveryBook(List<DeliveryBook> deliveryBook) {
this.deliveryBook = deliveryBook;
}

public List<BookAvailable> getBookAvailable() {
return bookAvailable;
}

public void setBookAvailable(List<BookAvailable> bookAvailable) {
this.bookAvailable = bookAvailable;
}

}
