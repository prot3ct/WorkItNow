package prot3ct.workit.utils

import prot3ct.workit.utils.base.GsonParserContract
import com.google.gson.Gson
import java.lang.reflect.Type

class GsonParser : GsonParserContract {
    override fun toJson(src: Any): String {
        val gson = Gson()
        return gson.toJson(src)
    }

    override fun <T> fromJson(json: String?, classOfT: Type): T {
        val gson = Gson()
        return gson.fromJson(json, classOfT)
    }
}