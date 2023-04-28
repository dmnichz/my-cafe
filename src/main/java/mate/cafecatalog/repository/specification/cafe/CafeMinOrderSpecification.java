package mate.cafecatalog.repository.specification.cafe;

import mate.cafecatalog.model.Cafe;
import mate.cafecatalog.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CafeMinOrderSpecification implements SpecificationProvider<Cafe> {
    private static final String RANGE_SPLIT_REGEX = "-";
    private static final String BLANK_RANGE_VALUE = "";
    private static final int RANGE_ARRAY_INDEX = 0;
    private static final int FROM_RANGE_ARRAY_INDEX = 0;
    private static final int TO_RANGE_ARRAY_INDEX = 1;
    private static final int MIN_RANGE_VALUE = 0;
    private static final int MAX_RANGE_VALUE = Integer.MAX_VALUE;
    private static final int DEFAULT_ARRAY_LENGTH = 2;
    private static final String FILTER_KEY = "minOrder";
    private static final String FIELD_NAME = "minOrder";

    @Override
    public Specification<Cafe> getSpecification(String[] range) {
        String[] rangeFromTo = range[RANGE_ARRAY_INDEX].split(RANGE_SPLIT_REGEX);
        int from = rangeFromTo[FROM_RANGE_ARRAY_INDEX].equals(BLANK_RANGE_VALUE)
                ? MIN_RANGE_VALUE
                : Integer.parseInt(rangeFromTo[FROM_RANGE_ARRAY_INDEX]);
        int to = rangeFromTo.length < DEFAULT_ARRAY_LENGTH
                ? MAX_RANGE_VALUE
                : Integer.parseInt(rangeFromTo[TO_RANGE_ARRAY_INDEX]);
        return (root, query, cb) -> cb.between(root.get(FIELD_NAME), from, to);
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
