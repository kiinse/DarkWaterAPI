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
package kiinse.me.plugins.darkwaterapi.api.databases

@Suppress("UNUSED")
class SQLConnectionSettings {
    var host = "localhost"
        private set
    var port = "5432"
        private set
    var login = "postgres"
        private set
    var password = "postgres"
        private set
    var urlDriver = "postgresql"
        private set
    var dbName = "darkwaterapi"
        private set

    @Throws(IllegalArgumentException::class)
    fun setURLDriver(urlDriver: String): SQLConnectionSettings {
        require(urlDriver.isNotBlank()) { "SQL database url driver is empty" }
        this.urlDriver = urlDriver
        return this
    }

    @Throws(IllegalArgumentException::class)
    fun setHost(sqlHost: String): SQLConnectionSettings {
        require(sqlHost.isNotBlank()) { "SQL host is empty" }
        host = sqlHost
        return this
    }

    @Throws(IllegalArgumentException::class)
    fun setPort(sqlPort: String): SQLConnectionSettings {
        require(sqlPort.isNotBlank()) { "SQL port is empty" }
        port = sqlPort
        return this
    }

    @Throws(IllegalArgumentException::class)
    fun setLogin(sqlLogin: String): SQLConnectionSettings {
        require(sqlLogin.isNotBlank()) { "SQL login is empty" }
        login = sqlLogin
        return this
    }

    @Throws(IllegalArgumentException::class)
    fun setPassword(sqlPassword: String): SQLConnectionSettings {
        require(sqlPassword.isNotBlank()) { "SQL password is empty" }
        password = sqlPassword
        return this
    }

    @Throws(IllegalArgumentException::class)
    fun setDbName(sqlDbName: String): SQLConnectionSettings {
        require(sqlDbName.isNotBlank()) { "SQL database name is empty" }
        dbName = sqlDbName
        return this
    }
}