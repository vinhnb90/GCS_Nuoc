package freelancer.gcsnuoc.server;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import freelancer.gcsnuoc.server.model.GetData.BookInfoPost;
import freelancer.gcsnuoc.server.model.GetData.DataGetModelServer;
import freelancer.gcsnuoc.server.model.GetToken.TokenModelServer;
import freelancer.gcsnuoc.server.model.LoginServer.LoginGet;
import freelancer.gcsnuoc.server.model.LoginServer.LoginPost;
import freelancer.gcsnuoc.server.model.PostData.DataBookPost;
import freelancer.gcsnuoc.server.model.PostData.DataBookPostReceived;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

import static android.content.ContentValues.TAG;

public interface GCSAPIInterface {
    @POST("EmpLogin")
    Call<LoginGet> EmpLogin(@Body LoginPost loginPost);

    @POST("GetToken")
    Call<TokenModelServer> GetToken(@Body LoginPost loginPost);

    @Streaming
    @POST("EmpGetBookInfo")
    Call<DataGetModelServer> EmpGetBookInfo(@Body BookInfoPost bookInfoPost);

    @Streaming
    @POST("SaveIndexValue")
    Call<DataBookPostReceived> SaveIndexValue(@Body DataBookPost dataBookPost);



//
//    @GET("Get_d_dviqly")
//    Call<List<D_DVIQLYModel>> Get_d_dviqly();
//
//    @GET("LayDuLieuCmis")
//    Call<List<UpdateStatus>> LayDuLieuCmis(@Query("maDonVi") String maDonVi, @Query("maNhanVien") String maNhanVien);
//
//    @GET("GeT_BBAN")
//    Call<List<MtbBbanModel>> GeT_BBAN(@Query("maDonVi") String maDonVi, @Query("maNhanVien") String maNhanVien);
//
//    @GET("Get_cto")
//    Call<Object> Get_cto(@Query("maDonVi") String maDonVi, @Query("idBienBan") String idBienBan);
//
//    @GET("Get_bban_TUTI")
//    Call<List<MtbBbanTutiModel>> Get_bban_TUTI(@Query("maDonVi") String maDonVi, @Query("maNhanVien") String maNhanVien);
//
//    @GET("Get_TUTI")
//    Call<List<MTB_TuTiModel>> Get_TUTI(@Query("maDonVi") String maDonVi, @Query("idTuTi") String idTuTi);
//
//    @GET("GetTram")
//    Call<List<TRAMVIEW>> GetTram(@Query("maDonViQuanLy") String maDonViQuanLy);
//
//    @GET("LayDuLieuLoaiCongTo")
//    Call<List<CHUNG_LOAI_CONGTO>> LayDuLieuLoaiCongTo();
//
//    @GET("GET_LY_DO_TREO_THAO")
//    Call<List<D_LY_DO_MODEL>> GET_LY_DO_TREO_THAO(@Query("maDonVi") String maDonVi);
//
//    @POST("PostMTBWithImage")
//    Call<List<MTB_ResultModel_NEW>> PostMTBWithImage(@Body List<MTBModelNew> list);
//
//    @POST("ResponseGetSuccess")
//    Call<Boolean> ResponseGetSuccess(@Body ResponseGetSuccessPostRequest request);


    public static class AsyncApi extends AsyncTask<Void, Void, Bundle> {
        IAsync iAsync;

        public AsyncApi(IAsync iAsync) {
            this.iAsync = iAsync;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                iAsync.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "onPreExecute: " + e.getMessage());
                this.cancel(true);
            }
        }

        @Override
        protected Bundle doInBackground(Void... voids) {
            return iAsync.doInBackground();
        }

        @Override
        protected void onPostExecute(Bundle result) {
            super.onPostExecute(result);
            iAsync.onPostExecute(result);
        }


    }

    abstract class IAsync {
        public static final String STATUS_CODE = "STATUS_CODE";
        public static final String BUNDLE_DATA = "BUNDLE_DATA";
        public static final String ERROR_BODY = "ERROR_BODY";

        public abstract void onPreExecute() throws Exception;
        public abstract Bundle doInBackground();
        public abstract void onPostExecute(Bundle result);
    }


//    @POST("/api/users")
//    Call<User> createUser(@Body User user);
//
//    @GET("/api/users?")
//    Call<UserList> doGetUserList(@Query("page") String page);
//
//    @FormUrlEncoded
//    @POST("/api/users?")
//    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);
}