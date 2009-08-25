package com.robertpyke.ohmage;

import android.view.View;
import android.view.View.OnFocusChangeListener;

public class EStdFocusListener implements OnFocusChangeListener{
	EStdOnScrollListener scrollList;
	public EStdFocusListener(EStdOnScrollListener scrollList) {
		// TODO Auto-generated constructor stub
		this.scrollList = scrollList;
	}
	public void onFocusChange(View v, boolean hasFocus) {
		if (!hasFocus) {
			scrollList.removeWindow();
		}
	}

}
