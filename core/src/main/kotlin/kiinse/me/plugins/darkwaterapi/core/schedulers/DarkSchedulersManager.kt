// MIT License
//
// Copyright (c) 2022 kiinse
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
package kiinse.me.plugins.darkwaterapi.core.schedulers

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.exceptions.SchedulerException
import kiinse.me.plugins.darkwaterapi.api.schedulers.Scheduler
import kiinse.me.plugins.darkwaterapi.api.schedulers.SchedulerData
import kiinse.me.plugins.darkwaterapi.api.schedulers.SchedulersManager

class DarkSchedulersManager(plugin: DarkWaterJavaPlugin) : SchedulersManager {

    private val schedulers: MutableSet<Scheduler> = HashSet()
    private val plugin: DarkWaterJavaPlugin

    init {
        this.plugin = plugin
    }

    @Throws(SchedulerException::class)
    override fun register(scheduler: Scheduler): SchedulersManager {
        val schedule: Scheduler = checkSchedulerData(scheduler)
        if (hasScheduler(schedule)) throw SchedulerException("Scheduler with same name '${scheduler.name}' already exist!")
        schedulers.add(scheduler)
        plugin.sendLog("Scheduler '&b${scheduler.name}&a' by plugin '&b${scheduler.plugin.name}&a' has been registered!")
        scheduler.start()
        return this
    }

    @Throws(SchedulerException::class)
    override fun startScheduler(scheduler: Scheduler): SchedulersManager {
        schedulers.forEach {
            if (it.name == scheduler.name) {
                if (it.isStarted) throw SchedulerException("This scheduler '${scheduler.name}' already started!")
                it.start()
            }
        }
        return this
    }

    @Throws(SchedulerException::class)
    override fun stopScheduler(scheduler: Scheduler): SchedulersManager {
        schedulers.forEach {
            if (it.name == scheduler.name) {
                if (!it.isStarted) throw SchedulerException("This scheduler '${scheduler.name}' already stopped!")
                it.stop()
            }
        }
        return this
    }

    override fun stopSchedulers(): SchedulersManager {
        schedulers.forEach { it.stop() }
        return this
    }

    override fun hasScheduler(scheduler: Scheduler): Boolean {
        schedulers.forEach { if (it.name == scheduler.name) return true }
        return false
    }

    override fun getSchedulerByName(name: String): Scheduler? {
        schedulers.forEach { if (it.name == name) return it }
        return null
    }

    @Throws(SchedulerException::class)
    override fun unregister(scheduler: Scheduler): SchedulersManager {
        if (!hasScheduler(scheduler)) throw SchedulerException("This scheduler '${scheduler.name}' not found!")
        schedulers.forEach {
            if (it.name == scheduler.name) {
                if (it.isStarted) it.stop()
                schedulers.remove(scheduler)
            }
        }
        plugin.sendLog("Scheduler '&b${scheduler.name}&a' by plugin '&b${scheduler.plugin.name}&a' has been unregistered!")
        return this
    }

    private fun checkSchedulerData(scheduler: Scheduler): Scheduler {
        val annotation = scheduler.javaClass.getAnnotation(SchedulerData::class.java)
        if (scheduler.name == null) scheduler.name = annotation.name
        if (scheduler.delay <= -1) scheduler.delay = annotation.delay
        if (scheduler.period <= -1) scheduler.period = annotation.period
        return scheduler
    }

    override val allSchedulers: Set<Scheduler>
        get() = HashSet(schedulers)
}