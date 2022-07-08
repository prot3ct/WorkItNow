package prot3ct.workit.models.base;

import java.util.List;
import java.util.Map;

public interface HttpResponseContract {
    Map<String, List<String>> getHeaders();

    String getBody();

    String getMessage();

    int getCode();
}