package net.ciyuan.meowclient.util
import java.util.concurrent.CopyOnWriteArrayList

fun runIn(ticks: Int, server: Boolean = false, func: () -> Unit) {
    TickUtils.schedule(ticks, server, func)
}

object TickUtils
{
    val serverTasks = CopyOnWriteArrayList<DelayedTask>()
    val clientTasks = CopyOnWriteArrayList<DelayedTask>()

    fun processTasks(tasks: CopyOnWriteArrayList<DelayedTask>) {
        val iterator = tasks.iterator()
        while (iterator.hasNext()) {
            val task = iterator.next()
            if (--task.remainingTicks <= 0) {
                task.func()
                tasks.remove(task)
            }
        }
    }

    fun schedule(ticks: Int, server: Boolean, func: () -> Unit) {
        if (ticks <= 0) {
            func()
            return
        }
        // 根据server参数选择任务队列
        val targetList = if (server) serverTasks else clientTasks
        targetList.add(DelayedTask(ticks, func))
    }
}

data class DelayedTask(
    var remainingTicks: Int,
    val func: () -> Unit
)