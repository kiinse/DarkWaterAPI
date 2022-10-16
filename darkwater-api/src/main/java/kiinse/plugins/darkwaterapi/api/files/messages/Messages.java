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

package kiinse.plugins.darkwaterapi.api.files.messages;

import kiinse.plugins.darkwaterapi.api.exceptions.JsonFileException;
import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocale;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;

@SuppressWarnings("unused")
public interface Messages {

    void reload() throws JsonFileException;

    @NotNull String getStringMessage(@NotNull PlayerLocale playerLocale, @NotNull MessagesKeys message);

    @NotNull String getStringMessageWithPrefix(@NotNull PlayerLocale playerLocale, @NotNull MessagesKeys message);

    @NotNull JSONObject getAllLocaleMessages(@NotNull PlayerLocale playerLocale);

    @NotNull HashMap<String, JSONObject> getAllMessages();

    @NotNull String getPrefix(@NotNull PlayerLocale playerLocale);
}
