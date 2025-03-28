package net.ciyuan.meowclient.util

import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

open class TaskExecutor(val delay: () -> Long, private val profileName: String = "Unspecified TaskExecutor", val shouldRun: () -> Boolean = { true }, val func: Executable) {

    constructor(delay: Long, profileName: String = "Unspecified TaskExecutor", shouldRun: () -> Boolean = { true }, func: Executable) : this({ delay }, profileName, shouldRun, func)

    internal val clock = TimeUtils()
    internal var shouldFinish = false

    open fun run(): Boolean {
        if (shouldFinish) return true
        if (clock.hasTimePassed(delay(), true)) {
            profile(profileName) {
                runCatching {
                    func()
                }
            }
        }
        return false
    }

    /**
     * Starts an TaskExecutor that ends after a certain number of times.
     */
    class LimitedTaskExecutor(delay: Long, repeats: Int, profileName: String = "Unspecified TaskExecutor", shouldRun: () -> Boolean = { true }, func: Executable) : TaskExecutor(delay, profileName, shouldRun, func) {
        private val repeats = repeats - 1
        private var totalRepeats = 0

        override fun run(): Boolean {
            if (shouldFinish) return true
            if (clock.hasTimePassed(delay(), true)) {
                runCatching {
                    if (totalRepeats >= repeats) destroyTaskExecutor()
                    totalRepeats++
                    func()
                }
            }
            return false
        }
    }

    /**
     * Allows stopping executing an TaskExecutor permanently
     *
     * Returning [Nothing] allows for us to stop running the function without specifying
     * @author Stivais
     */
    fun TaskExecutor.destroyTaskExecutor(): Nothing {
        shouldFinish = true
        throw Throwable()
    }

    companion object {
        private val TaskExecutors = ArrayList<TaskExecutor>()

        fun TaskExecutor.register() {
            TaskExecutors.add(this)
        }

        @SubscribeEvent
        fun onRender(event: RenderWorldLastEvent) {
            profile("TaskExecutors") {
                TaskExecutors.removeAll {
                    if (!it.shouldRun()) return@removeAll false
                    else it.run()
                }
            }
        }
    }
}

/**
 * Here for more readability
 */
typealias Executable = TaskExecutor.() -> Unit