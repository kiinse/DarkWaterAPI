package kiinse.me.plugins.darkwaterapi.core.commands

import kiinse.me.plugins.darkwaterapi.api.commands.CommandContext
import kiinse.me.plugins.darkwaterapi.api.files.locale.PlayerLocale
import org.bukkit.command.CommandSender

class DarkContext(override val sender: CommandSender, override val senderLocale: PlayerLocale, override val args: Array<String>) : CommandContext {

    override fun equals(other: Any?): Boolean {
        return other != null && other is CommandContext && other.hashCode() == hashCode()
    }

    override fun toString(): String {
        return """
            Sender: ${sender.name}
            Locale: $senderLocale
            Args: ${args.contentToString()}
            """.trimIndent()
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }
}