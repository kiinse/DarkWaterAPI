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
package kiinse.me.plugins.darkwaterapi.api.schedulers

import kiinse.me.plugins.darkwaterapi.api.exceptions.SchedulerException


@Suppress("unused")
interface SchedulersManager {
    @Throws(SchedulerException::class) fun register(scheduler: Scheduler): SchedulersManager
    @Throws(SchedulerException::class) fun startScheduler(scheduler: Scheduler): SchedulersManager
    @Throws(SchedulerException::class) fun stopScheduler(scheduler: Scheduler): SchedulersManager
    fun stopSchedulers(): SchedulersManager
    fun hasScheduler(scheduler: Scheduler): Boolean
    fun getSchedulerByName(name: String): Scheduler?
    @Throws(SchedulerException::class) fun unregister(scheduler: Scheduler): SchedulersManager
    val allSchedulers: Set<Scheduler?>
}