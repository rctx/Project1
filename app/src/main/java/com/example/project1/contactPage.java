/*
Ryan Carley
rjc074000
3/18/15
Purpose: To allow adding modifying and deleting of contacts
*/

package com.example.project1;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class contactPage extends Activity  implements OnClickListener{
	int currentEntry;
	int addEntry = 0;
	/** Called when the activity is first created. */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        setTitle("Contacts");
        getMenuInflater().inflate(R.menu.main, menu);

        // Hide menu items not needed for this activity
        MenuItem action_new = menu.findItem(R.id.action_new);
        if (action_new != null){action_new.setVisible(false);}
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
            return true;
        }
        if (id == R.id.action_save) {
            System.out.println("Got action_save\n");
            // Find and click the save contact button
            Button mBtn1 = (Button) getWindow().getDecorView().findViewById(R.id.saveBtn);
            if(mBtn1 == null){ return true;} // Just in case it isn't there
            mBtn1.performClick();
            return true;
        }
        if (id == R.id.action_delete) {
            System.out.println("Got action_delete\n");

            // Delete confirmation window
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Delete Contact")
                    .setMessage("Are you sure you want to delete?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.out.println("which: " + which);

                            // Delete the contact if the clicked yes
                            Button mBtn2 = (Button) findViewById(R.id.deleteBtn);
                            if(mBtn2 == null){ return;} // Just in case it isn't there
                            mBtn2.performClick();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();



            // Find and click the delete contact button
            /*Button mBtn2 = (Button) findViewById(R.id.deleteBtn);
            if(mBtn2 == null){ return true;} // Just in case it isn't there
            mBtn2.performClick();*/
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.contact_page);

        // setup save and delete buttons to be used by action bar
	    Button mBtn1 = (Button) findViewById(R.id.saveBtn);
        mBtn1.setVisibility(View.GONE);
        mBtn1.setOnClickListener(this);
        Button mBtn2 = (Button) findViewById(R.id.deleteBtn);
        mBtn2.setVisibility(View.GONE);
        mBtn2.setOnClickListener(this);

        // Grab the data sent in on activity creation
        Bundle b = getIntent().getExtras();
        if(b != null){
        	int value = b.getInt("key");
        	System.out.println("Row recieved: " + value);
        	//TextView tv1 = (TextView) findViewById(R.id.fName);
            //tv1.setText(Integer.toString(value));
            currentEntry = value;
            
            //Adding new entry
            // The Id is set very high so the add new contact button can be identified
            if(value == 99999999){
            	addEntry = 1;
            	return;
            }
            System.out.println("value key recieved in onCreate: " + value);

            // Setup file IO
            FileIO fio = new FileIO();

            // Read in the stored data
			String data = fio.readFile();
			System.out.println("data recieved in onCreate: " + data);

            // Data was null
			if(data == null){System.out.println("data was null in oncreate:");return;}

            // Parse out the data, lots of checking for array out of bounds
			String[] separated = data.split(":");
			if(separated[0] == null){System.out.println("separated was null in oncreate:");return;}
			System.out.println("separated length:" + separated.length + " current Entry:" + currentEntry);
			if(separated.length < currentEntry){return;}
			String[] sep2 = separated[currentEntry].split(",");
			if(sep2.length < 1 || sep2[0] == null){System.out.println("sep2 was null in oncreate:");return;}

            // Parse info for fields
			if(sep2[0] != null){
				TextView tv1 = (TextView) findViewById(R.id.fName);
				tv1.setText(sep2[0]);
			}
			if(sep2[1] != null){
				TextView tv1 = (TextView) findViewById(R.id.lName);
				tv1.setText(sep2[1]);
			}
			if(sep2[2] != null){
				TextView tv1 = (TextView) findViewById(R.id.pNum);
				tv1.setText(sep2[2]);
			}
			if(sep2[3] != null){
				TextView tv1 = (TextView) findViewById(R.id.email);
				tv1.setText(sep2[3]);
			}
            
        }
	}

	@Override
	public void onClick(View v) {
		// Handle Button Clicks
		System.out.println("click ID: " + v.getId());
		switch (v.getId()) {
		case  R.id.saveBtn: {
			//grab new data
			TextView tv1 = (TextView) findViewById(R.id.fName);
			String fname = tv1.getText().toString();
			TextView tv2 = (TextView) findViewById(R.id.lName);
			String lname = tv2.getText().toString();
			TextView tv3 = (TextView) findViewById(R.id.pNum);
			String pnum = tv3.getText().toString();
			TextView tv4 = (TextView) findViewById(R.id.email);
			String email = tv4.getText().toString();
			
			//get existing data
			FileIO fio = new FileIO();
			String data = fio.readFile();
			System.out.println("data recieved in save: " + data);
			
			//editing existing entry
			if(addEntry == 0){
			String[] separated = data.split(":");
			String outData = "";
			for(int i = 0; i < separated.length; i++){
				if(i == currentEntry){
					if( i == 0){
						outData += fname + "," + lname + "," + pnum + "," + email;
					}
					else{
						outData += ":" + fname + "," + lname + "," + pnum + "," + email;
					}
					 
				}
				else{
					if( i == 0){
						outData += separated[i];
					}
					else{
						outData += ":" + separated[i];
					}
				}
				
			}
			//fio.writeFile("test data");
			System.out.println("writing out data in edit entry: " + outData);
			fio.writeFile(outData);
			}
			else{
			//creating new entry
				String outData = "";
				if(data != null && data != ""){
					outData = data + ":"  + fname + "," + lname + "," + pnum + "," + email;
				}else{
					outData = fname + "," + lname + "," + pnum + "," + email;
				}
				System.out.println("writing out data in add new: " + outData);
				fio.writeFile(outData);
				
			}
			setResult(Activity.RESULT_OK);
			finish();
			break;
			
		}
		case  R.id.deleteBtn: {
			//get existing data
			FileIO fio = new FileIO();
			String data = fio.readFile();
			System.out.println("data recieved in delete: " + data);
			//deleting existing entry
			if(addEntry == 0){
				String[] separated = data.split(":");
				String outData = "";
				for(int i = 0; i < separated.length; i++){
					if(i != currentEntry){
						if( i == 0 || (i == 1 && currentEntry == 0)){
							outData += separated[i];
						}
						else{
							outData += ":" + separated[i];
						}
					}
				}
				System.out.println("writing out data in delete entry: " + outData);
				fio.writeFile(outData);
			}
			
			
			setResult(Activity.RESULT_OK);
			finish();
			break;
		}
		}
	}

}
