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

package kiinse.plugins.api.darkwaterapi.commands.manager;

import kiinse.plugins.api.darkwaterapi.commands.manager.interfaces.CommandFailureHandler;
import kiinse.plugins.api.darkwaterapi.commands.manager.reasons.CommandFailReason;
import kiinse.plugins.api.darkwaterapi.files.messages.MessagesUtilsImpl;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.MessagesUtils;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FailureHandler implements CommandFailureHandler {
    private final MessagesUtils messagesUtils;

    public FailureHandler(@NotNull DarkWaterJavaPlugin plugin) {
        this.messagesUtils = new MessagesUtilsImpl(plugin.getDarkWaterAPI());
    }

    @Override
    public void handleFailure(@NotNull CommandFailReason reason, @NotNull CommandSender sender, @Nullable RegisteredCommand command) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(DarkWaterUtils.colorize("&cYou cannot execute this command outside of the game!"));
        } else {
            messagesUtils.sendMessageWithPrefix(sender, reason);
        }
    }
}
