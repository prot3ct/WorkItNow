package prot3ct.workit.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import prot3ct.workit.utils.base.GsonParserContract;

public class GsonParser implements GsonParserContract {

    @Override
    public String toJson(Object src) {
        Gson gson = new Gson();
        return gson.toJson(src);
    }

    @Override
    public <T> T fromJson(String json, Type classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

}