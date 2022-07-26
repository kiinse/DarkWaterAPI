package kiinse.plugins.api.darkwaterapi.rest.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SuppressWarnings("unused")
public class ExecuteCMD implements ConsoleCommandSender {

    String cmdOut = null;
    private final Server server;

    public ExecuteCMD(Server server) {
        this.server = server;
    }

    @Override
    public void sendMessage(@NotNull String message) {
        cmdOut = message;
    }

    public String getOutput() {
        return cmdOut;
    }

    @Override
    public void sendMessage(@NotNull String... messages) {
        cmdOut = Arrays.toString(messages);
    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String message) {
        cmdOut = message;
    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String... messages) {
        cmdOut = Arrays.toString(messages);
    }

    @NotNull
    @Override
    public Server getServer() {
        return server;
    }

    @NotNull
    @Override
    public String getName() {
        return "DarkWaterAPI";
    }

    @NotNull
    @Override
    public Spigot spigot() {
        return null;
    }

    @Override
    public @NotNull Component name() {
        return Component.text("DarkWaterAPI Console");
    }

    @Override
    public boolean isConversing() {
        return false;
    }

    @Override
    public void acceptConversationInput(@NotNull String input) {
    }

    @Override
    public boolean beginConversation(@NotNull Conversation conversation) {
        return false;
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation) {

    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation, @NotNull ConversationAbandonedEvent details) {

    }

    @Override
    public void sendRawMessage(@NotNull String message) {

    }

    @Override
    public void sendRawMessage(@Nullable UUID sender, @NotNull String message) {

    }

    @Override
    public boolean isPermissionSet(@NotNull String name) {
        return true;
    }

    @Override
    public boolean isPermissionSet(@NotNull Permission perm) {
        return true;
    }

    @Override
    public boolean hasPermission(@NotNull String name) {
        return true;
    }

    @Override
    public boolean hasPermission(@NotNull Permission perm) {
        return true;
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value) {
        return null;
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        return null;
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value, int ticks) {
        return null;
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks) {
        return null;
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment attachment) {

    }

    @Override
    public void recalculatePermissions() {

    }

    @NotNull
    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return new Set<>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NotNull
            @Override
            public Iterator<PermissionAttachmentInfo> iterator() {
                return null;
            }

            @NotNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NotNull
            @Override
            public <T> T[] toArray(@NotNull T[] a) {
                return null;
            }

            @Override
            public boolean add(PermissionAttachmentInfo permissionAttachmentInfo) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NotNull Collection<? extends PermissionAttachmentInfo> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }
        };
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public void setOp(boolean value) {
    }
}
