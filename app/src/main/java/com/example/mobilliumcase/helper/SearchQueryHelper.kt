package com.example.mobilliumcase.helper

fun searchQueryMap(
    page: Int,
    query: String
): MutableMap<String, String> {
    val map = HashMap<String, String>()
    map["page"] = page.toString()
    map["query"] = query
    return map
}