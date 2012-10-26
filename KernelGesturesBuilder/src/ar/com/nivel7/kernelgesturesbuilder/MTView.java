package ar.com.nivel7.kernelgesturesbuilder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MTView extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = "KernelGesturesBuilder";

	private static final int MAX_TOUCHPOINTS = 10;
	private static final String START_TEXT = "Touch Anywhere To Test";

	private Paint textPaint = new Paint();
	private Paint touchPaints[] = new Paint[MAX_TOUCHPOINTS];
	private int colors[] = new int[MAX_TOUCHPOINTS];
	
	private int gridcolumns; 
	public void setGridcolumns(int gridcolumns) {
		this.gridcolumns = gridcolumns;
	}

	private int gridrows;
	public void setGridrows(int gridrows) {
		this.gridrows = gridrows;
	}
	
	private int width, height;
	private float scale = 1.0f;

	
	public MTView(Context context ) {
		super(context);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		setFocusable(true); // make sure we get key events
		setFocusableInTouchMode(true); // make sure we get touch events
		init();
	}

	private void init() {
		textPaint.setColor(Color.WHITE);
		colors[0] = Color.BLUE;
		colors[1] = Color.RED;
		colors[2] = Color.GREEN;
		colors[3] = Color.YELLOW;
		colors[4] = Color.CYAN;
		colors[5] = Color.MAGENTA;
		colors[6] = Color.DKGRAY;
		colors[7] = Color.WHITE;
		colors[8] = Color.LTGRAY;
		colors[9] = Color.GRAY;
		for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
			touchPaints[i] = new Paint();
			touchPaints[i].setColor(colors[i]);
		}
				
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int pointerCount = event.getPointerCount();
		if (pointerCount > MAX_TOUCHPOINTS) {
			pointerCount = MAX_TOUCHPOINTS;
		}
		Canvas c = getHolder().lockCanvas();
		if (c != null) {
			c.drawColor(Color.BLACK);
			if (event.getAction() == MotionEvent.ACTION_UP) {
				// draw grid
				drawGrid (c);
			} else {
				// draw grid
				drawGrid (c);
				// draw crosshairs first then circles as a second pass
				for (int i = 0; i < pointerCount; i++) {
					int id = event.getPointerId(i);
					int x = (int) event.getX(i);
					int y = (int) event.getY(i);
					drawCrosshairsAndText(x, y, touchPaints[id], i, id, c);
				}
				for (int i = 0; i < pointerCount; i++) {
					int id = event.getPointerId(i);
					int x = (int) event.getX(i);
					int y = (int) event.getY(i);
					drawCircle(x, y, touchPaints[id], c);
				}
			}
			getHolder().unlockCanvasAndPost(c);
		}
		return true;
	}

	private void drawGrid (Canvas c) {
		for (int i = 1; i < gridrows; i++) {
			c.drawLine(0, (height/gridrows)*i, 
					   width, (height/gridrows)*i, 
					   touchPaints[3]);
		}
		for (int i = 1; i < gridcolumns; i++) {
			c.drawLine((width/gridcolumns)*i, 0, 
					   (width/gridcolumns)*i, height, 
					   touchPaints[3]);
		}
		
	}

	private void drawCrosshairsAndText(int x, int y, Paint paint, int ptr,
			int id, Canvas c) {
		c.drawLine(0, y, width, y, paint);
		c.drawLine(x, 0, x, height, paint);
		int textY = (int) ((15 + 20 * ptr) * scale);
		c.drawText("x" + ptr + "=" + x, 10 * scale, textY, textPaint);
		c.drawText("y" + ptr + "=" + y, 70 * scale, textY, textPaint);
		c.drawText("id" + ptr + "=" + id, width - 55 * scale, textY, textPaint);
	}

	private void drawCircle(int x, int y, Paint paint, Canvas c) {
		c.drawCircle(x, y, 30 * scale, paint);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		this.width = width;
		this.height = height;
		if (width > height) {
			this.scale = width / 480f;
		} else {
			this.scale = height / 480f;
		}
		textPaint.setTextSize(14 * scale);
		Canvas c = getHolder().lockCanvas();
		if (c != null) {
			// clear screen
			c.drawColor(Color.BLACK);
			// draw grid
			drawGrid (c);
			float tWidth = textPaint.measureText(START_TEXT);
			c.drawText(START_TEXT, width / 2 - tWidth / 2, height / 2 - 10,
					textPaint);
			getHolder().unlockCanvasAndPost(c);
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}

}