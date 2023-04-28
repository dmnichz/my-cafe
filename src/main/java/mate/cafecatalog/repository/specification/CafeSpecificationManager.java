package mate.cafecatalog.repository.specification;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import mate.cafecatalog.model.Cafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CafeSpecificationManager implements SpecificationManager<Cafe> {
    private final Map<String, SpecificationProvider<Cafe>> providersMap;

    @Autowired
    public CafeSpecificationManager(List<SpecificationProvider<Cafe>> productSpecifications) {
        providersMap = productSpecifications.stream()
                .collect(Collectors.toMap(SpecificationProvider::getFilterKey,
                        Function.identity()));
    }

    @Override
    public Specification<Cafe> get(String filterKey, String[] params) {
        if (!providersMap.containsKey(filterKey)) {
            throw new RuntimeException("Key " + filterKey
                    + " is not supported for data filtering");
        }
        return providersMap.get(filterKey).getSpecification(params);
    }
}
