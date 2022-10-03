
package mo.ed.amit.dayten.network.room.model.profiles;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
@Entity(tableName = "Profile")
public class Profile {

    @NonNull
    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    private String mCreatedAt;

    @NonNull
    @ColumnInfo(name = "distance")
    @SerializedName("distance")
    private String mDistance;

    @NonNull
    @ColumnInfo(name = "email")
    @SerializedName("email")
    private String mEmail;

    @ColumnInfo(name = "email_verified_at")
    @SerializedName("email_verified_at")
    private String mEmailVerifiedAt;

    @NonNull
    @ColumnInfo(name = "eta")
    @SerializedName("eta")
    private String mEta;

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Long mId;


    @NonNull
    @ColumnInfo(name = "latitude")
    @SerializedName("latitude")
    private String mLatitude;

    @NonNull
    @ColumnInfo(name = "longitude")
    @SerializedName("longitude")
    private String mLongitude;

    @NonNull
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String mName;

    @NonNull
    @ColumnInfo(name = "password")
    @SerializedName("password")
    private String mPassword;

    @NonNull
    @ColumnInfo(name = "PaymentType")
    @SerializedName("PaymentType")
    private String mPaymentType;

    @NonNull
    @ColumnInfo(name = "PhoneNumber")
    @SerializedName("PhoneNumber")
    private String mPhoneNumber;

    @NonNull
    @ColumnInfo(name = "price")
    @SerializedName("price")
    private String mPrice;

    @NonNull
    @ColumnInfo(name = "ProfilePic")
    @SerializedName("ProfilePic")
    private String mProfilePic;

    @NonNull
    @ColumnInfo(name = "rate")
    @SerializedName("rate")
    private String mRate;

    @NonNull
    @ColumnInfo(name = "TransportType")
    @SerializedName("TransportType")
    private String mTransportType;

    @NonNull
    @ColumnInfo(name = "updated_at")
    @SerializedName("updated_at")
    private String mUpdatedAt;


    @NonNull
    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    @NonNull
    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

    @NonNull
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getEmailVerifiedAt() {
        return mEmailVerifiedAt;
    }

    public void setEmailVerifiedAt(String emailVerifiedAt) {
        mEmailVerifiedAt = emailVerifiedAt;
    }

    @NonNull
    public String getEta() {
        return mEta;
    }

    public void setEta(String eta) {
        mEta = eta;
    }

    @NonNull
    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    @NonNull
    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    @NonNull
    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @NonNull
    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @NonNull
    public String getPaymentType() {
        return mPaymentType;
    }

    public void setPaymentType(String paymentType) {
        mPaymentType = paymentType;
    }

    @NonNull
    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    @NonNull
    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    @NonNull
    public String getProfilePic() {
        return mProfilePic;
    }

    public void setProfilePic(String profilePic) {
        mProfilePic = profilePic;
    }

    @NonNull
    public String getRate() {
        return mRate;
    }

    public void setRate(String rate) {
        mRate = rate;
    }

    @NonNull
    public String getTransportType() {
        return mTransportType;
    }

    public void setTransportType(String transportType) {
        mTransportType = transportType;
    }

    @NonNull
    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }
}
