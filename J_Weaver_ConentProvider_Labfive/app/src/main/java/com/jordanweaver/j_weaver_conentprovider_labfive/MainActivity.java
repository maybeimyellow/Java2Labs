package com.jordanweaver.j_weaver_conentprovider_labfive;

import android.app.Activity;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity implements ContentListFragment.UpdateDetails, DetailsFragment.DetailsPageSelector,
        SchoolAddForm.SchoolSelector, ListButtonFragment.ListPageSelector {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getFragmentManager().beginTransaction().replace(R.id.buttonContainer,
                    ListButtonFragment.newInstance(), ListButtonFragment.TAG).commit();
            getFragmentManager().beginTransaction().replace(R.id.listContainer,
                    ContentListFragment.newInstance(), ContentListFragment.TAG).commit();
        }

    }

    @Override
    public void getPosition(int _position) {

        DetailsFragment detailsFrag = (DetailsFragment) getFragmentManager().
                findFragmentByTag(DetailsFragment.TAG);

        ContentResolver resolver = this.getContentResolver();

        String[] columns = new String[]{
                DeVaunteDataContract.ID,
                DeVaunteDataContract.FIRST_NAME,
                DeVaunteDataContract.LAST_NAME,
                DeVaunteDataContract.NAMEOFSCHOOL
        };

        Cursor nameCursor = resolver.query(
                DeVaunteDataContract.DeVaunte_CONTENT_URI, columns, null, null, null
        );

        nameCursor.moveToPosition(_position);

        String firstName = nameCursor.getString(1);
        String lastName = nameCursor.getString(2);
        String school = nameCursor.getString(3);
        int id = nameCursor.getInt(0);

        if (detailsFrag == null) {

            Log.e("School", school);

            getFragmentManager().beginTransaction().replace(R.id.listContainer,
                    DetailsFragment.newInstance(firstName, lastName, school, id), DetailsFragment.TAG).commit();
            findViewById(R.id.listContainer).setVisibility(View.VISIBLE);
        } else {
            detailsFrag.setOnText(firstName, lastName, school);
        }


    }

    @Override
    public void ChangeFrag(String _text) {
        switch (_text){
            case "AddButtonClicked":
                findViewById(R.id.buttonContainer).setVisibility(View.GONE);
                findViewById(R.id.listContainer).setVisibility(View.GONE);
                findViewById(R.id.formContainer).setVisibility(View.VISIBLE);
                getFragmentManager().beginTransaction().replace(R.id.formContainer,
                        SchoolAddForm.newInstance(), SchoolAddForm.TAG).commit();
                break;
            case "SchoolBackButton":
                findViewById(R.id.formContainer).setVisibility(View.GONE);
                findViewById(R.id.listContainer).setVisibility(View.VISIBLE);
                findViewById(R.id.buttonContainer).setVisibility(View.VISIBLE);
                getFragmentManager().beginTransaction().replace(R.id.listContainer,
                        ContentListFragment.newInstance(), ContentListFragment.TAG).commit();
                break;
            case "DetailsBackButton":
                findViewById(R.id.formContainer).setVisibility(View.GONE);
                findViewById(R.id.listContainer).setVisibility(View.VISIBLE);
                findViewById(R.id.buttonContainer).setVisibility(View.VISIBLE);
                getFragmentManager().beginTransaction().replace(R.id.buttonContainer,
                        ListButtonFragment.newInstance(), ListButtonFragment.TAG).commit();
                getFragmentManager().beginTransaction().replace(R.id.listContainer,
                        ContentListFragment.newInstance(), ContentListFragment.TAG).commit();
                break;

        }

    }
}
