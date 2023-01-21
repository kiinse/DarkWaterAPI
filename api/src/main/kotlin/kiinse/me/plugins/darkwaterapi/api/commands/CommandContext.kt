package kiinse.me.plugins.darkwaterapi.api.commands

import kiinse.me.plugins.darkwaterapi.api.files.locale.PlayerLocale
import org.bukkit.command.CommandSender

@Suppress("UNUSED")
interface CommandContext {
    val sender: CommandSender
    val args: Array<String>
    val senderLocale: PlayerLocale
}