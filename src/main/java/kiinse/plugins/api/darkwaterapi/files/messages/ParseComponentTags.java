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

package kiinse.plugins.api.darkwaterapi.files.messages;

import kiinse.plugins.api.darkwaterapi.files.messages.enums.ComponentAction;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.ComponentTags;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class ParseComponentTags implements ComponentTags {

    @Override
    public @NotNull Component parseMessage(@NotNull String msg) {
        var result = Component.text(msg);
        if (hasTextEndLabels(msg)) {
            result = Component.text("");
            for (var arg : msg.split("<")) {
                if (!hasTextLabels(arg) && !hasTextEndLabels(arg) && !arg.isEmpty()) {
                    result = result.append(Component.text(arg));
                } else {
                    var text = arg.split(">");
                    if (hasTextLabels(arg)) {
                        var label = text[0].split("=");
                        result = result.append(getComponent(text[1], label[1], ComponentAction.valueOf(label[0])));
                    }
                    if (hasTextEndLabels(arg) && text.length > 1) {
                        result = result.append(Component.text(text[1]));
                    }
                }
            }
        }
        return result;
    }

    private boolean hasTextLabels(@NotNull String msg) {
        for (var action : ComponentAction.values()) {
            if (msg.contains(action + "=")) {
                return true;
            }
        }
        return false;
    }

    private boolean hasTextEndLabels(@NotNull String msg) {
        for (var action : ComponentAction.values()) {
            if (msg.contains("/" + action + ">")) {
                return true;
            }
        }
        return false;
    }

    private @NotNull Component getComponent(@NotNull String text, @NotNull String click, @NotNull ComponentAction action) {
        var message = click.split("::");
        return switch (action) {
            case CMD -> Component.text(text).clickEvent(ClickEvent.runCommand(message[0])).hoverEvent(HoverEvent.showText(getHoverText(message)));
            case URL -> Component.text(text).clickEvent(ClickEvent.openUrl(message[0])).hoverEvent(HoverEvent.showText(getHoverText(message)));
            case COPY -> Component.text(text).clickEvent(ClickEvent.copyToClipboard(message[0])).hoverEvent(HoverEvent.showText(getHoverText(message)));
            case HOVER -> Component.text(text).hoverEvent(HoverEvent.showText(getHoverText(message)));
        };
    }

    private @NotNull Component getHoverText(@NotNull String[] text) {
        return Component.text(ChatColor.translateAlternateColorCodes('&', text.length > 1 ? text[1] : text[0]));
    }

}