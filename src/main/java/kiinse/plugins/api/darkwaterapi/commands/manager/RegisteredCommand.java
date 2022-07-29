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

import kiinse.plugins.api.darkwaterapi.commands.manager.annotation.Command;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public final class RegisteredCommand {

    private final Object instance;

    private final Method method;

    private final Command annotation;

    RegisteredCommand(@NotNull Method method, @NotNull Object instance, @NotNull Command annotation) {
        this.method = method;
        this.instance = instance;
        this.annotation = annotation;
    }

    public @NotNull Command getAnnotation() {
        return annotation;
    }

    public @NotNull Method getMethod() {
        return method;
    }

    public @NotNull Object getInstance() {
        return instance;
    }
}