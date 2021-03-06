package com.example.test_evo_reborn.lockbox;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbH;
    FragmentManager manager;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        final List<LockData> locks = new ArrayList<LockData>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbH = new DatabaseHelper(this);
        showSavedLocks();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog mBuilder = new AlertDialog.Builder(MainActivity.this).create();
                final View mView = getLayoutInflater().inflate(R.layout.input_dialog, null);
                final EditText mLockName = (EditText) mView.findViewById(R.id.lockName);
                final EditText mLockCombination = (EditText) mView.findViewById(R.id.lockCombination);
                mLockCombination.setRawInputType(Configuration.KEYBOARD_QWERTY);
                Button mSubmitButton = (Button) mView.findViewById(R.id.submitButton);
                mLockName.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){
                        mLockName.setText("");
                        mLockName.setTextColor(Color.BLACK);

                    }
                });
                mLockCombination.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){
                        mLockCombination.setText("");
                        mLockCombination.setTextColor(Color.BLACK);
                    }
                });
                mSubmitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextValidator tv = TextValidator.getInstance();
                        String name = mLockName.getText().toString();
                        String combo =  mLockCombination.getText().toString();
                        if(tv.isValidInput(name, combo, getString(R.string.lock_name), getString(R.string.lock_combination))) {
                            LockData temp = new LockData();
                            temp.setLockCombo(combo);
                            temp.setLockName(name);
                            locks.add(temp);
                            int currentId;
                            Cursor savedLocks = dbH.getAllLocks();
                            if(savedLocks.getCount() != 0) {
                                savedLocks.moveToLast();
                                currentId = Integer.parseInt(savedLocks.getString(0));
                                currentId = currentId + 1;
                            }
                            else{
                                currentId = 0;
                            }
                            System.out.println("Current lock# " + currentId);
                            dbH.addLock(name, combo, Integer.toString(currentId));
                            addFragment(name, combo, Integer.toString(currentId));
                            mBuilder.dismiss();

                        }
                        else{
                            Toast toast = Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });



                mBuilder.setView(mView);
                mBuilder.show();


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void addFragment(String name, String combo, String id){
        LockFragment newFragment = new LockFragment();
        final LinearLayout container = (LinearLayout) findViewById(R.id.ll);
        final View newView = newFragment.onCreateView(getLayoutInflater(), container, null);
        FloatingActionButton deleteButton = (FloatingActionButton) newView.findViewById(R.id.floatingActionButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("delete button clicked");
                final AlertDialog confirmD = confirmDelete(newView);
                confirmD.show();
            }
        });
        TextView tvn = (TextView) newView.findViewById(R.id.name);
        tvn.setText(name);
        TextView tvi = (TextView) newView.findViewById(R.id.ID);
        tvi.setText(id);
        TextView tvc = (TextView) newView.findViewById(R.id.combo);
        tvc.setText(combo);
        newView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        ));
        container.addView(newView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showSavedLocks(){
        Cursor savedLocks = dbH.getAllLocks();
        if(savedLocks.getCount() == 0){
            return;
        }
        String name;
        String combo;
        String id;
        while (savedLocks.moveToNext()){
           id = savedLocks.getString(0);
           name = savedLocks.getString(1);
           combo = savedLocks.getString(2);
           addFragment(name, combo, id);
        }
    }

    private AlertDialog confirmDelete(final View newView)
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")


                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        ((LinearLayout)newView.getParent()).removeView(newView);
                        TextView tvi = (TextView) newView.findViewById(R.id.ID);
                        dbH.deleteLock(tvi.getText().toString());
                        dialog.dismiss();
                    }

                })

                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }
}
