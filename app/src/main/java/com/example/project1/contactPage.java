package com.example.project1;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class contactPage extends Activity  implements OnClickListener{
	int currentEntry;
	int addEntry = 0;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.contact_page);
	    // TODO Auto-generated method stub
	    
	    Button mBtn1 = (Button) findViewById(R.id.saveBtn);
        mBtn1.setOnClickListener(this);
        Button mBtn2 = (Button) findViewById(R.id.deleteBtn);
        mBtn2.setOnClickListener(this);
        
        Bundle b = getIntent().getExtras();
        if(b != null){
        	int value = b.getInt("key");
        	System.out.println("Row recieved: " + value);
        	//TextView tv1 = (TextView) findViewById(R.id.fName);
            //tv1.setText(Integer.toString(value));
            currentEntry = value;
            
            //Adding new entry
            if(value == -10){
            	addEntry = 1;
            	return;
            }
            System.out.println("value key recieved in onCreate: " + value);
            FileIO fio = new FileIO();
			String data = fio.readFile();
			System.out.println("data recieved in onCreate: " + data);
			if(data == null){System.out.println("data was null in oncreate:");return;}
			String[] separated = data.split(":");
			if(separated[0] == null){System.out.println("separated was null in oncreate:");return;}
			System.out.println("separated length:" + separated.length + " current Entry:" + currentEntry);
			if(separated.length < currentEntry){return;}
			String[] sep2 = separated[currentEntry].split(",");
			if(sep2[0] == null){System.out.println("sep2 was null in oncreate:");return;}
			
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
		// TODO Auto-generated method stub
		//System.out.println("click ID: " + v.getId());
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
