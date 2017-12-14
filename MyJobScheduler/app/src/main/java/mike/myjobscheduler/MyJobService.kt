package mike.myjobscheduler

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.os.Messenger

class MyJobService : JobService() {
    private lateinit var messenger: Messenger

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        messenger = intent.getParcelableExtra(MESSENGER_INTENT_KEY)
        return Service.START_NOT_STICKY
    }

    override fun onStartJob(params: JobParameters): Boolean {
        Handler().postDelayed({
            sendMessage(MSG_SCHEDULE_DONE)
            jobFinished(params, false)
        }, PROGRESS_MILLIS)
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        sendMessage(MSG_SCHEDULE_DONE)
        return false
    }

    private fun sendMessage(what: Int) {
        val message = Message.obtain()
        message.what = what
        messenger.send(message)
    }
}
