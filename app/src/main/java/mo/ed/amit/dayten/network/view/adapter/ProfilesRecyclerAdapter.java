package mo.ed.amit.dayten.network.view.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mo.ed.amit.dayten.network.R;
import mo.ed.amit.dayten.network.room.model.profiles.Profile;
import mo.ed.amit.dayten.network.util.Configs;
import mo.ed.amit.dayten.network.util.MapHelper;
import mo.ed.amit.dayten.network.view.MapActivity;

public class ProfilesRecyclerAdapter extends RecyclerView.Adapter<ProfilesRecyclerAdapter.ViewHolder> {

    private final List<Profile> feedItemList;
    private final Context mContext;
    private final boolean mTwoPane;
    private final MapActivity mActivity;
    private Profile profile;

    public ProfilesRecyclerAdapter(Context mContext, List<Profile> items, boolean twoPane, MapActivity activity) {
        this.mContext = mContext;
        this.feedItemList = items;
        this.mTwoPane = twoPane;
        this.mActivity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View articleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_profile_listitem,
                parent, false);
        return new ProfilesRecyclerAdapter.ViewHolder(articleView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder placeViewHolder = (ViewHolder) holder;
        if (feedItemList!=null){
            if (feedItemList != null && feedItemList.size() > 0) {
                profile = feedItemList.get(position);
                if (mContext != null) {
                    TextView username=(TextView) placeViewHolder.itemView.findViewById(R.id.tvUsername);
                    TextView type=(TextView) placeViewHolder.itemView.findViewById(R.id.tvType);
                    RatingBar ratingBar=(RatingBar) placeViewHolder.itemView.findViewById(R.id.ratingBar);
                    TextView distanceVal=(TextView) placeViewHolder.itemView.findViewById(R.id.distanceVal);
                    TextView priceVal=(TextView) placeViewHolder.itemView.findViewById(R.id.tvPriceVal);
                    TextView etaVal=(TextView) placeViewHolder.itemView.findViewById(R.id.tvETAVal);
                    TextView paymentVal=(TextView) placeViewHolder.itemView.findViewById(R.id.tvPaymentVal);

                    ConstraintLayout MyLayout=(ConstraintLayout) placeViewHolder.itemView.findViewById(R.id.MyLayout);
                    MyLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String Lat=profile.getLatitude();
                            String Long=profile.getLongitude();
                            if (Lat!=null&&Long!=null){
                                ((OnProfileSelected)mActivity).onProfileItemSelection(Lat,Long);
                            }
                        }
                    });

                    AppCompatButton btnCall=(AppCompatButton) placeViewHolder.itemView.findViewById(R.id.btnCall);
                    btnCall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String Phone=profile.getPhoneNumber();
                            // TODO: 10/2/2022 show call intent (to make a call ), task 1
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + Phone));
                            if (ContextCompat.checkSelfPermission(mContext,
                                    Manifest.permission.CALL_PHONE)
                                    != PackageManager.PERMISSION_GRANTED) {

                                ActivityCompat.requestPermissions(mActivity,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        Configs.MY_PERMISSIONS_REQUEST_CALL_PHONE);

                                // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                                // app-defined int constant. The callback method gets the
                                // result of the request.
                            } else {
                                //You already have permission
                                try {
                                    mContext.startActivity(callIntent);
                                } catch(SecurityException e) {
                                    e.printStackTrace();
                                }
                            }


                        }
                    });

                    ratingBar.setRating(Float.valueOf( profile.getRate()));
                    distanceVal.setText(profile.getDistance());
                    username.setText(profile.getName());
                    type.setText(profile.getTransportType());
                    priceVal.setText(profile.getPrice());
                    etaVal.setText(profile.getEta());
                    paymentVal.setText(profile.getPaymentType());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return feedItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    public interface OnProfileSelected{
        public void onProfileItemSelection(String latitude, String longitude);
    }
}