package com.robertpyke.ohmage;

import android.widget.AutoCompleteTextView.Validator;

public class OhmInputDigitsValueValidator implements Validator {
	public CharSequence fixText(CharSequence invalidText) {
		return "ERR";
	}

	public boolean isValid(CharSequence text) {
		for(String s : OhmageUtil.getValidOhmInputs(R.string.ohm)) {
			if (s.equals(text.toString())) {
				return true;
			}
		}
		return false;
	}
}
