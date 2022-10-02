package mo.ed.amit.dayten.network.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import mo.ed.amit.dayten.network.R;
import mo.ed.amit.dayten.network.databinding.ProfilesFragmentBinding;
import mo.ed.amit.dayten.network.room.model.profiles.Profile;
import mo.ed.amit.dayten.network.viewmodel.ProfileViewModel;

public class ProfilesFragment extends Fragment {


    private ProfilesFragmentBinding binding;
    private ProfileViewModel profilesViewModel;

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
        profilesViewModel.getApiProfiles().observe(getViewLifecycleOwner(), new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                if (profiles!=null){
                    for (Profile profile : profiles){
                        Log.e("Profiles", profile.getEmail()+" "+profile.getName());
                    }
                }
            }
        });
    }
}
