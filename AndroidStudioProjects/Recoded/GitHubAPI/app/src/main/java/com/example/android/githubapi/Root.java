package com.example.android.githubapi;

        import java.util.ArrayList;

/**
 * Created by muhammad on 10/22/2017.
 */

public class Root {
    private int total_count;
    private ArrayList<Item> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
