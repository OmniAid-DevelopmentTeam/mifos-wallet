/*
 * Centralized network configuration for Mifos Wallet
 * This consolidates previously scattered constants (URLs, headers, credentials).
 * If you later want environment-based configs, consider wiring these via Gradle
 * properties or a platform-specific resource loader. For now, we keep it simple
 * and KMP-safe using a commonMain singleton.
 */
package org.mifospay.core.network.utils

object NetworkConfig {
    const val API_ENDPOINT: String = "boffice.demo.omniaid.dev"
    const val API_PATH: String = "/cbs/api/v1/"
    const val API_ENDPOINT_SELF: String = "boffice.demo.omniaid.dev"
    const val API_PATH_SELF: String = "/cbs/api/v1/self/"
    const val DEFAULT_TENANT: String = ""
    const val BASIC_USERNAME: String = "mifos"
    const val BASIC_PASSWORD: String = ""
}
