package mo.ed.amit.dayten.network.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.List;

import mo.ed.amit.dayten.network.R;
import mo.ed.amit.dayten.network.databinding.ActivityMainBinding;
import mo.ed.amit.dayten.network.room.model.Entries;
import mo.ed.amit.dayten.network.util.VerifyConnection;
import mo.ed.amit.dayten.network.viewmodel.OfferViewModel;

public class MyFragment extends Fragment {

    private OfferViewModel OffersViewModel;
    ActivityMainBinding binding;
    private VerifyConnection connectivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("OnCreateFunction", "OnCreate");
        connectivity= new VerifyConnection(getActivity());
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("OnViewCreated", "OnViewCreated");
        OffersViewModel = new ViewModelProvider(this).get(OfferViewModel.class);
        if (OffersViewModel != null) {
            // TODO: 9/27/2022 checkConnection
            if (connectivity.isConnected()) {
                OffersViewModel.getEntriesList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Entries>>() {
                    @Override
                    public void onChanged(ArrayList<Entries> entries) {
                        for (int i = 0; i < entries.size(); i++) {
                            Log.e("entries num " + i + " : ", "API: " + entries.get(i).getAPI() + "\n" + "Auth: " + entries.get(i).getAuth() + "\n" + "Category: " + entries.get(i).getCategory() + "\n" + "Link: " + entries.get(i).getLink());
                            Entries entry = entries.get(i);
                            // Thread - AsyncTask
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    OffersViewModel.insertData(entry);
                                }
                            }).start();
                        }
                    }
                });
            } else {
                OffersViewModel.getLiveDataList().observe(getViewLifecycleOwner(), new Observer<List<Entries>>() {
                    @Override
                    public void onChanged(List<Entries> entries) {
                        if (entries != null) {
                            if (entries.size() > 0) {
                                // TODO: 9/27/2022 showData on RecyclerView
                            }
                        }
                    }
                });
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment,container,false);
        Log.e("OnCreateView", "OncreateView");
        return binding.getRoot();
    }

    public static MyFragment newInstance(){
        return new MyFragment();
    }
}