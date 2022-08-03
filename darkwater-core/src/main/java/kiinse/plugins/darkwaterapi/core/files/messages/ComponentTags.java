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

package kiinse.plugins.darkwaterapi.core.files.messages;

import kiinse.plugins.darkwaterapi.api.files.messages.ComponentAction;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class ComponentTags {

    public static @NotNull BaseComponent[] parseMessage(@NotNull String msg) {
        var result = new ComponentBuilder(msg);
        if (hasTextEndLabels(msg)) {
            result = new ComponentBuilder("");
            for (var arg : msg.split("<")) {
                if (!hasTextLabels(arg) && !hasTextEndLabels(arg) && !arg.isEmpty()) {
                    result = result.append(arg);
                } else {
                    var text = arg.split(">");
                    if (hasTextLabels(arg)) {
                        var label = text[0].split("=");
                        result = result.append(getComponent(text[1], label[1], ComponentAction.valueOf(label[0])));
                    }
                    if (hasTextEndLabels(arg) && text.length > 1) {
                        result = result.append(text[1]);
                    }
                }
            }
        }
        return result.create();
    }

    private static boolean hasTextLabels(@NotNull String msg) {
        for (var action : ComponentAction.values()) {
            if (msg.contains(action + "=")) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasTextEndLabels(@NotNull String msg) {
        for (var action : ComponentAction.values()) {
            if (msg.contains("/" + action + ">")) {
                return true;
            }
        }
        return false;
    }

    private static @NotNull BaseComponent[] getComponent(@NotNull String text, @NotNull String click, @NotNull ComponentAction action) {
        var message = click.split("::");
        var builder = new ComponentBuilder(text);

        return switch (action) {
            case CMD -> builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, message[0])).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, getHoverText(message))).create();
            case URL -> builder.event(new ClickEvent(ClickEvent.Action.OPEN_URL, message[0])).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, getHoverText(message))).create();
            case COPY -> builder.event(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, message[0])).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, getHoverText(message))).create();
            case HOVER -> builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, getHoverText(message))).create();
        };
    }

    private static @NotNull Content getHoverText(@NotNull String[] text) {
        return new Text(ChatColor.translateAlternateColorCodes('&', text.length > 1 ? text[1] : text[0]));
    }

    private ComponentTags() {}

}