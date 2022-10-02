package mo.ed.amit.dayten.network.room.model.entries;

import java.io.Serializable;
import java.util.ArrayList;

public class EntityModel implements Serializable {
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

}