import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import java.util.*

abstract class DebouncedOnTouchListener(private val minimumInterval: Long = 300): View.OnTouchListener {

    private val lastTouchMap: MutableMap<View, Long>


    /**
     * Implement this in your subclass instead of onClick
     * @param v The view that was clicked
     */
    abstract fun onDebouncedTouch(v: View)

    init {
        this.lastTouchMap = WeakHashMap()
    }


    override fun onTouch(clickedView: View, event : MotionEvent) : Boolean {
        val previousClickTimestamp = lastTouchMap[clickedView]
        val currentTimestamp = SystemClock.uptimeMillis()

        lastTouchMap[clickedView] = currentTimestamp
        if (previousClickTimestamp == null || Math.abs(currentTimestamp - previousClickTimestamp.toLong()) > minimumInterval) {
            onDebouncedTouch(clickedView)
            return true
        }
        return false
    }

}