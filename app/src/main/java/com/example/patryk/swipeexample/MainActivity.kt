package com.example.patryk.swipeexample

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var gestureDetectorListener : MyGestureDetector
    lateinit var gestureDetector :GestureDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gestureDetectorListener = MyGestureDetector(this)
        gestureDetector = GestureDetector(this, gestureDetectorListener)
        gestureDetectorListener.onSwipeDown= {
            Toast.makeText(this,"swipe Down",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
}

class MyGestureDetector(var context:Context): GestureDetector.OnGestureListener
{
    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    var onSwipeRight : ()-> Unit = {
        Toast.makeText(context,"swipe Down",Toast.LENGTH_SHORT).show()}
    var onSwipeLeft : ()-> Unit = {}
    var onSwipeUp :  () -> Unit = {}
    var onSwipeDown : ()-> Unit = {}
    var swipeLen =100
    var swipeVel =500
    override fun onFling(down: MotionEvent?, move: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        if (down==null || move==null ) return false
        val diffX = move.x -down.x
        val diffY = move.y -down.y
        var consumed =false
        if(Math.abs(diffX) > Math.abs(diffY))
        {
            //wipe right or left
            if(Math.abs(diffX) > swipeLen && Math.abs(velocityX) > swipeVel){
                if (diffX > 0 ){
                    onSwipeRight()
                    consumed = true
                }
                else{
                    onSwipeLeft()
                    consumed = true
                }
            }
        }
        else{
            if(Math.abs(diffY) > swipeLen && Math.abs(velocityY) > swipeVel){
                if (diffY > 0 ){
                    onSwipeDown()
                    consumed = true
                }
                else{
                    onSwipeUp()
                    consumed = true
                }
            }
        }

        return consumed
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }
}