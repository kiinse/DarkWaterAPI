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
package kiinse.me.plugins.darkwaterapi.api.files.filemanager

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.exceptions.JsonFileException
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

@Suppress("unused")
open class JsonFile(plugin: DarkWaterJavaPlugin, fileName: FilesKeys) : FilesManager(plugin) {
    private val plugin: DarkWaterJavaPlugin
    private val file: File

    init {
        this.plugin = plugin
        if (isFileNotExists(fileName)) copyFile(fileName)
        file = getFile(fileName)
    }

    @get:Throws(JsonFileException::class)
    val jsonFromFile: JSONObject
        get() {
            try {
                BufferedReader(FileReader(file.absolutePath)).use {
                    val json = JSONObject(Files.readString(Paths.get(file.absolutePath)))
                    plugin.sendLog("File '&b${file.name}&a' loaded")
                    return json
                }
            } catch (e: Exception) {
                when (e) {
                    is JSONException -> return JSONObject()
                    else -> throw JsonFileException(e)
                }
            }
        }

    @Throws(JsonFileException::class)
    fun saveJsonToFile(json: JSONObject) {
        try {
            if (!file.exists() && file.createNewFile()) plugin.sendLog("File '&b${file.name}&a' created")
            Files.write(Paths.get(file.absolutePath), listOf(json.toString()), StandardCharsets.UTF_8)
            plugin.sendLog("File '&b${file.name}&a' saved")
        } catch (e: IOException) {
            throw JsonFileException(e)
        }
    }
}