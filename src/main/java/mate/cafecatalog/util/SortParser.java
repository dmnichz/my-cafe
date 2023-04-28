package mate.cafecatalog.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortParser {
    private static final String FIELD_PARSE_REGEX = ";";
    private static final String FIELD_DIR_PARSE_REGEX = ":";
    private static final int FIELD_VALUE_INDEX = 0;
    private static final int DIR_VALUE_INDEX = 1;

    public Sort parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(FIELD_DIR_PARSE_REGEX)) {
            String[] sortingFields = sortBy.split(FIELD_PARSE_REGEX);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(FIELD_DIR_PARSE_REGEX)) {
                    String[] fieldsAndDirs = field.split(FIELD_DIR_PARSE_REGEX);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirs[DIR_VALUE_INDEX]),
                            fieldsAndDirs[FIELD_VALUE_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
