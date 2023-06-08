package com.appbishoy.packyourbag.DAO;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.appbishoy.packyourbag.Models.Items;

import java.util.List;

@Dao
public interface ItemsDao {

    @Insert(onConflict = REPLACE)
    void saveItem(Items items);

    @Query("select * from Items where category=:category order by id asc")
    List<Items> getAll(String category);

    @Delete
    void delete(Items items);

    @Query("update Items set checked=:checked where ID=:id")
    void checkUncheck(int id,Boolean checked);

    @Query("select count(*) from Items")
    Integer getItemsCount();

    @Query("delete from Items where addedBy=:addedby")
    int deleteAllSystemItems(String addedby);

    @Query("delete from Items where category=:category")
    int deleteAllByCategory(String category);

    @Query("delete from Items where category=:category and addedBy=:addedby")
    int deleteAllByCategoryAndAddedBy(String category,String addedby);

    @Query("select * from Items where checked=:checked order by id asc")
    List<Items> getAllSelected(Boolean checked);
}
