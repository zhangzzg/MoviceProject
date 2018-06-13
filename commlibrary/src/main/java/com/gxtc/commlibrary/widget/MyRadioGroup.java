package com.gxtc.commlibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.gxtc.commlibrary.R;


/**
 * 可滑动圆点的radiogroup
 * setCount(int count,ViewPager viewPager)
 * 
 * OnPageChangeListener 监听viewpager的滚动
 * 
 * 
 * @author 伍玉南
 *
 */
public class MyRadioGroup extends LinearLayout {

	private Context context; 
	
	private int validCount;		//有效的tab数
	
	private int tabCount;		//tab的个数
	
	private float radius;		//圆点的半径
	
	private Paint mPaint;	//绿色圆点画笔
	
	private Paint wPaint;	//白色圆点画笔
	
	private int tabWidth=0;	//每个tab的宽度
	
	private float offsetX=0;	//圆点的偏移量
	
	private float initX;		//圆点初始化X轴位置
	
	private float initY;		//圆点初始化Y轴位置
	
	private int parentWidth;	//父布局的宽度
	
	private boolean isInit =false;		//判断是否初始化
	
	private ViewPager viewPager;	//要关联的viewpager
	
	private OnPageChangeListener pageChangeListener;
	
	public MyRadioGroup(Context context) {
		super(context);
		this.context=context;
		init();
	}
	public MyRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		init();
	}
	
	public MyRadioGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context=context;
		init();
	}
	
	private void init() {
		mPaint=new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(context.getResources().getColor(R.color.colorAccent));
		wPaint=new Paint();
		wPaint.setAntiAlias(true);
		wPaint.setColor(context.getResources().getColor(R.color.white));
	}
	
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	/**
	 * 初始化圆的半径
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		parentWidth = w;
//		validCount = 3;
		tabWidth = parentWidth/8;
		radius = h/2;
		initY = h/2;
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		canvas.save();
		
		if(isInit){
			int position = 0;
			initX=(parentWidth/2)-(tabWidth*validCount)/2;
			for(int i=0;i<validCount;i++){
				canvas.drawCircle(position+radius+initX, initY, radius, mPaint);
				canvas.drawCircle(position+radius+initX,initY,radius-1,wPaint);
				position+=tabWidth;
			}
			canvas.drawCircle(offsetX+radius+initX,initY, radius,mPaint);
			canvas.restore();
			
		}
		
	}
	
	
	private void scroll(int position, float offset) {
		offsetX = (int) (tabWidth*(position+offset));
		invalidate();
	}
	
	
	
	
	
	/**
	 * 设置要关联的viewpager
	 * @param viewPager
	 */
	public void setCount(int count,ViewPager viewPager){
		setCount(count, viewPager, false);
	}
	
	/**
	 * 设置要关联的viewpager
	 * @param viewPager
	 */
	@SuppressWarnings("deprecation")
	public void setCount(int count, ViewPager viewPager, boolean isLoop){
		this.viewPager = viewPager;
		validCount = count;

		tabCount = count;
		isInit = true;
		tabWidth = parentWidth/8;
		viewPager.setOnPageChangeListener(pagerListener);
		invalidate();
		
	}
	
	/**
	 * 设置当前小圆点位置
	 * @param position
	 */
	private void setCurrentIndex(int position){
		offsetX = (int) (tabWidth*(position));
		invalidate();
	}
	
	
	/**
	 * 设置viewpager当前页
	 */
	public void setCurrentPager(int index){
		viewPager.setCurrentItem(index, true);
		invalidate();
	}
	

	/**
	 * 翻页监听
	 */
	private ViewPager.OnPageChangeListener pagerListener =
			new ViewPager.OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			if(pageChangeListener!=null){
				pageChangeListener.onPageSelected(arg0);
			}
			
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			scroll(arg0,arg1);
			if(pageChangeListener!=null){
				pageChangeListener.onPageScrolled(arg0, arg1, arg2);;
			}
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			if(pageChangeListener!=null){
				pageChangeListener.onPageScrollStateChanged(arg0);
			}
			
		}
	};
	
	

	//viewpager 滑动监听
	public interface OnPageChangeListener{
		public void onPageSelected(int arg0);
		public void onPageScrolled(int arg0, float arg1, int arg2);
		public void onPageScrollStateChanged(int arg0);
	}
	
	public void setOnPageChangeListener(OnPageChangeListener listener){
		this.pageChangeListener = listener;
	}
	


}
