package com.pagaleve.eheinen.api.utils

import org.json.JSONObject

class JSONUtils {

    companion object {
        fun getMessageFromErrorResponse(response: String): String = JSONObject(response).getString("message")
    }
}