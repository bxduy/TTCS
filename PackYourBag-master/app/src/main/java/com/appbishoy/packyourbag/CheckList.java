package com.appbishoy.packyourbag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appbishoy.packyourbag.Adapter.CheckListAdapter;
import com.appbishoy.packyourbag.Constants.MyConstants;
import com.appbishoy.packyourbag.Data.AppData;
import com.appbishoy.packyourbag.Database.RoomDB;
import com.appbishoy.packyourbag.Models.Items;

import java.util.ArrayList;
import java.util.List;

public class CheckList extends AppCompatActivity {

    RecyclerView recyclerView;
    CheckListAdapter checkListAdapter;
    RoomDB roomDB;
    List<Items> itemsList;
    String header , show;

    EditText editText;
    Button button;
    LinearLayout linearLayout;

    public final static int REQ_CODE_FOR_MY_SELECTION = 101;

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {

        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu_one,menu);

        if(MyConstants.MY_SELECTIONS.equals(header)){
            menu.getItem(0).setVisible(false);
            menu.getItem(2).setVisible(false);
            menu.getItem(3).setVisible(false);
        }else if(MyConstants.MY_LIST_CAMEL_CASE.equals(header)){
            menu.getItem(1).setVisible(false);
        }

        MenuItem menuItem = menu.findItem(R.id.btnSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Items> nFinalList = new ArrayList<>();
                for (Items items:itemsList){
                    if(items.getItemName().toLowerCase().startsWith(newText.toLowerCase())){
                        nFinalList.add(items);
                    }
                }
                updateRecyclerView(nFinalList);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this,CheckList.class);
        AppData appData = new AppData(roomDB,this);

        switch (item.getItemId()){
            case R.id.btnMySelection:
                intent.putExtra(MyConstants.HEADER_SMALL,MyConstants.MY_SELECTIONS);
                intent.putExtra(MyConstants.SHOW_SMALL,MyConstants.FALSE_STRING);
                startActivityForResult(intent,REQ_CODE_FOR_MY_SELECTION);
                return true;
            case R.id.btnCustomList:
                intent.putExtra(MyConstants.HEADER_SMALL,MyConstants.MY_LIST_CAMEL_CASE);
                intent.putExtra(MyConstants.SHOW_SMALL,MyConstants.TRUE_STRING);
                startActivity(intent);
                return true;
            case R.id.btnDeleteDefault:
                new AlertDialog.Builder(this)
                        .setTitle("Delete default data")
                        .setMessage("Are you sure ?\nAs this will delete the data provided by ( Pack Your Bag ) while installing.")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                appData.persistDataByCategory(header,true);
                                itemsList = roomDB.mainDao().getAll(header);
                                updateRecyclerView(itemsList);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(), "Canceled", Toast.LENGTH_SHORT).show();
                            }
                        }).setIcon(R.drawable.ic_warning)
                        .show();
                return true;
            case R.id.btnReset:
                new AlertDialog.Builder(this)
                        .setTitle("Reset to default")
                        .setMessage("Are you sure ?\nAs this will load the default data provided by ( Pack Your Bag )\nAnd will delete the custom data you have added ( "+header+" )")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                appData.persistDataByCategory(header,false);
                                itemsList = roomDB.mainDao().getAll(header);
                                updateRecyclerView(itemsList);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(), "Canceled", Toast.LENGTH_SHORT).show();
                            }
                        }).setIcon(R.drawable.ic_warning)
                        .show();
                return true;
            case R.id.btnAboutUs:
                intent = new Intent(this,AboutUs.class);
                startActivity(intent);
                return true;
            case R.id.btnExit:
                this.finishAffinity();
                Toast.makeText(this, "Pack Your Bag\nExit Completed", Toast.LENGTH_SHORT).show();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE_FOR_MY_SELECTION&& requestCode==RESULT_OK) {
            itemsList = roomDB.mainDao().getAll(header);
            updateRecyclerView(itemsList);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        header = intent.getStringExtra(MyConstants.HEADER_SMALL);
        show = intent.getStringExtra(MyConstants.SHOW_SMALL);

        getSupportActionBar().setTitle(header);

        editText = findViewById(R.id.et_add);
        button = findViewById(R.id.btn_add);
        linearLayout = findViewById(R.id.linear_layout2);
        recyclerView = findViewById(R.id.recycler_view2);

        roomDB = RoomDB.getInstance(this);

        if(MyConstants.FALSE_STRING.equals(show)){
            linearLayout.setVisibility(View.GONE);
            itemsList = roomDB.mainDao().getAllSelected(true);
        }else {
            itemsList = roomDB.mainDao().getAll(header);
        }
        updateRecyclerView(itemsList);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = editText.getText().toString();
                if(itemName!=null&& !itemName.isEmpty()){
                    addNewItem(itemName);
                    Toast.makeText(CheckList.this, "Item Added", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(CheckList.this, "Empty cannot be added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void addNewItem(String itemName){
        Items items = new Items();
        items.setChecked(false);
        items.setCategory(header);
        items.setItemName(itemName);
        items.setAddedBy(MyConstants.USER_SMALL);
        roomDB.mainDao().saveItem(items);
        itemsList = roomDB.mainDao().getAll(header);
        updateRecyclerView(itemsList);
        recyclerView.scrollToPosition(checkListAdapter.getItemCount()-1);
        editText.setText("");
    }
    private void updateRecyclerView(List<Items> itemsList){
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        checkListAdapter = new CheckListAdapter(CheckList.this,itemsList,roomDB,show);
        recyclerView.setAdapter(checkListAdapter);

    }
}