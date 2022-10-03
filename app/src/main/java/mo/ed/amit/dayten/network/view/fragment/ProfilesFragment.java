package mo.ed.amit.dayten.network.view.fragment;

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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mo.ed.amit.dayten.network.R;
import mo.ed.amit.dayten.network.databinding.ProfilesFragmentBinding;
import mo.ed.amit.dayten.network.room.model.profiles.Profile;
import mo.ed.amit.dayten.network.util.Configs;
import mo.ed.amit.dayten.network.util.VerifyConnection;
import mo.ed.amit.dayten.network.view.adapter.ProfilesRecyclerAdapter;
import mo.ed.amit.dayten.network.viewmodel.ProfileViewModel;

public class ProfilesFragment extends Fragment {


    private ProfilesFragmentBinding binding;
    private ProfileViewModel profilesViewModel;
    private VerifyConnection verifyConnection;

    public static ProfilesFragment newInstance(){
        return new ProfilesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater, R.layout.profiles_fragment,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profilesViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        verifyConnection=new VerifyConnection(getActivity());
        if(verifyConnection.isConnected()){
            profilesViewModel.getApiProfiles().observe(getViewLifecycleOwner(), new Observer<List<Profile>>() {
                @Override
                public void onChanged(List<Profile> profiles) {
                    if (profiles!=null) {
                        int delete= profilesViewModel.deleteData();
                        if (delete>=0) {
                            for (Profile profile: profiles){
                                long insert= profilesViewModel.insertData(profile);
                                if (insert>0){
                                    Log.e("RecordNumber","inserted: "+insert);
                                }
                            }
                        }

//                        showRecyclerView(profiles);
                        // insert in a Background Thread
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        }).start();
                    }
                }
            });
        }else {
            getOfflineData();
        }
        getOfflineData();
    }

    private void getOfflineData(){
        // get Live Data from Room (Database)
        profilesViewModel.getLiveDataList().observe(getViewLifecycleOwner(), new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                showRecyclerView(profiles);
            }
        });
    }

    private void showRecyclerView(List<Profile> profiles) {
        ProfilesRecyclerAdapter adapter=new ProfilesRecyclerAdapter(getActivity(), profiles, false, Configs.MapActivity);
        adapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvProfiles.setLayoutManager(mLayoutManager);
        binding.rvProfiles.setItemAnimator(new DefaultItemAnimator());
        binding.rvProfiles.setAdapter(adapter);
    }
}