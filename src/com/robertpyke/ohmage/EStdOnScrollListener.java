package com.robertpyke.ohmage;

import android.graphics.PixelFormat;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class EStdOnScrollListener implements OnScrollListener {
	Handler mHandler = new Handler();
    private boolean mReady = false;
	private boolean mShowing = false;
	private TextView mDialogText;
	private String mPrevString = "";
	private RemoveWindow mRemoveWindow = new RemoveWindow();
	private String[] mStrings;
	private WindowManager mWindowManager;
	
	public EStdOnScrollListener(WindowManager windowManager, TextView dialogText, boolean ready, String[] strings) {
		this.mWindowManager = windowManager;
		this.mReady = ready;
		this.mDialogText = dialogText;
		this.mStrings = strings;
		mHandler.post(new Runnable() {
            public void run() {
            	mReady = true;
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);
                mWindowManager.addView(mDialogText, lp);
            }
        });
		
	}

	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {
		if (view.hasFocus()) {
			if (mReady) {
	            String textVal = "";
	            final String text = OhmageUtil.eStdToleranceBasedOnEStdPos(firstVisibleItem);
	            if (!TextUtils.isEmpty(text)) {
	                textVal = text;
	            }
	
	            if (!mShowing || textVal != mPrevString) {
	                mShowing = true;
	                mDialogText.setVisibility(View.VISIBLE);
	            }
	
	            mDialogText.setText(textVal);
	            mHandler.removeCallbacks(mRemoveWindow);
	            mHandler.postDelayed(mRemoveWindow, 3000);
	            mPrevString = textVal;
	        }
		}
    }

	private final class RemoveWindow implements Runnable {
        public void run() {
            removeWindow();
        }
    }
	
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
    
    public void removeWindow() {
        if (mShowing) {
            mShowing = false;
            mDialogText.setVisibility(View.INVISIBLE);
        }
    }


}
