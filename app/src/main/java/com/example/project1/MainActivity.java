//Ryan Carley Solo Project
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
		
		
		for(int i = 0; i< separated.length; i++){ 
			
			System.out.println("separated length:" + separated.length + " i:" + i);
			String[] sep2 = separated[i].split(",");
			if(sep2[0] == null){System.out.println("sep2 was null in oncreate:");return;}
			if(sep2.length < 3){return;}
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
			
			tr1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
		            //v.setBackgroundColor(Color.GRAY);
		            System.out.println("Row clicked: " + v.getId());
		            Intent intent = new Intent(MainActivity.this, contactPage.class);
			        //Bundle b = new Bundle();
			        int vid = v.getId();
			        //b.putInt("key",  vid); //Your id
			        //intent.putExtras(b); //Put your id to your next Intent
			        intent.putExtra("key", vid);
			        startActivityForResult(intent,1);
			        //finish();
		        }
		    });
		}
		TableRow trB = new TableRow(this);
		Button btn=new Button(this);
		btn.setText("Add Person");
		//btn.setId(-10);
		btn.setOnClickListener(this);
		trB.addView(btn);
		tl.addView( trB , new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
        
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
