package mike.myjobscheduler

import android.os.Handler
import android.os.Message
import android.widget.TextView
import java.lang.ref.WeakReference

class MessengerHandler(activity: MyJobSchedulerActivity) : Handler() {
    private val activity: WeakReference<MyJobSchedulerActivity> = WeakReference(activity)

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val activity = activity.get() ?: return
        val text = activity.findViewById<TextView>(R.id.text)
        when (msg.what) {
            MSG_SCHEDULE_DONE -> {
                text.text = "10-seconds schedule done"
            }
        }
    }
}