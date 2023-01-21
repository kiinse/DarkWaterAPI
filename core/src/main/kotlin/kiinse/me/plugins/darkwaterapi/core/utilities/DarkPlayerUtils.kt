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
package kiinse.me.plugins.darkwaterapi.core.utilities

import kiinse.me.plugins.darkwaterapi.api.utilities.PermissionsKeys
import kiinse.me.plugins.darkwaterapi.core.schedulers.darkwater.JumpSchedule
import kiinse.me.plugins.darkwaterapi.core.schedulers.darkwater.MoveSchedule
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder
import org.bukkit.*
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.permissions.Permission
import org.bukkit.potion.PotionEffectType
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.*

@Suppress("unused")
object DarkPlayerUtils {
    fun isActing(player: Player): Boolean {
        return isJumping(player) || player.isSprinting || player.isInWater || isClimbing(player)
    }

    fun isWalking(player: Player): Boolean {
        val value = MoveSchedule.notMovingMap[player.uniqueId]
        return value != null && value <= 10
    }

    fun isJumping(player: Player): Boolean {
        return JumpSchedule.getJumpingMap().containsKey(player.uniqueId)
    }

    private fun isClimbing(player: Player): Boolean {
        return player.isClimbing && isWalking(player)
    }

    fun isSurvivalAdventure(player: Player): Boolean {
        val gameMode = player.gameMode
        return gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE
    }

    fun isSurvivalAdventure(sender: CommandSender): Boolean {
        val gameMode = getPlayer(sender).gameMode
        return gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE
    }

    fun isCreativeSpectator(player: Player): Boolean {
        val gameMode = player.gameMode
        return gameMode == GameMode.CREATIVE || gameMode == GameMode.SPECTATOR
    }

    fun isCreativeSpectator(sender: CommandSender): Boolean {
        val gameMode= getPlayer(sender).gameMode
        return gameMode == GameMode.CREATIVE || gameMode == GameMode.SPECTATOR
    }

    fun isSurvival(player: Player): Boolean {
        return player.gameMode == GameMode.SURVIVAL
    }

    fun isSurvival(sender: CommandSender): Boolean {
        return getPlayer(sender).gameMode == GameMode.SURVIVAL
    }

    fun isAdventure(player: Player): Boolean {
        return player.gameMode == GameMode.ADVENTURE
    }

    fun isAdventure(sender: CommandSender): Boolean {
        return getPlayer(sender).gameMode == GameMode.ADVENTURE
    }

    fun isCreative(player: Player): Boolean {
        return player.gameMode == GameMode.CREATIVE
    }

    fun isCreative(sender: CommandSender): Boolean {
        return getPlayer(sender).gameMode == GameMode.CREATIVE
    }

    fun isSpectator(player: Player): Boolean {
        return player.gameMode == GameMode.SPECTATOR
    }

    fun isSpectator(sender: CommandSender): Boolean {
        return getPlayer(sender).gameMode == GameMode.SPECTATOR
    }

    fun isPoisoned(player: Player): Boolean {
        return player.hasPotionEffect(PotionEffectType.POISON)
    }

    fun isPoisoned(sender: CommandSender): Boolean {
        return getPlayer(sender).hasPotionEffect(PotionEffectType.POISON)
    }

    fun isInRain(player: Player): Boolean {
        val world = player.world
        val block = world.getHighestBlockAt(player.location)
        return !world.isClearWeather && block.location.y > player.location.y && block.type == Material.AIR
    }

    fun isInRain(sender: CommandSender): Boolean {
        val player = getPlayer(sender)
        val world = player.world
        val block = world.getHighestBlockAt(player.location)
        return !world.isClearWeather && block.location.y > player.location.y && block.type == Material.AIR
    }

    fun isInLava(player: Player): Boolean {
        return player.location.block.type == Material.LAVA
    }

    fun isInLava(sender: CommandSender): Boolean {
        return getPlayer(sender).location.block.type == Material.LAVA
    }

    fun playSound(sender: CommandSender, sound: Sound, v: Float, v1: Float) {
        if (sender is ConsoleCommandSender) return
        val player = getPlayer(sender)
        player.playSound(player, sound, v, v1)
    }

    fun playSound(player: Player, sound: Sound, v: Float, v1: Float) {
        player.playSound(player, sound, v, v1)
    }

    fun playSound(sender: CommandSender, sound: Sound, v: Float) {
        if (sender is ConsoleCommandSender) return
        val player = getPlayer(sender)
        player.playSound(player, sound, v, 1f)
    }

    fun playSound(player: Player, sound: Sound, v: Float) {
        player.playSound(player, sound, v, 1f)
    }

    fun playSound(sender: CommandSender, sound: Sound) {
        if (sender is ConsoleCommandSender) return
        val player = getPlayer(sender)
        player.playSound(player, sound, 1f, 1f)
    }

    fun playSound(player: Player, sound: Sound) {
        player.playSound(player, sound, 1f, 1f)
    }

    fun getPlayer(uuid: UUID): Player? {
        return Bukkit.getPlayer(uuid)
    }

    fun getPlayer(name: String): Player? {
        return Bukkit.getPlayer(name)
    }

    fun getPlayer(event: PlayerEvent): Player {
        return event.player
    }

    fun getPlayer(sender: CommandSender): Player {
        return sender as Player
    }

    fun getPlayerName(sender: CommandSender): String {
        return sender.name
    }

    fun sendActionBar(player: Player, message: String) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, *ComponentBuilder(message).create())
    }

    fun sendActionBar(player: Player, message: BaseComponent) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, message)
    }

    fun sendActionBar(sender: CommandSender, message: String) {
        getPlayer(sender).spigot().sendMessage(ChatMessageType.ACTION_BAR, *ComponentBuilder(message).create())
    }

    fun sendActionBar(sender: CommandSender, message: BaseComponent) {
        getPlayer(sender).spigot().sendMessage(ChatMessageType.ACTION_BAR, message)
    }

    fun hasPermission(sender: CommandSender, permission: PermissionsKeys): Boolean {
        return sender.hasPermission(formatPermissionsKey(permission))
    }

    fun hasPermission(player: Player, permission: PermissionsKeys): Boolean {
        return player.hasPermission(formatPermissionsKey(permission))
    }

    fun hasPermission(sender: CommandSender, permission: Permission): Boolean {
        return sender.hasPermission(permission)
    }

    fun hasPermission(player: Player, permission: Permission): Boolean {
        return player.hasPermission(permission)
    }

    fun hasPermissions(sender: CommandSender, permissions: Array<PermissionsKeys>): Boolean {
        var i = 0
        permissions.forEach { if (sender.hasPermission(formatPermissionsKey(it))) i++ }
        return i == permissions.size
    }

    fun hasPermissions(player: Player, permissions: Array<PermissionsKeys>): Boolean {
        var i = 0
        permissions.forEach { if (player.hasPermission(formatPermissionsKey(it))) i++ }
        return i == permissions.size
    }

    fun hasOneOfPermissions(sender: CommandSender, permissions: Array<PermissionsKeys>): Boolean {
        var isTrue = false
        permissions.forEach {
            if (isTrue) return true
            if (sender.hasPermission(formatPermissionsKey(it))) isTrue = true
        }
        return isTrue
    }

    fun hasOneOfPermissions(player: Player, permissions: Array<PermissionsKeys>): Boolean {
        var isTrue = false
        permissions.forEach {
            if (isTrue) return true
            if (player.hasPermission(formatPermissionsKey(it))) isTrue = true
        }
        return isTrue
    }

    fun giveItem(player: Player, item: ItemStack) {
        player.inventory.addItem(item)
    }

    fun giveItem(sender: CommandSender, item: ItemStack) {
        getPlayer(sender).inventory.addItem(item)
    }

    @Throws(IOException::class)
    fun getPlayerID(player: String): String {
        val bufferedReader = BufferedReader(InputStreamReader(URL("https://api.mojang.com/users/profiles/minecraft/${player.replace(" ", "_")}").openConnection().getInputStream()))
        return JSONObject(bufferedReader.readLine()).getString("id")
    }

    @Throws(IOException::class)
    fun getPlayerTextures(uuid: String): String {
        val bufferedReader = BufferedReader(InputStreamReader(URL("https://sessionserver.mojang.com/session/minecraft/profile/$uuid?unsigned=false").openConnection().getInputStream()))
        val sb = StringBuilder()
        while (bufferedReader.ready()) sb.append(bufferedReader.readLine())
        val data = JSONObject(sb.toString()).getJSONArray("properties")[0].toString()
        return JSONObject(data).getString("value")
    }

    private fun formatPermissionsKey(permission: PermissionsKeys): String {
        return permission.toString().lowercase(Locale.getDefault()).replace("_", ".")
    }
}