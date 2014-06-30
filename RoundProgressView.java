package com.kaiser.pendergrast.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


public class RoundedProgressView extends View {

	public interface ProgressListener {
		/**
		 * Called when the RoundedProgressView has reached a progress of 1 and
		 * is now stopping (when animating progress)
		 */
		public void onCompleted();

		/**
		 * Called when the RoundedProgressView has reached a progress of 1 and
		 * is looping back to 0 (when animating progress)
		 */
		public void onLooped();

		/**
		 * Called when the RoundedProgressView updates its progress level (when
		 * animating progress)
		 */
		public void onUpdate(double progress);
	}

	protected ProgressListener mListener;

	protected double mProgress = 0;
	protected boolean mReversed = false;

	protected long mLoopLength = 0;
	protected long mLoopStart = 0;
	protected boolean mShouldLoop;

	protected Runnable mAnimationRunnable;

	public RoundedProgressView(Context context) {
		super(context);
	}

	public RoundedProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RoundedProgressView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Set progress from 0 to 1
	 */
	public void setProgress(double progress) {
		mProgress = Math.max(0, Math.min(1, progress));
		invalidate();
	}

	public double getProgress() {
		return mProgress;
	}

	/**
	 * Set if the arc should be drawn clockwise (normal) or counter-clockwise
	 * (reversed). This is ignored if using loop animation
	 */
	public void setReversed(boolean reversed) {
		mReversed = reversed;
	}

	public boolean isReversed() {
		return mReversed;
	}

	/**
	 * Set a listener to recieve callbacks about the progress of this View
	 */
	public void setListener(ProgressListener lis) {
		mListener = lis;
	}
	
	/**
	 * Animate the progress of this RoundedProgressView from 0 to 1
	 * 
	 * @param duration
	 *            how long the animation should occur
	 * @param loop
	 *            if the animation should loop after reaching progress of 1
	 */
	public void startAnimatingProgress(long duration, boolean loop) {
		if (mAnimationRunnable == null) {
			mLoopStart = System.currentTimeMillis();
			mLoopLength = duration;
			mShouldLoop = loop;

			mAnimationRunnable = new Runnable() {
				@Override
				public void run() {
					while (mLoopStart != 0) {
						try {
							Thread.sleep(16);
							advanceProgress();

							RoundedProgressView.this.post(new Runnable() {
								@Override
								public void run() {
									RoundedProgressView.this.invalidate();
								}
							});
						} catch (Exception e) {
						}
					}

					mAnimationRunnable = null;
				}
			};
			new Thread(mAnimationRunnable).start();

		}
	}

	public void stopAnimatingProgress() {
		mLoopStart = 0;
	}

	protected void advanceProgress() {
		mProgress = (System.currentTimeMillis() - mLoopStart) / ((double) mLoopLength);

		if(mListener != null){
			mListener.onUpdate(mProgress);
		}
		
		if(mProgress > 1){
			if(mShouldLoop){
				mReversed = ((int) mProgress) % 2 == 1;
				mProgress -= ((int) mProgress);
				
				if(mListener != null){
					mListener.onLooped();
				}
			}else{
				mLoopLength = 0;
				mProgress = 1;
				
				if(mListener != null){
					mListener.onCompleted();
				}
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {

		// Get the smallest dimension, because we're drawing
		// into a circle
		int dimen = Math.min(getWidth(), getHeight());

		if (dimen == 0) {
			return;
		}

		drawProgressArc(canvas);
	}

	protected void drawProgressArc(Canvas canvas) {
		final Paint whiteStroke = new Paint();
		whiteStroke.setColor(0xFFFFFFFF);
		whiteStroke.setStyle(Paint.Style.STROKE);
		whiteStroke.setStrokeWidth(7);
		whiteStroke.setAntiAlias(true);

		final Paint whiteThickStroke = new Paint();
		whiteThickStroke.setColor(0xFFFFFFFF);
		whiteThickStroke.setStyle(Paint.Style.STROKE);
		whiteThickStroke.setStrokeWidth(20);
		whiteThickStroke.setAntiAlias(true);

		final RectF rectf = new RectF(20, 20, getWidth() - 20, getHeight() - 20);

		// Clear the canvas
		canvas.drawARGB(0, 0, 0, 0);

		// Draw the thin circle
		canvas.drawArc(rectf, -90, 360, true, whiteStroke);

		// Draw the thick circle
		if (!mReversed) {
			canvas.drawArc(rectf, -90, ((int) (360 * mProgress)), false, whiteThickStroke);
		} else {
			canvas.drawArc(rectf, (int) (360 * mProgress) - 90, (int) (360 * (1 - mProgress)), false, whiteThickStroke);
		}
	}

}
