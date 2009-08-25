package com.robertpyke.ohmage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OhmageUtil {
	
	private static Map<Integer, Set<String>> validOhmInputs = null;
	private static Map<Integer, Drawable> colorIdToDrawableMap;
	
	public static String[] eStds = {
		// E6
		"10",
		"15",
		"22",
		"33",
		"47",
		"68",
		// E12
		"10",
		"12",
		"15",
		"18",
		"22",
		"27",
		"33",
		"39",
		"47",
		"56",
		"68",
		"82",
		// E24
		"10",
		"11",
		"12",
		"13",
		"15",
		"16",
		"18",
		"20",
		"22",
		"24",
		"27",
		"30",
		"33",
		"36",
		"39",
		"43",
		"47",
		"51",
		"56",
		"62",
		"68",
		"75",
		"82",
		"91",
		// E48
		"100",
		"105",
		"110",
		"115",
		"121",
		"127",
		"133",
		"140",
		"147",
		"154",
		"162",
		"169",
		"178",
		"187",
		"196",
		"205",
		"215",
		"226",
		"237",
		"249",
		"261",
		"274",
		"287",
		"301",
		"316",
		"332",
		"348",
		"365",
		"383",
		"402",
		"422",
		"442",
		"464",
		"487",
		"511",
		"536",
		"562",
		"590",
		"619",
		"649",
		"681",
		"715",
		"750",
		"787",
		"825",
		"866",
		"909",
		"953",
		// E96
		"100",
		"102",
		"105",
		"107",
		"110",
		"113",
		"115",
		"118",
		"121",
		"124",
		"127",
		"130",
		"133",
		"137",
		"140",
		"143",
		"147",
		"150",
		"154",
		"158",
		"162",
		"165",
		"169",
		"174",
		"178",
		"182",
		"187",
		"191",
		"196",
		"200",
		"205",
		"210",
		"215",
		"221",
		"226",
		"232",
		"237",
		"243",
		"249",
		"255",
		"261",
		"267",
		"274",
		"280",
		"287",
		"294",
		"301",
		"309",
		"316",
		"324",
		"332",
		"340",
		"348",
		"357",
		"365",
		"374",
		"383",
		"392",
		"402",
		"412",
		"422",
		"432",
		"442",
		"453",
		"464",
		"475",
		"487",
		"499",
		"511",
		"523",
		"536",
		"549",
		"562",
		"576",
		"590",
		"604",
		"619",
		"634",
		"649",
		"665",
		"681",
		"698",
		"715",
		"732",
		"750",
		"768",
		"787",
		"806",
		"825",
		"845",
		"866",
		"887",
		"909",
		"931",
		"953",
		"976",
		// E192
		"100",
		"101",
		"102",
		"104",
		"105",
		"106",
		"107",
		"109",
		"110",
		"111",
		"113",
		"114",
		"115",
		"117",
		"118",
		"120",
		"121",
		"123",
		"124",
		"126",
		"127",
		"129",
		"130",
		"132",
		"133",
		"135",
		"137",
		"138",
		"140",
		"142",
		"143",
		"145",
		"147",
		"149",
		"150",
		"152",
		"154",
		"156",
		"158",
		"160",
		"162",
		"164",
		"165",
		"167",
		"169",
		"172",
		"174",
		"176",
		"178",
		"180",
		"182",
		"184",
		"187",
		"189",
		"191",
		"193",
		"196",
		"198",
		"200",
		"203",
		"205",
		"208",
		"210",
		"213",
		"215",
		"218",
		"221",
		"223",
		"226",
		"229",
		"232",
		"234",
		"237",
		"240",
		"243",
		"246",
		"249",
		"252",
		"255",
		"258",
		"261",
		"264",
		"267",
		"271",
		"274",
		"277",
		"280",
		"284",
		"287",
		"291",
		"294",
		"298",
		"301",
		"305",
		"309",
		"312",
		"316",
		"320",
		"324",
		"328",
		"332",
		"336",
		"340",
		"344",
		"348",
		"352",
		"357",
		"361",
		"365",
		"370",
		"374",
		"379",
		"383",
		"388",
		"392",
		"397",
		"402",
		"407",
		"412",
		"417",
		"422",
		"427",
		"432",
		"437",
		"442",
		"448",
		"453",
		"459",
		"464",
		"470",
		"475",
		"481",
		"487",
		"493",
		"499",
		"505",
		"511",
		"517",
		"523",
		"530",
		"536",
		"542",
		"549",
		"556",
		"562",
		"569",
		"576",
		"583",
		"590",
		"597",
		"604",
		"612",
		"619",
		"626",
		"634",
		"642",
		"649",
		"657",
		"665",
		"673",
		"681",
		"690",
		"698",
		"706",
		"715",
		"723",
		"732",
		"741",
		"750",
		"759",
		"768",
		"777",
		"787",
		"796",
		"806",
		"816",
		"825",
		"835",
		"845",
		"856",
		"866",
		"876",
		"887",
		"898",
		"909",
		"920",
		"931",
		"942",
		"953",
		"965",
		"976",
		"988"
	};
	
	public static String eStdToleranceBasedOnEStdPos(int pos) {
		if (pos < 0) {
			throw new IllegalArgumentException("eStdToleranceBasedOnEStdPos called with an invalid position. pos: " + pos);
		// 0 - 6 (6)
		} else if (pos < 6) {
			return "E6 - 20%";
		// 6 - 18 (12)
		} else if (pos < 18) {
			return "E12 - 10%";
		//	18 - 42 (24)
		} else if (pos < 42) {
			return "E24 - 5%";
		// 42 - 90 (48)
		} else if (pos < 90) {
			return "E48 - 2%";
		// 90 - 186 (96)	
		} else if (pos < 186) {
			return "E96 - 1%";
		// 186 - 378 (192) 	
		} else if (pos < 378){
			return "E192 - 0.5%, 0.25%, 0.1%";
		} else {
			throw new IllegalArgumentException("eStdToleranceBasedOnEStdPos called with an invalid position. pos: " + pos);
		}
		
	}
	
	
	
	public static enum Res_Band {
		BAND_1,
		BAND_2,
		BAND_3,
		BAND_4
	}
	
	public static void setBandColor(TextView v, int resourceId){
		switch(resourceId) {
			case R.drawable.black :
				v.setBackgroundResource(resourceId);
				v.setText("Black");
				v.setTextColor(Color.WHITE);
				break;
			case R.drawable.blue:
				v.setBackgroundResource(resourceId);
				v.setText("Blue");
				v.setTextColor(Color.WHITE);
				break;
			case R.drawable.brown :
				v.setBackgroundResource(resourceId);
				v.setText("Brown");
				v.setTextColor(Color.WHITE);
				break;
			case R.drawable.gold :				
				v.setBackgroundResource(resourceId);
				v.setText("Gold");
				v.setTextColor(Color.BLACK);
				break;
			case R.drawable.gray:
				v.setBackgroundResource(resourceId);
				v.setText("Gray");
				v.setTextColor(Color.BLACK);
				break;
			case R.drawable.green :
				v.setBackgroundResource(resourceId);
				v.setText("Green");
				v.setTextColor(Color.WHITE);
				break;
			case R.drawable.orange :
				v.setBackgroundResource(resourceId);
				v.setText("Orange");
				v.setTextColor(Color.BLACK);
				break;
			case R.drawable.red :
				v.setBackgroundResource(resourceId);
				v.setText("Red");
				v.setTextColor(Color.WHITE);
				break;
			case R.drawable.silver :
				v.setBackgroundResource(resourceId);
				v.setText("Silver");
				v.setTextColor(Color.WHITE);
				break;
			case R.drawable.violet :
				v.setBackgroundResource(resourceId);
				v.setText("Violet");
				v.setTextColor(Color.WHITE);
				break;
			case R.drawable.white :
				v.setBackgroundResource(resourceId);
				v.setText("White");
				v.setTextColor(Color.BLACK);
				break;
			case R.drawable.yellow :
				v.setBackgroundResource(resourceId);
				v.setText("Yellow");
				v.setTextColor(Color.BLACK);
				break;
		}
	}
	
	public static Double getTolerance(int bandColorDrawableId) {
		switch(bandColorDrawableId) {
			case R.drawable.blue:
	        	return 0.25;
	        case R.drawable.brown:
	        	return 1.0;
	        case R.drawable.gold:
	        	return 5.0;
	        case R.drawable.gray:
	        	return 0.05;
	        case R.drawable.green:
	        	return 0.5;
	        case R.drawable.red:
	        	return 2.0;
	        case R.drawable.silver:
	        	return 10.0;
	        case R.drawable.violet:
	        	return 0.10;
		}
		return null;		
	}
	
	public static Integer getToleranceDrawableIdFromTolRef(int toleranceId) {
		switch(toleranceId) {
			case R.string.tol_0p05pc:
		    	return R.drawable.gray;
		    case R.string.tol_0p1pc:
		    	return R.drawable.violet;
		    case R.string.tol_0p25pc:
		    	return R.drawable.blue;
		    case R.string.tol_0p5pc:
		    	return R.drawable.green;                	
		    case R.string.tol_1pc:
		    	return R.drawable.brown;
		    case R.string.tol_2pc:
		    	return R.drawable.red;
		    case R.string.tol_5pc:
		    	return R.drawable.gold;
		    case R.string.tol_10pc:
		    	return R.drawable.silver;
		}
		throw new IllegalArgumentException("getToleranceDrawableId called with an invalid toleranceId: " + toleranceId + ".");
	}
	
	/*
	 * Give the value you would raise ten to the power of to get the multiplier.
	 * So for a multiplier of 1000, provide the paramater 3, as 10^3 = 1000
	 */
	public static Integer getMultiplierBandDrawableFromMultiplier(Integer multiplier){
		switch(multiplier) {
		case -2:
			// 0.01
			return R.drawable.silver;
		case -1:
			// 0.1
			return R.drawable.gold;
		case 0:
			// 1
			return R.drawable.black;
		case 1:
			// 10
			return R.drawable.brown;
		case 2:
			// 100
			return R.drawable.red;
		case 3:
			// k
			return R.drawable.orange;
		case 4:
			// 10k
			return R.drawable.yellow;
		case 5:
			// 100k
			return R.drawable.green;
		case 6:
			// M
			return R.drawable.blue;
		case 7:
			// 10M
			return R.drawable.violet;			
		}
		throw new IllegalArgumentException("getMultiplierBandDrawableFromMultiplier called with an invalid multiplier: " + multiplier + ".");
	}
	
	public static Integer getDigit(int bandColorDrawableId) {
		switch(bandColorDrawableId) {
			case R.drawable.black:
		    	return 0;
		    case R.drawable.brown:
		    	return 1;
		    case R.drawable.red:
		    	return 2;
		    case R.drawable.orange:
		    	return 3;                	
		    case R.drawable.yellow:
		    	return 4;
		    case R.drawable.green:
		    	return 5;
		    case R.drawable.blue:
		    	return 6;
		    case R.drawable.violet:
		    	return 7;
		    case R.drawable.gray:
		    	return 8;
		    case R.drawable.white:
		    	return 9;
		}
		return null;
	}
	
	public static Integer getDigitDrawableId(int digit) {
		switch(digit) {
			case 0:
		    	return R.drawable.black;
		    case 1:
		    	return R.drawable.brown;
		    case 2:
		    	return R.drawable.red;
		    case 3:
		    	return R.drawable.orange;                	
		    case 4:
		    	return R.drawable.yellow;
		    case 5:
		    	return R.drawable.green;
		    case 6:
		    	return R.drawable.blue;
		    case 7:
		    	return R.drawable.violet;
		    case 8:
		    	return R.drawable.gray;
		    case 9:
		    	return R.drawable.white;
		}
		throw new IllegalArgumentException("getDigitDrawableId called with an invalid digit: " + digit + ". Digit must be 0 < x < 10");
	}
	
	public static Double getMultiplier(int bandColorDrawableId) {
		switch(bandColorDrawableId) {
	        case R.drawable.black:
	        	return 1.0;
	        case R.drawable.blue:
	        	return Math.pow(10, 6);
	        case R.drawable.brown:
	        	return 10.0;
	        case R.drawable.gold:
	        	return 0.1;                	
	        case R.drawable.green:
	        	return 100 * Math.pow(10, 3);
	        case R.drawable.orange:
	        	return Math.pow(10, 3);
	        case R.drawable.red:
	        	return 100.0;
	        case R.drawable.silver:
	        	return 0.01;
	        case R.drawable.violet:
	        	return 10 * Math.pow(10, 6);
	        case R.drawable.yellow:
	        	return 10 * Math.pow(10, 3);
		}
		throw new IllegalArgumentException("getMultiplier called with an invalid bandColorId: " + bandColorDrawableId + ".");
	}
	
	public static Double round(Double Rval, int Rpl) {
  	  Double p = Math.pow(10,Rpl);
	  Rval = Rval * p;
	  long tmp = Math.round(Rval);
	  return tmp/p;	
	}
	
	public static Set<String> getValidOhmInputs(int multiplierId) {
		// If this has never been requested before,
		// generate the validOhmInputs.
		if(validOhmInputs == null) {
			validOhmInputs = new HashMap<Integer, Set<String>>();
		}
		
		if(!validOhmInputs.containsKey(multiplierId)) {
			Set<String> validStrings = new HashSet<String>();
			for (int i = 0; i < 10; i++) {
				// All 1 digit Numbers..
    			String tmpIntS = Integer.toString(i);
    			validStrings.add(tmpIntS);
    			for (int j = 0; j < 10; j++) {
    				// All 2 digit Numbers
    				String tmpIntS2 = Integer.toString(j);
					validStrings.add(tmpIntS + tmpIntS2);
    				// All 1 digit then decimal place then another digit numbers.
        			validStrings.add(tmpIntS + "." + tmpIntS2);
        			if(i == 0) {
						// If the first number was 0, add it the string as
						// a valid string as it won't equal about 99
						for(int k = 0; k < 10; k++) {
	    	        		String tmpIntS3 = Integer.toString(k);
	            			validStrings.add(tmpIntS + tmpIntS2 + tmpIntS3);
						}
        			} else {
        				// All 2 digit numbers followed by a zero
    					validStrings.add(tmpIntS + tmpIntS2 + "0");
        			}
        		}
	    	}
			validOhmInputs.put(multiplierId, validStrings);			
		}
		
		return validOhmInputs.get(multiplierId);
	}

	public static Integer getToleranceDrawableIdFromTolSpinnerPosition(int tolSpinnerPos) {
			switch(tolSpinnerPos) {
	        case 0:
	        	return R.string.tol_0p05pc;
	        case 1:
	        	return R.string.tol_0p1pc;
	        case 2:
	        	return R.string.tol_0p25pc;
	        case 3:
	        	return R.string.tol_0p5pc;
	        case 4:
	        	return R.string.tol_1pc;
	        case 5:
	        	return R.string.tol_2pc;
	        case 6:
	        	return R.string.tol_5pc;
	        case 7:
	        	return R.string.tol_10pc;
		}
		throw new IllegalArgumentException("getToleranceDrawableIdFromTolSpinnerPosition called with an invalid tolSpinnerPos: " + tolSpinnerPos + ".");
	}
}
