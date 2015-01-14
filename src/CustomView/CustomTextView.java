package CustomView;

import android.content.Context;

import android.widget.TextView;

public class CustomTextView extends TextView {

	@Override
	public void setPadding(int left, int top, int right, int bottom) {
		// TODO Auto-generated method stub
		super.setPadding(34, 0, 0, 0);
	}

	public CustomTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


}
