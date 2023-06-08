package com.appbishoy.packyourbag.Data;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.appbishoy.packyourbag.Constants.MyConstants;
import com.appbishoy.packyourbag.Database.RoomDB;
import com.appbishoy.packyourbag.Models.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppData extends Application {

    RoomDB database;
    String category;
    Context context;
    public static final String LAST_VERSION = "LAST_VERSION";
    public static final int NEW_VERSION = 1;

    public AppData(RoomDB database) {
        this.database = database;
    }

    public AppData(RoomDB database, Context context) {
        this.database = database;
        this.context = context;
    }

    public List<Items> getBasicData(){
        category = "Basic Needs";
        List<Items> basicItem = new ArrayList<>();
        basicItem.add(new Items("Visa",category,false));
        basicItem.add(new Items("Passport",category,false));
        basicItem.add(new Items("Tickets",category,false));
        basicItem.add(new Items("Wallet",category,false));
        basicItem.add(new Items("Driving License",category,false));
        basicItem.add(new Items("Currency",category,false));
        basicItem.add(new Items("Mouse Key",category,false));
        basicItem.add(new Items("Book",category,false));
        basicItem.add(new Items("Travel Pillow",category,false));
        basicItem.add(new Items("Eva Patch",category,false));
        basicItem.add(new Items("Umbrella",category,false));
        basicItem.add(new Items("Not Book",category,false));

        return basicItem;
    }

    public List<Items> getPersonalCareData()
    {
        String[] data = {"Tooth-brush","Tooth-paste","Floss","Fiber","Suntan-cream","Brush","Com"};
        return prepareItemsList(MyConstants.PERSONAL_CARE_CAMEL_CASE,data);
    }
    public List<Items> getClothingData()
    {
        String[] data = {"T-Shirts","Jacket","Jeans","Shorts","Suit","Vest","Shirt","Slipper","Belt"};
        return prepareItemsList(MyConstants.CLOTHING_CAMEL_CASE,data);
    }
    public List<Items> getBabyNeedsData()
    {
        String[] data = {"SnapSuit","Outfit","Baby Socks","Baby Hat","JumpSuit","Baby Bottles","Baby soap","Toys","Baby Radio"};
        return prepareItemsList(MyConstants.BABY_NEEDS_CAMEL_CASE,data);
    }
    public List<Items> getFoodData()
    {
        String[] data = {"Snacks","Sandwich","Juice","Coffee","Water","Tea Bags","Chips","Thermos","Baby Food"};
        return prepareItemsList(MyConstants.FOOD_CAMEL_CASE,data);
    }

    public List<Items> getBeachSuppliesData()
    {
        String[] data = {"Sea Bed","Suntan Cream","Swim Fins","Beach Bag","Sunbed","Snorkel","","Beach Slippers","Waterproof Clock"};
        return prepareItemsList(MyConstants.BEACH_SUPPLIES_CAMEL_CASE,data);
    }
    public List<Items> getCarSuppliesData()
    {
        String[] data = {"Pum","Carjack","Spare Car Key","CarCover","Car Charger","Window Sun Shades","Accident Record set","Auto Refrigerator"};
        return prepareItemsList(MyConstants.CAR_SUPPLIES_CAMEL_CASE,data);
    }
    public List<Items> getNeedsData()
    {
        String[] data = {"BackBag","Daily Bags","Sewing Kit","Laundry Bag","Travel Lock","luggage Tag","Magazine","Important Numbers","Sports Equipment"};
        return prepareItemsList(MyConstants.NEEDS_CAMEL_CASE,data);
    }

    public List<Items> getTechnologyData()
    {
        String[] data = {"Mobile Phone","Phone Cover","Camera","Camera Charger","IPad (Tab)","Laptop","Laptop Charger","Mouse","Headphone","Power bank","Battery"};
        return prepareItemsList(MyConstants.TECHNOLOGY_CAMEL_CASE,data);
    }
    public List<Items> getHealsData()
    {
        String[] data = {"Aspirin","Drugs Used","Vitamins Used","Lens Solutions","Hot Water Bag","Adhesive Plaster","First Aid Hit","Replacement Lens","Pain Relieve Spray"};
        return prepareItemsList(MyConstants.HEALTH_CAMEL_CASE,data);
    }
    public List<Items> prepareItemsList(String category , String [] data){
        List<String> list = Arrays.asList(data);
        List<Items> dataList = new ArrayList<>();
        dataList.clear();

        for (int i=0;i<list.size();i++)
        {
            dataList.add(new Items(list.get(i),category,false));
        }
        return dataList;
    }
    public List<List<Items>> getAllData(){
        List<List<Items>> listOfAllItems = new ArrayList<>();
        listOfAllItems.clear();
        listOfAllItems.add(getBasicData());
        listOfAllItems.add(getClothingData());
        listOfAllItems.add(getPersonalCareData());
        listOfAllItems.add(getBabyNeedsData());
        listOfAllItems.add(getHealsData());
        listOfAllItems.add(getTechnologyData());
        listOfAllItems.add(getFoodData());
        listOfAllItems.add(getBeachSuppliesData());
        listOfAllItems.add(getCarSuppliesData());
        listOfAllItems.add(getNeedsData());
        return listOfAllItems;
    }

    public void persistAllData(){
        List<List<Items>> listOfAllItems = getAllData();
        for (List<Items> list : listOfAllItems)
        {
            for (Items items : list)
            {
                database.mainDao().saveItem(items);
            }
        }
        System.out.println("Data added");
    }

    public void persistDataByCategory(String category,Boolean onlyDelete){
        try {
            List<Items> list = deleteAndGetListByCategory(category,onlyDelete);
            if(!onlyDelete){
                for (Items item : list){
                    database.mainDao().saveItem(item);
                }
                Toast.makeText(context, category+" Rest Successfully", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, category+" Rest Successfully", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }
    private List<Items> deleteAndGetListByCategory(String category,Boolean onlyDelete){
        if(onlyDelete)
        {
            database.mainDao().deleteAllByCategoryAndAddedBy(category,MyConstants.SYSTEM_SMALL);
        }else {
            database.mainDao().deleteAllByCategory(category);
        }

        switch (category){
            case MyConstants.BASIC_NEEDS_CAMEL_CASE:
                return getBasicData();
            case MyConstants.CLOTHING_CAMEL_CASE:
                return getClothingData();
            case MyConstants.PERSONAL_CARE_CAMEL_CASE:
                return getPersonalCareData();
            case MyConstants.BABY_NEEDS_CAMEL_CASE:
                return getBabyNeedsData();
            case MyConstants.HEALTH_CAMEL_CASE:
                return getHealsData();
            case MyConstants.TECHNOLOGY_CAMEL_CASE:
                return getTechnologyData();
            case MyConstants.FOOD_CAMEL_CASE:
                return getFoodData();
            case MyConstants.CAR_SUPPLIES_CAMEL_CASE:
                return getCarSuppliesData();
            case MyConstants.NEEDS_CAMEL_CASE:
                return getNeedsData();
            default:
                return new ArrayList<>();
        }
    }
}
