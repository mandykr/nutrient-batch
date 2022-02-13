package mandykr.nutrient.batch.openapi.domain;

import java.util.List;

public interface ApiRequester<T> {
    void request();
    List<T> getResponses();
}
