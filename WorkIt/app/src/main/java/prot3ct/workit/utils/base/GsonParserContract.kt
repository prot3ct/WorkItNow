package prot3ct.workit.utils.base

import java.lang.reflect.Type

interface GsonParserContract {
    fun toJson(src: Any): String
    fun <T> fromJson(json: String?, classOfT: Type): T
}