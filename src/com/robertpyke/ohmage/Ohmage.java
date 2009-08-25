package com.robertpyke.ohmage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;

public class Ohmage extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ResistorColorList list = new ResistorColorList(this);
        setContentView(list);
    }
    
    private class ResistorColorList extends ListView {

		public ResistorColorList(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			setBackgroundColor(Color.YELLOW);
		}
    	
    }
    
}