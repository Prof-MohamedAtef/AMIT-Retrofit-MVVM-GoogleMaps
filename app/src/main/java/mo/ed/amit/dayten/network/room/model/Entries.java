package mo.ed.amit.dayten.network.room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "Entries")
public class Entries implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "key")
    private int key;

    @NonNull
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @NonNull()
    @ColumnInfo(name = "Description")
    private String Description;


    @NonNull()
    @ColumnInfo(name = "Category")
    private String Category;

    @NonNull
    @ColumnInfo(name = "HTTPS")
    private Boolean HTTPS;
    // TODO: 9/26/2022
    @NonNull
    @ColumnInfo()
    private String Auth;

    @NonNull
    @ColumnInfo(name = "API")
    private String API;

    @NonNull
    @ColumnInfo(name = "CORS")
    private String Cors;

    @NonNull
    @ColumnInfo(name = "LINK")
    private String Link;

    @NonNull
    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @NonNull
    public String getCategory() {
        return this.Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    @NonNull
    public Boolean getHTTPS() {
        return this.HTTPS;
    }

    public void setHTTPS(Boolean HTTPS) {
        this.HTTPS = HTTPS;
    }

    @NonNull
    public String getAuth() {
        return this.Auth;
    }

    public void setAuth(String Auth) {
        this.Auth = Auth;
    }

    @NonNull
    public String getAPI() {
        return this.API;
    }

    public void setAPI(String API) {
        this.API = API;
    }

    @NonNull
    public String getCors() {
        return this.Cors;
    }

    public void setCors(String Cors) {
        this.Cors = Cors;
    }

    @NonNull
    public String getLink() {
        return this.Link;
    }

    public void setLink(String Link) {
        this.Link = Link;
    }
}