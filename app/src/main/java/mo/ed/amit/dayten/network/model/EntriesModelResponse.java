package mo.ed.amit.dayten.network.model;

import java.io.Serializable;
import java.util.ArrayList;

public class EntriesModelResponse implements Serializable {
    private ArrayList<Entries> entries;

    private Integer count;

    public ArrayList<Entries> getEntries() {
        return this.entries;
    }

    public void setEntries(ArrayList<Entries> entries) {
        this.entries = entries;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public static class Entries implements Serializable {
        private String Description;

        private String Category;

        private Boolean HTTPS;

        private String Auth;

        private String API;

        private String Cors;

        private String Link;

        public String getDescription() {
            return this.Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getCategory() {
            return this.Category;
        }

        public void setCategory(String Category) {
            this.Category = Category;
        }

        public Boolean getHTTPS() {
            return this.HTTPS;
        }

        public void setHTTPS(Boolean HTTPS) {
            this.HTTPS = HTTPS;
        }

        public String getAuth() {
            return this.Auth;
        }

        public void setAuth(String Auth) {
            this.Auth = Auth;
        }

        public String getAPI() {
            return this.API;
        }

        public void setAPI(String API) {
            this.API = API;
        }

        public String getCors() {
            return this.Cors;
        }

        public void setCors(String Cors) {
            this.Cors = Cors;
        }

        public String getLink() {
            return this.Link;
        }

        public void setLink(String Link) {
            this.Link = Link;
        }
    }
}