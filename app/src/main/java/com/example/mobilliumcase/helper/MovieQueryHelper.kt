package com.example.mobilliumcase.helper

fun movieQueryMap(
    page: Int
): MutableMap<String, String> {
    val map = HashMap<String, String>()
    map["page"] = page.toString()
    return map
}