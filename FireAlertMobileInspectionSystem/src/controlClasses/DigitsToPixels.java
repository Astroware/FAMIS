package controlClasses;

import android.content.Context;

public class DigitsToPixels {
	// DisplayHelper:
	private static Float scale;
	public static int dpToPixel(int dp, Context context) {
	    if (scale == null)
	        scale = context.getResources().getDisplayMetrics().density;
	    return (int) ((float) dp * scale);
	}
}
