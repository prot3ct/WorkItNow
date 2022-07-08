package prot3ct.workit.utils.base;

import java.lang.reflect.Type;
import java.util.List;

public interface GsonParserContract {
    String toJson(Object src);

    <T> T fromJson(String json, Type classOfT);
}