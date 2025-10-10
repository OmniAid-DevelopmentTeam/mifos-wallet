/*
 * Copyright 2024 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See https://github.com/openMF/mobile-wallet/blob/master/LICENSE.md
 */
package org.mifospay.core.network.utils

object BaseURL {
    private const val PROTOCOL_HTTPS = "https://"

    const val HEADER_TENANT = "Fineract-Platform-TenantId"
    const val HEADER_AUTH = "Authorization"

    val url: String
        get() = PROTOCOL_HTTPS + NetworkConfig.API_ENDPOINT + NetworkConfig.API_PATH

    val selfServiceUrl: String
        get() = PROTOCOL_HTTPS + NetworkConfig.API_ENDPOINT_SELF + NetworkConfig.API_PATH_SELF
}
