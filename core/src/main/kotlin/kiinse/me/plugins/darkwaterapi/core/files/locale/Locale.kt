package kiinse.me.plugins.darkwaterapi.core.files.locale

import kiinse.me.plugins.darkwaterapi.api.files.locale.PlayerLocale

class Locale(private val value: String) : PlayerLocale() {

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is PlayerLocale && this.hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }
}