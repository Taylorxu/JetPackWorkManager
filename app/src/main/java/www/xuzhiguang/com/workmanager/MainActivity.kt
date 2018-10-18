package www.xuzhiguang.com.workmanager

import android.arch.lifecycle.LiveData
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*
import www.xuzhiguang.com.workmanager.workManager.WorkImpl
import www.xuzhiguang.com.workmanager.workManager.WorkImplB
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.SimpleFormatter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        workerPlay()
    }

    /**
     *
     *  约束条件 网络部分  ;NOT_REQUIRED 无约束; CONNECTED  网络连接状态;UNMETERED  连接无线网络
     *  NOT_ROAMING  连接非漫游网络;METERED 手机流量
     */
    val myConstraints = Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED).build()


    fun workerPlay() {
        val dateFormat = SimpleDateFormat("yyyy:MM:dd hh:mm:ss", Locale.getDefault())
        //将参数数据放到一个集合中
        var data = Data.Builder().putString(TIME, dateFormat.format(Date())).build()
        //定义workRequest onTimeWorkRequest 只执行一遍
        val requestA = OneTimeWorkRequest.Builder(WorkImpl::class.java)
                .setInputData(data)   //在请求中添加参数
                .setConstraints(myConstraints) //只有在有网络连接的时候才执行
                .build()
        val requestB = OneTimeWorkRequest.Builder(WorkImplB::class.java)
                .setInputData(data)   //在请求中添加参数
                .setConstraints(myConstraints) //只有在有网络连接的时候才执行
                .build()

        //定时查询，时间间隔最小是15分钟
        /*val request2 = PeriodicWorkRequest.Builder(WorkImpl::class.java, 15, TimeUnit.MINUTES)
                .setInputData(data)   //在请求中添加参数
                .setConstraints(myConstraints) //只有在有网络连接的时候才执行
                .build()
*/

        //加入任务队列链 上一个任务的 outputData 会成为下一个任务的 inputData。
        WorkManager.getInstance().beginWith(requestA).then(requestB).enqueue()
//        WorkManager.getInstance().enqueue(requestA)

        //接收返回的状态
        WorkManager.getInstance().getStatusById(requestA.id)
                .observe(this, android.arch.lifecycle.Observer { workStatus ->
                    if (workStatus != null && workStatus.state.isFinished) {
                        Log.e(TAG, workStatus.outputData.getString(TIME))
                    }
                })
        //接收返回的状态
        WorkManager.getInstance().getStatusById(requestB.id)
                .observe(this, android.arch.lifecycle.Observer { workStatus ->
                    if (workStatus != null && workStatus.state.isFinished) {
                        Log.e(TAG, workStatus.outputData.getString(RETURNSTATUSKEY))
                    }
                })


    }


}

