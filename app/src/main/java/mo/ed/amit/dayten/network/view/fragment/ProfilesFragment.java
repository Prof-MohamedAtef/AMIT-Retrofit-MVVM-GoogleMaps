package mo.ed.amit.dayten.network.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import mo.ed.amit.dayten.network.R;
import mo.ed.amit.dayten.network.databinding.ProfilesFragmentBinding;
import mo.ed.amit.dayten.network.room.model.profiles.Profile;

public class ProfilesFragment extends Fragment {


    private ProfilesFragmentBinding binding;

    public static ProfilesFragment newInstance(){
        return new ProfilesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater, R.layout.profiles_fragment,container,false);

        return binding.getRoot();
    }
}
