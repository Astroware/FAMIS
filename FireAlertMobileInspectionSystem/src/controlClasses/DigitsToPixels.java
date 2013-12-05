package controlClasses;

import android.content.Context;

public class DigitsToPixels {
	// DisplayHelper:
	private static Float scale;
	
	//Changes the integer inputted to properly scale to the display
	public static int dpToPixel(int dp, Context context) {
	    if (scale == null)
	        scale = context.getResources().getDisplayMetrics().density;
	    return (int) ((float) dp * scale);
	}
}
