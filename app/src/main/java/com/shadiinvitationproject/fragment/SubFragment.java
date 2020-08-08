package com.shadiinvitationproject.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shadiinvitationproject.R;
import com.shadiinvitationproject.adapters.SubFragmentAdapter;
import com.shadiinvitationproject.db.DatabaseAdapter;
import com.shadiinvitationproject.network.Retrofit.RFInterface;
import com.shadiinvitationproject.network.Retrofit.ResponseModels.MatchResponse;
import com.shadiinvitationproject.network.Retrofit.ResponseModels.Result;
import com.shadiinvitationproject.network.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;

public class SubFragment extends Fragment implements SubFragmentAdapter.OnItemClickListener {

    // DataBase Connecter(Connect With Database)
    private DatabaseAdapter databaseAdapter;

    //recycleview
    private RecyclerView recyclerView;
    private SubFragmentAdapter subFragmentAdapter;
    private EditText editsearch;
    private String position;

    private List<Result> resultsAll = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.sub_fragment, container, false);

        position = getArguments().getString("myposition");

        // create a instance of SQLite Database
        databaseAdapter = new DatabaseAdapter(getActivity());

        recyclerView = root.findViewById(R.id.recyclerSubFragment);
        editsearch = root.findViewById(R.id.etSearch);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


        databaseAdapter.open();
        resultsAll = databaseAdapter.getAllRecords(position);
        databaseAdapter.close();

        if (position.equals("0")) {
            //remote
            new DownloadMatchResponse(getString(R.string.base_url)).execute("");
            editsearch.setVisibility(View.GONE);
        } else {
            //local
            initRecycleView(resultsAll);
            editsearch.setVisibility(View.VISIBLE);
        }

        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                subFragmentAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });

        return root;
    }

    @Override
    public void onDeclineClick(Result result) {
        databaseAdapter.open();
        databaseAdapter.deleteRecord(result);
        databaseAdapter.close();
        subFragmentAdapter.notifyData(result);
        toast("Decline " + result.getName().getFirst());
    }

    @Override
    public void onAcceptClick(Result result) {
        databaseAdapter.open();
        databaseAdapter.updateStatus(result);
        databaseAdapter.close();
        subFragmentAdapter.notifyData(result);
        toast("Accepted " + result.getName().getFirst());
    }

    @Override
    public void onRemoveClick(Result result) {
        databaseAdapter.open();
        databaseAdapter.deleteRecord(result);
        databaseAdapter.close();
        subFragmentAdapter.notifyData(result);
        toast("Removed " + result.getName().getFirst());
    }

    @Override
    public void onImageClick(String strURL) {
        showDialog(strURL);
    }

    class DownloadMatchResponse extends AsyncTask<String, String, MatchResponse> {

        private ProgressDialog uploadingDialog;
        private RFInterface rfInterface;

        DownloadMatchResponse(String baseUrl) {
            rfInterface = Utility.getRetrofitInterface(baseUrl);
            uploadingDialog = new ProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            uploadingDialog.setMessage("Loading,Please wait...");
            uploadingDialog.setCancelable(false);
            uploadingDialog.show();
        }

        @Override
        protected MatchResponse doInBackground(String... strings) {
            try {

                Response<MatchResponse> matchResponse = rfInterface.getMatchResponse(10).execute();

                if (matchResponse.isSuccessful()) {
                    if (matchResponse.body() != null) {
                        return matchResponse.body();
                    }
                } else {
                    matchResponse.errorBody();
                }

            } catch (Exception e) {
                System.out.println("Exception " + e);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(MatchResponse matchResponse) {
            super.onPostExecute(matchResponse);
            uploadingDialog.dismiss();
            if (matchResponse != null) {
                databaseAdapter.open();
                for (Result result : matchResponse.getResults()) {
                    databaseAdapter.insertRecord(result);
                    resultsAll.add(result);
                }
                databaseAdapter.close();
                initRecycleView(resultsAll);
                //toast("success");
            } else {
                toast("failed");
            }
        }
    }

    public void showDialog(String strUrl){
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(getLayoutInflater().inflate(R.layout.image_layout
                , null));

        ImageView imageView = dialog.findViewById(R.id.imDialog);

        Picasso.get()
                .load(strUrl)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(imageView);

        dialog.show();
    }

    public void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


    public void initRecycleView(List<Result> results) {

        subFragmentAdapter = new SubFragmentAdapter(results, this,position);
        recyclerView.setAdapter(subFragmentAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (databaseAdapter != null)
            databaseAdapter.close();
    }

}