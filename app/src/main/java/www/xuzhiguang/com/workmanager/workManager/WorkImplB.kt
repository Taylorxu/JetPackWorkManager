package www.xuzhiguang.com.workmanager.workManager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import www.xuzhiguang.com.workmanager.RETURNSTATUSKEY
import www.xuzhiguang.com.workmanager.TAG
import www.xuzhiguang.com.workmanager.TIME

class WorkImplB(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    //实现 Worker后必须重载的方法
    override fun doWork(): Result {
        //通过 inputData对象获取参数
        //来自于上一个请求的outputData的值
//        Log.e(TAG, inputData.getString(TIME))
        outputData = Data.Builder().putString(RETURNSTATUSKEY, "最后一个请求SUCCESS").build()
        return Worker.Result.SUCCESS
    }
}