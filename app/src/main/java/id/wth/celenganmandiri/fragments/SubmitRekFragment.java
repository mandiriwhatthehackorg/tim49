package id.wth.celenganmandiri.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.gson.JsonObject;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import id.wth.celenganmandiri.R;
import id.wth.celenganmandiri.model.otp.GetOTP;
import id.wth.celenganmandiri.model.submitdata.GetData;
import id.wth.celenganmandiri.network.APIClientBoarding;
import id.wth.celenganmandiri.network.APIRequests;
import id.wth.celenganmandiri.utils.PreferenceManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class SubmitRekFragment extends Fragment {

    LinearLayout ktpLayout, lyic_ktp, lyic_selfi, lyic_ttd, lyt_selfie, lyt_ttd;
    AppCompatEditText edtIbu;
    Button verivyBtn;

    final int CAMERA_REQUEST_KTP = 100;
    final int CAMERA_REQUEST_SELFIE = 110;
    final int CAMERA_REQUEST_TTD = 120;

    APIRequests apiRequests;
    PreferenceManager preferenceManager;
    String token = "";
    private RequestQueue rQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tes, container, false);
        preferenceManager = new PreferenceManager(getActivity());

        ktpLayout =  rootView.findViewById(R.id.lyt_ktp);
        lyt_selfie =  rootView.findViewById(R.id.lyt_selfie);
        lyic_ktp =  rootView.findViewById(R.id.layout_ic_ktp);
        lyic_selfi =  rootView.findViewById(R.id.layout_ic_selfi);
        lyic_ttd =  rootView.findViewById(R.id.layout_ic_ttd);
        lyt_ttd =  rootView.findViewById(R.id.lyt_ttd);
        edtIbu =  rootView.findViewById(R.id.edtIbu);
        verivyBtn =  rootView.findViewById(R.id.verivyBtn);

        ktpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtIbu.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Isikan nama ibu terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    sendData();
                }
            }
        });

        lyt_selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeImageFromCameraSelfie();
            }
        });

        lyt_ttd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeImageFromCameraTTD();
            }
        });

        verivyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.client_content, new AksesFragment(), "NewFragmentTag");
                ft.commit();
            }
        });

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        return rootView;
    }

    public void takeImageFromCameraKtp() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_KTP);
    }

    public void takeImageFromCameraSelfie() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_SELFIE);
    }

    public void takeImageFromCameraTTD() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_TTD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_KTP && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            uploadImage(mphoto, lyic_ktp);
        } else if (requestCode == CAMERA_REQUEST_SELFIE && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            uploadImage(mphoto, lyic_selfi);
        } else if (requestCode == CAMERA_REQUEST_TTD && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            uploadImage(mphoto, lyic_ttd);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    public void getImageDrawable(){
        Bitmap dummybm = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_arrow_forward_white);
        //uploadImage(dummybm);
    }

    private File createTempFile(Bitmap bitmap) {
        File file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() +"_image.jpg");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.WEBP,0, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public void uploadImage(Bitmap gambarbitmap, final LinearLayout layout) {
        HashMap<String, RequestBody> map = new HashMap<>();
        File file = createTempFile(gambarbitmap);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        //Create request body with text description and text media type
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");

        apiRequests = APIClientBoarding.createService(APIRequests.class, "Bearer "+token, true);

        Call<ResponseBody> call = apiRequests.uploadImageKtp(body, description);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "sukses", Toast.LENGTH_SHORT).show();
                    layout.setBackgroundResource(R.drawable.btn_rounded);
                } else {
                    Toast.makeText(getActivity(), "sukses", Toast.LENGTH_SHORT).show();
                    layout.setBackgroundResource(R.drawable.btn_rounded);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });
    }

    private void sendData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("motherName", edtIbu.getText().toString());
        jsonObject.addProperty("productType", "TAB");
        jsonObject.addProperty("cardType", "silver");
        jsonObject.addProperty("branchCode", "1");

        apiRequests = APIClientBoarding.createService(APIRequests.class, "Bearer "+preferenceManager.getPreference("jwt"), true);
        Call<GetData> call = apiRequests.postSubmitData( jsonObject);
        call.enqueue(new Callback<GetData>() {
            @Override
            public void onResponse(Call<GetData> call, Response<GetData> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isSuccess()) {
                        preferenceManager.setPreference("jwt", response.body().getData().getToken());
                        token =  response.body().getData().getToken();
                        takeImageFromCameraKtp();
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<GetData> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("error", t.getMessage().toString());
            }
        });
    }

}
