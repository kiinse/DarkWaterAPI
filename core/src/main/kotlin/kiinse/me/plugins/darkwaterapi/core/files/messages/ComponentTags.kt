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
package kiinse.me.plugins.darkwaterapi.core.files.messages

import kiinse.me.plugins.darkwaterapi.api.files.messages.ComponentAction
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.ChatColor

object ComponentTags {
    fun parseMessage(msg: String): Array<BaseComponent> {
        var result = ComponentBuilder(msg)
        if (hasTextEndLabels(msg)) {
            result = ComponentBuilder("")
            msg.split("<".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().forEach { arg ->
                if (!hasTextLabels(arg) && !hasTextEndLabels(arg) && arg.isNotEmpty()) {
                    result = result.append(arg)
                } else {
                    val text = arg.split(">".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    if (hasTextLabels(arg)) {
                        val label = text[0].split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        result = result.append(getComponent(text[1], label[1], ComponentAction.valueOf(label[0])))
                    }
                    if (hasTextEndLabels(arg) && text.size > 1) result = result.append(text[1])
                }
            }
        }
        return result.create()
    }

    private fun hasTextLabels(msg: String): Boolean {
        ComponentAction.values().forEach { if (msg.contains("$it=")) return true }
        return false
    }

    private fun hasTextEndLabels(msg: String): Boolean {
        ComponentAction.values().forEach { if (msg.contains("/$it>")) return true }
        return false
    }

    private fun getComponent(text: String, click: String, action: ComponentAction): Array<BaseComponent> {
        val message = click.split("::".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val builder = ComponentBuilder(text)
        return when (action) {
            ComponentAction.CMD -> builder.event(ClickEvent(ClickEvent.Action.RUN_COMMAND, message[0])).event(getHoverText(message)).create()
            ComponentAction.URL -> builder.event(ClickEvent(ClickEvent.Action.OPEN_URL, message[0])).event(getHoverText(message)).create()
            ComponentAction.COPY-> builder.event(ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, message[0])).event(getHoverText(message)).create()
            else  -> builder.event(getHoverText(message)).create()
        }
    }

    private fun getHoverText(text: Array<String>): HoverEvent {
        return HoverEvent(HoverEvent.Action.SHOW_TEXT, Text(ChatColor.translateAlternateColorCodes('&', if (text.size > 1) text[1] else text[0])))
    }
}