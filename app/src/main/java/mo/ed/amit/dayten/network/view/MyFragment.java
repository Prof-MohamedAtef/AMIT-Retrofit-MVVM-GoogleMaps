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
import mo.ed.amit.dayten.network.R;
import mo.ed.amit.dayten.network.databinding.ActivityMainBinding;
import mo.ed.amit.dayten.network.model.EntriesModelResponse;
import mo.ed.amit.dayten.network.viewmodel.OfferViewModel;

public class MyFragment extends Fragment {

    private OfferViewModel OffersViewModel;
    ActivityMainBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("OnCreate", "OnCreate");
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("OnViewCreated", "OnViewCreated");
        OffersViewModel = new ViewModelProvider(this).get(OfferViewModel.class);
        if (OffersViewModel!=null){
            OffersViewModel.getEntriesList().observe(getViewLifecycleOwner(), new Observer<ArrayList<EntriesModelResponse.Entries>>() {
                @Override
                public void onChanged(ArrayList<EntriesModelResponse.Entries> entries) {
                    for (int i=0; i < entries.size(); i++){
                        Log.e("entries num "+ i +" : ","API: "+entries.get(i).getAPI()+"\n"+"Auth: "+entries.get(i).getAuth()+"\n"+"Category: "+entries.get(i).getCategory()+"\n"+"Link: "+entries.get(i).getLink());
                    }
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.activity_main,container,false);
        Log.e("OnCreateView", "OncreateView");
        return binding.getRoot();
    }

    public static MyFragment newInstance(){
        return new MyFragment();
    }
}