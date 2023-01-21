package kiinse.me.plugins.darkwaterapi.api.commands

@Suppress("UNUSED")
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class SubCommand(
        /**
         * The command that the player must execute. For example /darkwater reload. Should start with a slash
         *
         * @return Command
         */
        val command: String,
        /**
         * Number of received parameters. Default - 0
         *
         * @return Number of parameters
         */
        val parameters: Int = 0,
        /**
         * Allows you to disable restrictions on the number of parameters in a command. Disabled by default
         *
         * @return Enabled or Disabled
         */
        val overrideParameterLimit: Boolean = false,
        /**
         * Allows you to disable the use of the command by non-players (for example, via the console). Disabled by default
         *
         * @return Enabled or Disabled
         */
        val disallowNonPlayer: Boolean = false,
        /**
         * Required rights to use the command. Default is empty
         *
         * @return Permissions to use the command
         */
        val permission: String = "")