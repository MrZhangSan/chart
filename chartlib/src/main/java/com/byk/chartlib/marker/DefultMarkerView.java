package com.byk.chartlib.marker;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.byk.chartlib.R;
import com.byk.chartlib.marker.data.MarkBean;

public class DefultMarkerView extends MarkerView<MarkBean>{
    private TextView tvMarker;
    public DefultMarkerView(Context context, int layout) {
        super(context, layout);
        tvMarker = findViewById(R.id.tvMarker);
    }

    @Override
    public void refreshContent(MarkBean markBean) {
        tvMarker.setText(markBean.data);

        setLayoutParams(new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }
}
