/**
 * 
 */
package com.example.project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

import android.os.Environment;

/**
 * @author Ryan
 *
 */
public class FileIO {

	/**
	 * 
	 */
	public FileIO() {
		// TODO Auto-generated constructor stub
	}
	public void writeFile(String data){
		File file = null;
	    PrintWriter pw = null;
	    int linect = 0;
	    Scanner s = null;
	    boolean bExists;
	    
	    String strFilename = "MyMsgs.txt";
	    try
	      {
	    	File newFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"MsgFolder");
	    	if (!newFolder.exists())
	        {
	    		bExists = newFolder.mkdirs();
	        }
	    	try
	        {
	    		file = new File(newFolder, strFilename);
	    		file.createNewFile();
	        }
	    	catch (Exception ex)
	        {
	    		System.out.println("ex: " + ex);
	        }
	      }
	    catch (Exception e)
	      {
	    	System.out.println("e: " + e);
	      }
	    if (!file.canWrite())
	      {
	    	System.out.println("Can't write");
	      }
	    try
	      {
	    	pw = new PrintWriter(file);
	    	pw.write(data);
	    	pw.close();
	      }
	    catch (Exception ex)
	      {
	    	System.out.println("Error creating PW: " + ex.getMessage());
	      }
		
	}
	
	public String readFile(){
		File file = null;
		System.out.println("In readFile");
		
		int linect = 0;
	    Scanner s = null;
	    boolean bExists;
	    FileInputStream fis = null;
	    BufferedReader bfr = null;
	    
	    String strFilename = "MyMsgs.txt";
	    try
	      {
	    	File newFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"MsgFolder");
	    	if (!newFolder.exists())
	        {
	    		bExists = newFolder.mkdirs();
	        }
	    	try
	        {
	    		file = new File(newFolder, strFilename);
	    		file.createNewFile();
	        }
	    	catch (Exception ex)
	        {
	    		System.out.println("ex: " + ex);
	        }
	      }
	    catch (Exception e)
	      {
	    	System.out.println("e: " + e);
	      }
		
	    if (!file.canRead())
	      {
	    	System.out.println("Can't read");
	      }
	    try
	      {
	    	System.out.println("In readFile try");
	    	fis = new FileInputStream(file);
	    	bfr = new BufferedReader(new InputStreamReader(fis));
	    	String x = "";
	    	x = bfr.readLine();
	    	String total = "";

	    	while(x!= null){
		    	total += x ;
		    	x = bfr.readLine();
	    	}
	    	bfr.close();
	    	return total;

	      }
	    catch (Exception ex)
	      {
	    	System.out.println("Error creating PW: " + ex.getMessage());
	      }
		
		return null;
		
	}

}
