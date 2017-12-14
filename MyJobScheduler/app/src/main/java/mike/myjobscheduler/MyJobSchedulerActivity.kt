package mike.myjobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Messenger
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MyJobSchedulerActivity : AppCompatActivity() {
    private lateinit var jobServiceComponentName: ComponentName

    private lateinit var jobScheduler: JobScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.scheduleButton).setOnClickListener { scheduleJob() }

        jobServiceComponentName = ComponentName(this, MyJobService::class.java)
        jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MyJobService::class.java)
        intent.putExtra(MESSENGER_INTENT_KEY, obtainMessenger())
        startService(intent)
    }

    override fun onStop() {
        super.onStop()
        stopService(Intent(this, MyJobService::class.java))
        jobScheduler.cancelAll()
    }

    private fun scheduleJob() {
        val builder = JobInfo.Builder(JOB_ID, jobServiceComponentName)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        jobScheduler.schedule(builder.build())
    }

    private fun obtainMessenger(): Messenger {
        return Messenger(MessengerHandler(this))
    }
}
