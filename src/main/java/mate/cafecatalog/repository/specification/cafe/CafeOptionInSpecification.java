package mate.cafecatalog.repository.specification.cafe;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import mate.cafecatalog.model.Cafe;
import mate.cafecatalog.model.Option;
import mate.cafecatalog.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CafeOptionInSpecification implements SpecificationProvider<Cafe> {
    private static final String JOIN_ENTITY = "options";
    private static final String FILTER_KEY = "option";
    private static final String FIELD_NAME = "name";
    private static final String CAFE_ID_NAME = "id";

    @Override
    public Specification<Cafe> getSpecification(String[] options) {
        return (root, query, cb) -> {

            Predicate predicate = cb.and();
            // ManyToMany, get Café which has few options in cafes_options table, like logical AND

            // https://devramble.netlify.app/criteria_query_many_to_many/
            // Iterate over each option name we want to match with
            for (String value : options) {
                boolean include = true;
                // if value contains ':' and keyword 'no' after ':', set flag include = false
                if (value.contains(":")) {
                    String[] optionInclude = value.split((":"));
                    if (optionInclude[1].equals("no")) {
                        include = false;
                    }
                    // get first part of value - > "alcohol:no", set value = alcohol;
                    // or value = value if there is no ':' or keyword different from 'no'
                    // it means 'yes', include
                    value = optionInclude[0];
                }
                // Initialize the sub-query
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Cafe> subCafeRoot = subquery.from(Cafe.class);
                Join<Option, Cafe> subOptionCafeJoin = subCafeRoot.join(JOIN_ENTITY);

                // Select the Café ID where one of their options matches
                subquery.select(subCafeRoot.get(CAFE_ID_NAME))
                        .where(cb.equal(subOptionCafeJoin.get(FIELD_NAME), value));

                // if keyword 'no' do not present, add to query condition as: value IN
                if (include) {
                    predicate = cb.and(predicate, cb.in(root.get(CAFE_ID_NAME)).value(subquery));
                } else {
                    // if keyword 'no' present, add to query condition as: value NOT IN
                    predicate = cb.and(predicate, cb.not(cb.in(root.get(CAFE_ID_NAME)).value(subquery)));
                }
            }
            query.distinct(true);
            return predicate;
        };
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
