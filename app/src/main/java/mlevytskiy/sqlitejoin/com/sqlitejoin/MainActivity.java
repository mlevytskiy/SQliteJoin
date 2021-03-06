package mlevytskiy.sqlitejoin.com.sqlitejoin;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import mlevytskiy.sqlitejoin.com.sqlitejoin.database.DAO;
import mlevytskiy.sqlitejoin.com.sqlitejoin.vo.Field;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DAO dao = new DAO(this);
        List<Field> result =  dao.getAll();
        Log.i("MainActivity", ""+result.size());
        Field field = new Field();
        field.setA("test");
        field.setB("testC");
        dao.add(field);
        result = dao.getAll();
        Field testField = dao.searchFieldForId("1");
        Log.i("MainActivity", ""+result.size() + "field.b=" + testField.getB());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
