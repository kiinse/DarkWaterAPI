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

package kiinse.plugins.darkwaterapi.common.initialize;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.common.listeners.*;
import org.jetbrains.annotations.NotNull;

public class RegisterEvents {

    public RegisterEvents(@NotNull DarkWaterJavaPlugin plugin) {
        plugin.sendLog("Registering listeners...");
        var pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new EntityDeathListener(plugin), plugin);
        pluginManager.registerEvents(new MoveListener(), plugin);
        pluginManager.registerEvents(new GUIListener(plugin), plugin);
        pluginManager.registerEvents(new CloseInventoryListener(), plugin);
        pluginManager.registerEvents(new OnJoinListener(plugin), plugin);
        pluginManager.registerEvents(new OnQuitListener(), plugin);
        plugin.sendLog("Listeners registered");
    }
}
