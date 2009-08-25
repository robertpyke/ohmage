package com.robertpyke.ohmage;

import android.R.dimen;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ColorListAdapter extends BaseAdapter {
		private Context context = null;
    	private Integer[] colorPool;
    	private OhmageUtil.Res_Band resBand;
    	
    	public ColorListAdapter(Context c, OhmageUtil.Res_Band resBand) {
    		this.resBand = resBand;
    		switch (resBand) {
    		case BAND_1:
    		case BAND_2:
    			colorPool = new Integer[] {
    					R.drawable.black,
    					R.drawable.brown,
    					R.drawable.red,
    					R.drawable.orange,
    					R.drawable.yellow,
    					R.drawable.green,
    					R.drawable.blue,
    					R.drawable.violet,
    					R.drawable.gray,
    					R.drawable.white,
    			};
    			break;
    		case BAND_3:
    			colorPool = new Integer[] {
    					R.drawable.silver,
    					R.drawable.gold,
    					R.drawable.black,
    					R.drawable.brown,
    					R.drawable.red,
    					R.drawable.orange,
    					R.drawable.yellow,
    					R.drawable.green,
    					R.drawable.blue,
    					R.drawable.violet,    					
    			};
    			break;
    		case BAND_4:
    			colorPool = new Integer[] {
    					R.drawable.silver,
    					R.drawable.gold,
    					R.drawable.brown,
    					R.drawable.red,
    					R.drawable.green,
    					R.drawable.blue,
    					R.drawable.violet,
    					R.drawable.gray
    			};
    			break;
    		default:
    			/* A new band was added without updating the colorListAdapter, naughty, naughty.. */
    		}
    		this.context = c;
    	}
    	
		public int getCount() {
			return colorPool.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public Double getResValueOfItem(int position) {
	 		switch (resBand) {
    		case BAND_1:
    		case BAND_2:
    			Integer digit = OhmageUtil.getDigit(colorPool[position]);
    			return new Double(digit);
    		case BAND_3:
    			return OhmageUtil.getMultiplier(colorPool[position]);
    		case BAND_4:
    			return OhmageUtil.getTolerance(colorPool[position]);                
			default:
				return null;
	 		}
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
            // Make an ImageView to show a photo
			TextView tv = new TextView(context);
			Float lr_padding = context.getResources().getDimension(R.dimen.text_padding);
			tv.setPadding(lr_padding.intValue(), 0, lr_padding.intValue(), 0);
            tv.setTextSize(context.getResources().getDimension(R.dimen.co_b_text_size));
            OhmageUtil.setBandColor(tv, colorPool[position]);            
            
            return tv;
		}        
				
    }    
