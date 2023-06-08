package com.appbishoy.packyourbag.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Items")
public class Items implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID=0;

    //@ColumnInfo(name = "itemname")
    @NonNull
    String itemName;

    //@ColumnInfo(name = "category")
    @NonNull
    String category;

    //@ColumnInfo(name = "addedby")
    @NonNull
    String addedBy;

    //@ColumnInfo(name = "checked")
    Boolean checked;

    public Items() {
    }

    public Items(@NonNull String itemName, @NonNull String category, @NonNull String addedBy, Boolean checked) {
        this.itemName = itemName;
        this.category = category;
        this.addedBy = addedBy;
        this.checked = checked;
    }

    public Items(@NonNull String itemName, @NonNull String category, Boolean checked) {
        this.addedBy = "system";
        this.itemName = itemName;
        this.category = category;
        this.checked = checked;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @NonNull
    public String getItemName() {
        return itemName;
    }

    public void setItemName(@NonNull String itemName) {
        this.itemName = itemName;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    @NonNull
    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(@NonNull String addedBy) {
        this.addedBy = addedBy;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
