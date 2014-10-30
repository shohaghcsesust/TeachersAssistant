package com.comboyz.abc;



import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Edit extends Activity {
	String option;
	
	String[] Days =
    {"Class Routine",
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday"
    };
	
	String w="";
	Cursor c;
	static final int TIME_DIALOG_ID=0;

	 SQLiteDatabase db;
	 Button save,settime;
	 int mHour,mMinute,mDay;
    
	 
	 TextView aftersettime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classroutine);
		Intent intent = getIntent();
		option=intent.getStringExtra("option");
		
		ArrayAdapter<String> adapter = 
	        new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,Days);
		AutoCompleteTextView select_day = (AutoCompleteTextView)findViewById(R.id.Cr_day);
		 select_day.setThreshold(1);
		 select_day.setAdapter(adapter);

		 
	        
	       aftersettime=(TextView) findViewById(R.id.after_setting_time);
	   save=(Button)findViewById(R.id.btn_Cr_save);
	   settime=(Button) findViewById(R.id.btn_Cr_set_time);
	   
	   //creating database
	   try{
		      db=openOrCreateDatabase("Teachersassistance",SQLiteDatabase.CREATE_IF_NECESSARY,null);
		      db.execSQL("Create Table if not exists tblcr(cr_serial INTEGER PRIMARY KEY AUTOINCREMENT,cr_type text, cr_course_no text,cr_course_name text,room text, cr_batch text,cr_hour integer,cr_minute integer,cr_day integer,mss text,s_mss text,cr_phone text)");
		      
	   
		      
	   
	   }catch(SQLException e)
		      {
		    	  Toast.makeText(Edit.this, "DB ERROR", Toast.LENGTH_SHORT).show();
		      }
	   
	   
	   settime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				 showDialog(TIME_DIALOG_ID);
				
			}});
	   
	   
	   
	   //saving the values into database by clicking the save button
	   save.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v)
		{
			
			//dialog after clicking the save button
			
			
			AlertDialog.Builder builder=new AlertDialog.Builder(Edit.this);
			builder.setTitle("Save");
			builder.setMessage("Are you sure you want to save");
			builder.setCancelable(false);
			
			// specify the yes dialog with its listener
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					EditText cr_course_no=(EditText) findViewById(R.id.EditC_No);
					EditText cr_room=(EditText) findViewById(R.id.EditCr_room);
					EditText cr_course_name=(EditText) findViewById(R.id.EditCr_Name);
					EditText cr_batch=(EditText) findViewById(R.id.EditCrBatch);
					AutoCompleteTextView select_day = (AutoCompleteTextView)findViewById(R.id.Cr_day);
					
				
					
					  ContentValues values=new ContentValues();
					  
					  int f=0,m=15;
					if((mMinute-m)<0){
						m=m-mMinute;
					mMinute=60-m;f=1;}
					else
						mMinute=mMinute-m;
					if(f==1){mHour--;
					if(mHour<0){mHour=23;mDay--;}}
					  
					  
					  int i=0;
					  for(i=1;i<=7;i++)
						  if(select_day.getText().toString().equals(Days[i])){mDay=i;break;}
					  
					  
					  
					  
				        
				        
				        
				        
					  
					  if(option.equals("Edit Class")){
					   values.put("cr_type","Class");
					   values.put("mss","Sir,  How are you?  you have a class  named "+cr_course_name.getText().toString()+" with "+cr_batch.getText().toString()+" Batch,    after 15 minutes from now,    at Room no "+cr_room.getText()+".   Best of Luck sir. ".toString());
					   values.put("s_mss","Your class  named "+cr_course_name.getText().toString()+" has been suspended.");
					   c=db.rawQuery("SELECT * FROM tblcr where cr_type='Class' and cr_course_no='"+cr_course_no.getText().toString()+"'",null);
					  }
					  else if(option.equals("Edit Lab")){
						   values.put("cr_type","Lab");
						   values.put("mss","Sir,  How are you?  you have a lab  named "+cr_course_name.getText().toString()+" with "+cr_batch.getText().toString()+" Batch,    after 15 minutes from now,    at Room no "+cr_room.getText()+".   Best of Luck sir. ".toString());
						   values.put("s_mss","Your Lab  named "+cr_course_name.getText().toString()+" has been suspended.");
						   c=db.rawQuery("SELECT * FROM tblcr where cr_type='Lab' and cr_course_no='"+cr_course_no.getText().toString()+"'",null);
					  }
					  
					  else if(option.equals("Edit exam")){
						   values.put("cr_type","Exam");
						   values.put("mss","Sir,  How are you?  you have a Examination  named "+cr_course_name.getText().toString()+" with "+cr_batch.getText().toString()+" Batch,    after 15 minutes from now,    at Room no "+cr_room.getText()+".   Best of Luck sir. ".toString());
						   values.put("s_mss","Your Examination  named "+cr_course_name.getText().toString()+" has been suspended.");
						   c=db.rawQuery("SELECT * FROM tblcr where cr_type='Exam' and cr_course_no='"+cr_course_no.getText().toString()+"'",null);
					  }
					  
					  else if(option.equals("Edit class Test")){
						   values.put("cr_type","Class_Test");
						   values.put("mss","Sir,  How are you?  you have a class Test  named "+cr_course_name.getText().toString()+" with "+cr_batch.getText().toString()+" Batch,    after 15 minutes from now,    at Room no "+cr_room.getText()+".   Best of Luck sir. ".toString());
						   values.put("s_mss","Your class test  named "+cr_course_name.getText().toString()+" has been suspended.");
						   c=db.rawQuery("SELECT * FROM tblcr where cr_type='Class_Test' and cr_course_no='"+cr_course_no.getText().toString()+"'",null);
					  }
					  
					  
					  
					  
					  
					  c.moveToFirst();
				        while(!c.isAfterLast())
				        {
				        	w=c.getString(c.getColumnIndex("cr_course_no"));
				        	c.moveToNext();
				        }
				        c.close();
					  
					   values.put("cr_course_no", cr_course_no.getText().toString());
			           values.put("cr_course_name", cr_course_name.getText().toString());
			           values.put("room", cr_room.getText().toString());
			           values.put("cr_batch", cr_batch.getText().toString()); 
			           values.put("cr_hour",mHour);
			           values.put("cr_minute",mMinute);
			           values.put("cr_day",mDay);
			           values.put("cr_phone","");
			           
			           
			           if(((db.update("tblcr", values, "cr_course_no='"+cr_course_no.getText().toString()+"'", null))!=-1)&&!w.equals(""))
			           {
			           Toast.makeText(getApplicationContext(), "Record Successfully Updated", Toast.LENGTH_SHORT).show();
			           }
			           else
			           {
			           Toast.makeText(getApplicationContext(), "update Error,Please input correct information and try again", Toast.LENGTH_SHORT).show();
			           }
			           
			         
			           cr_course_no.setText("");
			           cr_course_name.setText("");
			           cr_batch.setText("");
			           select_day.setText("");
			           
			          
					
					
				}
			}); 
			
			
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					
				}
			});
			
			
			AlertDialog alert =builder.create();
			alert.show();
			
	           
	           
	           
			
		}
	});
	   
	   
	   
	   
	}
    private TimePickerDialog.OnTimeSetListener mtimeSetListener=new  
    TimePickerDialog.OnTimeSetListener() {         
public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

mHour=hourOfDay;
mMinute=minute;
aftersettime.setText(mHour+" : "+mMinute);
}
};
	
	
	protected Dialog onCreateDialog(int id)
    {
      switch(id)
      {
        case TIME_DIALOG_ID:
           return new TimePickerDialog(this,mtimeSetListener,mHour,mMinute,false);
      }
      return null;      
    }  
	
	
	
	
	
	protected void onStop() {
	      // TODO Auto-generated method stub
	      db.close();
	      super.onStop();
	    }  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
