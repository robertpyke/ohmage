package com.robertpyke.ohmage;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.robertpyke.ohmage.R.id;
import com.robertpyke.ohmage.R.string;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView.Validator;

public class Ohmage extends TabActivity {

	
	private Map<OhmageUtil.Res_Band, Double> resToValueMap;
	private Map<OhmageUtil.Res_Band, ColorListAdapter> resToColorList;
	
	private AutoCompleteTextView ocOhmValueTextInput;
	private TextView coOhmValueTextOutput;
	private Spinner ocUnitSpinner;
	private Spinner ocToleranceSpinner;
	private Spinner coBand1Spinner;
	private Spinner coBand2Spinner;
	private Spinner coBand3Spinner;
	private Spinner coBand4Spinner;
	
	private TextView ocBand1TextImage;
	private TextView ocBand2TextImage;
	private TextView ocBand3TextImage;
	private TextView ocBand4TextImage;
	
	private WindowManager mWindowManager;
    private TextView mDialogText;
    
	public Ohmage() {
		resToValueMap = new HashMap<OhmageUtil.Res_Band, Double>();
        resToColorList = new HashMap<OhmageUtil.Res_Band, ColorListAdapter>(); 
        
    }
		
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TabHost tabHost = getTabHost();
        LayoutInflater.from(this).inflate(R.layout.color_to_ohm_tab, tabHost.getTabContentView(), true);
        LayoutInflater.from(this).inflate(R.layout.ohm_to_color_tab, tabHost.getTabContentView(), true);
        LayoutInflater.from(this).inflate(R.layout.error_output, tabHost.getTabContentView(), true);
        LayoutInflater.from(this).inflate(R.layout.info_output, tabHost.getTabContentView(), true);
        LayoutInflater.from(this).inflate(R.layout.e_standard_tab, tabHost.getTabContentView(), true);
        
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.color_to_ohm_label))
        		.setIndicator(getString(R.string.color_to_ohm_label))
        		.setContent(R.id.coColorToOhmTabOuterLinearLayout)
        );
        
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.ohm_value_to_color_label))
        		.setIndicator(getString(R.string.ohm_value_to_color_label))
        		.setContent(R.id.ocOhmToColorTabOuterLinearLayout)
        );
        
        
        try {
        	ohmToColorTabSetup();
        	colorToOhmTabSetup();
        	generateEStdTab();
        	generateInfoTab();
        } catch (Exception e) {
        	generateErrorTab(e);
        }
	}
    
    private void generateEStdTab() {
		TabHost tabHost =  getTabHost();
		tabHost.addTab(tabHost.newTabSpec(getString(R.string.e_std_label))
        		.setIndicator(getString(R.string.e_std_label))
        		.setContent(R.id.eStdList)
        );
		
		ListView eStdList = (ListView) findViewById(R.id.eStdList);
		eStdList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, OhmageUtil.eStds));
        
		WindowManager mWindowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        mDialogText = new TextView(eStdList.getContext());
        mDialogText.setTextSize(18);
        mDialogText.setBackgroundColor(Color.BLACK);
        mDialogText.setVisibility(View.INVISIBLE);
        
        EStdOnScrollListener eStdScrollList = new EStdOnScrollListener(mWindowManager, mDialogText, false, OhmageUtil.eStds);
        eStdList.setOnFocusChangeListener(new EStdFocusListener(eStdScrollList));
        eStdList.setOnScrollListener(eStdScrollList);
	}

	private void generateErrorTab(Exception e) {
    	TabHost tabHost =  getTabHost();
		tabHost.addTab(tabHost.newTabSpec("ERROR")
        		.setIndicator("ERROR")
        		.setContent(R.id.errorText)
        );
		TextView errorText = (TextView) findViewById(R.id.errorText);
		StringBuilder error = new StringBuilder();
		error.append("Error: " + e.getMessage() + "\n");
		for(StackTraceElement te : e.getStackTrace()) {
			error.append("f: " + te.getFileName() + ", m: " + te.getMethodName() + ", l: " + te.getLineNumber() + "\n");
		}
		errorText.setText(error.toString());
    }
    
    private void generateInfoTab() {
    	TabHost tabHost =  getTabHost();
		tabHost.addTab(tabHost.newTabSpec("Info")
        		.setIndicator("Info")
        		.setContent(R.id.infoText)
        );
		TextView infoText = (TextView) findViewById(R.id.infoText);
		StringBuilder info = new StringBuilder();
		info.append("App: " + getString(R.string.app_name) + "\n\n");
		info.append("Version: " + getString(R.string.version) + "\n\n");
		info.append("Description: " + getString(R.string.app_description) + "\n\n");
		info.append("Instructions:\n" +
						"\tcolor → Ω: " + getString(R.string.co_instructions) + 
						"\n\n\tΩ → color: " + getString(R.string.oc_instructions) +
						"\n\n");
		info.append("Home Page: " + getString(R.string.home_page) + "\n\n");
		info.append("Support: " + getString(R.string.support_message) + "\n\n");
		info.append("Email: " + getString(R.string.email) + "\n\n");
		info.append("Future additions: Keep an eye out for the following Ohmage updates: " + getString(R.string.future_additions) + "\n\n");
		
		infoText.setText(info.toString());
    }
    
    private void colorToOhmTabSetup() {
    	coOhmValueTextOutput = (TextView) findViewById(id.coOhmValue);
    	
    	coBand1Spinner = (Spinner) findViewById(R.id.band1);
        ColorListAdapter cl1 = new ColorListAdapter(this, OhmageUtil.Res_Band.BAND_1);
        coBand1Spinner.setAdapter(cl1);
        SpinnerChangeListener sl1 = new SpinnerChangeListener(this, OhmageUtil.Res_Band.BAND_1);
        coBand1Spinner.setOnItemSelectedListener(sl1);
        resToValueMap.put(OhmageUtil.Res_Band.BAND_1, cl1.getResValueOfItem(0));
        resToColorList.put(OhmageUtil.Res_Band.BAND_1, cl1);
        
        coBand2Spinner = (Spinner) findViewById(R.id.band2);
        ColorListAdapter cl2 = new ColorListAdapter(this, OhmageUtil.Res_Band.BAND_2);
        coBand2Spinner.setAdapter(cl2);  
        SpinnerChangeListener sl2 = new SpinnerChangeListener(this, OhmageUtil.Res_Band.BAND_2);
        coBand2Spinner.setOnItemSelectedListener(sl2);
        resToValueMap.put(OhmageUtil.Res_Band.BAND_2, cl2.getResValueOfItem(0));
        resToColorList.put(OhmageUtil.Res_Band.BAND_2, cl2);
        
        coBand3Spinner = (Spinner) findViewById(R.id.band3);
        ColorListAdapter cl3 = new ColorListAdapter(this, OhmageUtil.Res_Band.BAND_3);
        coBand3Spinner.setAdapter(cl3);
        SpinnerChangeListener sl3 = new SpinnerChangeListener(this, OhmageUtil.Res_Band.BAND_3);
        coBand3Spinner.setOnItemSelectedListener(sl3);
        resToValueMap.put(OhmageUtil.Res_Band.BAND_3, cl3.getResValueOfItem(0));
        resToColorList.put(OhmageUtil.Res_Band.BAND_3, cl3);
        
        coBand4Spinner = (Spinner) findViewById(R.id.band4);
        ColorListAdapter cl4 = new ColorListAdapter(this, OhmageUtil.Res_Band.BAND_4);
        coBand4Spinner.setAdapter(cl4);  
        SpinnerChangeListener sl4 = new SpinnerChangeListener(this, OhmageUtil.Res_Band.BAND_4);
        coBand4Spinner.setOnItemSelectedListener(sl4);
        resToValueMap.put(OhmageUtil.Res_Band.BAND_4, cl4.getResValueOfItem(0));
        resToColorList.put(OhmageUtil.Res_Band.BAND_4, cl4);
    }
    
    private void ohmToColorTabSetup() {
    	ocOhmValueTextInput = (AutoCompleteTextView) findViewById(R.id.ocOhmValueAutoCompleteTextView);
        
        ArrayAdapter<Object> ohmValueAdapter = new ArrayAdapter<Object>(this,
               android.R.layout.simple_dropdown_item_1line, OhmageUtil.getValidOhmInputs(R.string.ohm).toArray());
        ocOhmValueTextInput.setAdapter(ohmValueAdapter);
        
        ocOhmValueTextInput.setCompletionHint("#, ##, #.#, ###");
        ocOhmValueTextInput.setValidator(new OhmInputDigitsValueValidator());
        
        ocUnitSpinner = (Spinner) findViewById(R.id.ocUnitSpinner);
        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(
                this, R.array.unit_array, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ocUnitSpinner.setAdapter(unitAdapter);
        
        ocToleranceSpinner = (Spinner) findViewById(R.id.ocToleranceSpinner);
        ArrayAdapter<CharSequence> toleranceAdapter = ArrayAdapter.createFromResource(
                this, R.array.tolerance_array, android.R.layout.simple_spinner_item);
        toleranceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ocToleranceSpinner.setAdapter(toleranceAdapter);
        
        Button updateB = (Button) findViewById(R.id.updateButton);
        updateB.setOnClickListener(new UpdateOhmageOnClickedListener(this));
        
        ocBand1TextImage = (TextView) findViewById(id.ocBand1TextView);
        ocBand2TextImage = (TextView) findViewById(id.ocBand2TextView);
        ocBand3TextImage = (TextView) findViewById(id.ocBand3TextView);
        ocBand4TextImage = (TextView) findViewById(id.ocBand4TextView);
        
        updateOhmageBasedOnInputFields();
    }

	public void resistorColorBandChanged(OhmageUtil.Res_Band bandNum, int itemPos) {
		Double value = resToColorList.get(bandNum).getResValueOfItem(itemPos);
		resToValueMap.put(bandNum, value);
		updateOhmageBasedOnColorBands();
	}
    
	/* Will Die if ohmValue is not a valid Double */
	private void updateOhmageBasedOnInputFields() {
		try {
			Double valueD;
			try {
				if (!OhmageUtil.getValidOhmInputs(R.string.ohm).contains(ocOhmValueTextInput.getText().toString())) {
					throw new IllegalArgumentException("ocOhmValueTextInput is invalid.");						
				}
				// The following should never fail if the above passed.
				valueD = Double.valueOf(ocOhmValueTextInput.getText().toString());
			} catch (NumberFormatException e) {
				// If the user provided an invalid input.
				ocOhmValueTextInput.setText("ERR");
				return;
			} catch (IllegalArgumentException e) {
				ocOhmValueTextInput.setText("ERR");
				return;
			}
			
			int toPow = ( ocUnitSpinner.getSelectedItemPosition() )  * 3;
			Double multiplier = Math.pow(10, toPow);
			Double resistanceValue = valueD * multiplier;
			// Validate that the resistance value is within the available bounds of the resistor display.
			// Min == 0.1OOhm, Max = 990MOhm
			if (resistanceValue != 0 &&  resistanceValue < 0.1) {
				// Resistance is out of scope.. Do something about this..
				ocOhmValueTextInput.setText("ERR");
				throw new InvalidParameterException(getString(R.string.ohm) + " input must be between 0.1" + getString(R.string.ohm) + " and 990" + getString(R.string.megaOhm));
			} else if (resistanceValue > ( 990 * Math.pow(10, 6)) ) {
				// Resistance is out of scope.. Do something about this..
				ocOhmValueTextInput.setText("ERR");
				throw new InvalidParameterException(getString(R.string.ohm) + " input must be between 0.1" + getString(R.string.ohm) + " and 990" + getString(R.string.megaOhm));
			}
			
			Double digitsValue = null;
			
			digitsValue = resistanceValue;
			
			int count = 0;
			if (digitsValue == 0) {
				// count = 0 -> Do nothing
			} else if (digitsValue < 10) {
				while (digitsValue < 10) {
					digitsValue =  digitsValue * 10;
					count--;
				}	
			} else if (digitsValue >= 100) {
				while (digitsValue >= 100) {
					digitsValue =  digitsValue / 10;
					count++;
				}
			}

			int multiplierValueId = OhmageUtil.getMultiplierBandDrawableFromMultiplier(count);
			OhmageUtil.setBandColor(ocBand3TextImage, multiplierValueId);

			// digitsValue == 0 or  10 <= digitsValue < 100 
			digitsValue = OhmageUtil.round(digitsValue, 0);
			if (digitsValue == 0) {
				OhmageUtil.setBandColor(ocBand1TextImage, R.drawable.black);
				OhmageUtil.setBandColor(ocBand2TextImage, R.drawable.black);
			} else {
				// Get the first and second value of digitsValue.
				int a = new Double(digitsValue / 10).intValue();
				int b = new Double(digitsValue % 10).intValue();
				
				OhmageUtil.setBandColor(ocBand1TextImage, OhmageUtil.getDigitDrawableId(a));
				OhmageUtil.setBandColor(ocBand2TextImage, OhmageUtil.getDigitDrawableId(b));		
				
			}
			
			
			// Update the tolerance, this is a simple mapping from a position in one view,
			// to the position in the other view.
			
			int tolSpinnerPos = ocToleranceSpinner.getSelectedItemPosition();
			int tolValueId = OhmageUtil.getToleranceDrawableIdFromTolSpinnerPosition(tolSpinnerPos);
			OhmageUtil.setBandColor(ocBand4TextImage, OhmageUtil.getToleranceDrawableIdFromTolRef(tolValueId));
			
		} catch (Exception e) {
			generateErrorTab(e);
		} 
	}
	
	private class UpdateOhmageOnSelectedListener implements OnItemSelectedListener {
		Ohmage ohmageMaster;
		public UpdateOhmageOnSelectedListener(Ohmage ohmage) {
			this.ohmageMaster = ohmage;
		}
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			ohmageMaster.updateOhmageBasedOnInputFields();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	}
	
	private class UpdateOhmageOnClickedListener implements OnClickListener {
		Ohmage ohmageMaster;
		public UpdateOhmageOnClickedListener(Ohmage ohmage) {
			this.ohmageMaster = ohmage;
		}
		public void onClick(View v) {
			ohmageMaster.updateOhmageBasedOnInputFields();
		}
	}
	
	
	private void updateOhmageBasedOnColorBands() {
		try {
		Integer b1 = resToValueMap.get(OhmageUtil.Res_Band.BAND_1).intValue();
		Integer b2 = resToValueMap.get(OhmageUtil.Res_Band.BAND_2).intValue();
		Double b3 = resToValueMap.get(OhmageUtil.Res_Band.BAND_3);
		Double b4 = resToValueMap.get(OhmageUtil.Res_Band.BAND_4);
		Integer digits = ( b1 * 10 ) + b2;
		Double valueD = digits * b3;
		valueD = OhmageUtil.round(valueD, 4);
		String ohmDisplayValue;
		int ohmMagnitudeUnits;
		String tolerance = OhmageUtil.round(b4, 3).toString();
		
		if (valueD == 0) {
			ohmDisplayValue = valueD.toString();
			ohmMagnitudeUnits = R.string.ohm;
		} else if (valueD < 1) {	// If it is a fraction, print it as a double.
			Double valueP = valueD * Math.pow(10, 3);
			if (valueP % 1 == 0) {
				ohmDisplayValue = Integer.toString(valueP.intValue());
				ohmMagnitudeUnits = R.string.miliOhm;
			} else {
				ohmDisplayValue = valueP.toString();
				ohmMagnitudeUnits = R.string.miliOhm;
			}
		} else if (valueD < Math.pow(10, 3)) {
			if (valueD % 1 == 0) {
				ohmDisplayValue = Integer.toString(valueD.intValue());
				ohmMagnitudeUnits = R.string.ohm;
			} else {
				ohmDisplayValue = valueD.toString();
				ohmMagnitudeUnits = R.string.ohm;
			}
		} else if (valueD < Math.pow(10, 6)) {
			Double valueP = valueD / Math.pow(10, 3);
			if (valueP % 1 == 0) {
				ohmDisplayValue = Integer.toString(valueP.intValue());
				ohmMagnitudeUnits = R.string.kilaOhm;
			} else {
				ohmDisplayValue = valueP.toString();
				ohmMagnitudeUnits = R.string.kilaOhm;
			}
		} else if (valueD < Math.pow(10, 9)) {
			Double valueP = valueD / Math.pow(10, 6);
			if (valueP % 1 == 0) {
				ohmDisplayValue = Integer.toString(valueP.intValue());
				ohmMagnitudeUnits = R.string.megaOhm;
			} else {
				ohmDisplayValue = valueP.toString();
				ohmMagnitudeUnits = R.string.megaOhm;
			}
		} else {
			Double valueP = valueD / Math.pow(10, 6);
			if (valueP % 1 == 0) {
				ohmDisplayValue = Integer.toString(valueP.intValue());
				ohmMagnitudeUnits = R.string.megaOhm;
			} else {
				ohmDisplayValue = valueP.toString();
				ohmMagnitudeUnits = R.string.megaOhm;
			}
		}
		
		String text = ohmDisplayValue + getString(ohmMagnitudeUnits) + " " + getString(string.plus_or_minus_symbol) + tolerance + "%"; 	
		
		coOhmValueTextOutput.setText(text);
		
		} catch (Exception e) {
			generateErrorTab(e);
		}
    }
    



    private class SpinnerChangeListener implements OnItemSelectedListener {

    	Ohmage masterOhmage;
    	OhmageUtil.Res_Band bandNum;
    	
    	public SpinnerChangeListener(Ohmage ohmage, OhmageUtil.Res_Band bandNum) {
			this.masterOhmage = ohmage;
			this.bandNum = bandNum;
		}
    	
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			masterOhmage.resistorColorBandChanged(bandNum, arg2);	
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// This should never happen.
		}
    	
    }
    
    


    
}