package www.xuzhiguang.com.workmanager.workManager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import www.xuzhiguang.com.workmanager.RETURNSTATUSKEY
import www.xuzhiguang.com.workmanager.TAG
import www.xuzhiguang.com.workmanager.TIME

class WorkImpl(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    //实现 Worker后必须重载的方法
    override fun doWork(): Result {
        //通过 inputData对象获取参数
        var time = inputData.getString(TIME)
        var returnStatus = inputData.getString("returnStatus")
        //当任务处理完了，可以通过给outputData变量赋值，将处理结果返回
        //如果是请求链条模式，outputData的值是下一个请求的inputData的值.
        outputData = Data.Builder().putString(TIME, "传给下一个请求时间$time").build()
        return Worker.Result.SUCCESS
    }
}