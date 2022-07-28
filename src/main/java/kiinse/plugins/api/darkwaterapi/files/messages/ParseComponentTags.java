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