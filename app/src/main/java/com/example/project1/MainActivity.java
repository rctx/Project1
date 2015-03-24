/*
Ryan Carley
rjc074000
3/18/15
Purpose: Main activity that will hold the list of contacts and open the contact entry page.
To have a list on contacts that will be updated a changes are made to the list
*/
package com.example.project1;


import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
//Ryan Carley Solo Project
public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Start test row population
		TableLayout tl=(TableLayout)findViewById(R.id.tableLayout);
		
		//grab existing Data
		FileIO fio = new FileIO();
		String data = fio.readFile();
		System.out.println("data recieved in Main Activity onCreate: " + data);
		if(data == null){System.out.println("data was null in oncreate:");return;}
		String[] separated = data.split(":");
		if(separated[0] == null){System.out.println("separated was null in oncreate:");return;}
		
		// Put data from file IO into the table list
		for(int i = 0; i< separated.length; i++){ 

            // Lots of parsing of data, then putting into the list
			System.out.println("separated length:" + separated.length + " i:" + i);
			String[] sep2 = separated[i].split(",");
            if(sep2.length < 1){continue;}
			if(sep2[0] == null){System.out.println("sep2 was null in oncreate:");continue;}
			if(sep2.length < 3){continue;}
			TextView textview = new TextView(this);
			String entryString = "";
			if(sep2[0] != null){
				entryString += sep2[0];
			}
			if( sep2.length > 0 && sep2[1] != null){
				entryString += " " + sep2[1];
			}
			if(sep2.length > 0 && sep2[2] != null){
				entryString += "   " + sep2[2];
			}
			textview.setText(entryString);
			TableRow tr1 = new TableRow(this);
			textview.setTextSize( 20 );
			//textview.setTextColor(Color.BLUE);
			tr1.addView(textview);
			tr1.setId(i);
			
			tl.addView( tr1 );
			tr1.setClickable(true); 

            // Add action listener for the contact to open the contact editor/viewer
			tr1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
		            System.out.println("Row clicked: " + v.getId());
		            Intent intent = new Intent(MainActivity.this, contactPage.class);
			        int vid = v.getId();
			        intent.putExtra("key", vid);
			        startActivityForResult(intent,1);
		        }
		    });
		}

        // Create button for adding new contacts
		TableRow trB = new TableRow(this);
		Button btn=new Button(this);
		btn.setText("Add Person");

        // This is the correct usage of setId and it works fine (android studio thinks otherwise)
        // see view.java for: public void setId(int id) {
		btn.setId(99999999);
		btn.setOnClickListener(this);
        btn.setVisibility(View.GONE);
		trB.addView(btn);
		tl.addView( trB , new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
        setTitle("Contacts");
		getMenuInflater().inflate(R.menu.main, menu);

        // Hide menu items not needed for this activity
        MenuItem save = menu.findItem(R.id.action_save);
        if (save != null){save.setVisible(false);}
        MenuItem delete = menu.findItem(R.id.action_delete);
        if (delete != null){delete.setVisible(false);}


		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

        // Setup actions, ones that are not available on this page return true

        if (id == R.id.action_new) {
            System.out.println("Got action_new\n");

            // Find and click the new contact button
            Button newButton = (Button)findViewById(99999999);
            newButton.performClick();
            return true;
        }
        if (id == R.id.action_save) {
            return true;
        }
        if (id == R.id.action_delete) {
            return true;
        }
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {

		// A Contact was clicked, open page to view it
        System.out.println("btn clicked: " + v.getId());
        Intent intent = new Intent(MainActivity.this, contactPage.class);
        int vid = v.getId();
        intent.putExtra("key", vid);
        startActivityForResult(intent,1);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		finish();
		startActivity(getIntent());
	}
}
