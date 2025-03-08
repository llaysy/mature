package com.example.mature;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class BlurredEllipseView extends View {

    private Paint paint;

    public BlurredEllipseView(Context context) {
        super(context);
        init();
    }

    public BlurredEllipseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xFF101010); // Цвет #101010
        paint.setAlpha(77); // Прозрачность 0.3
        paint.setMaskFilter(new BlurMaskFilter(9.94f, BlurMaskFilter.Blur.NORMAL)); // Размытие
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.addOval(0, 0, getWidth(), getHeight(), Path.Direction.CW);
        canvas.drawPath(path, paint);
    }
}