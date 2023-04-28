package mate.cafecatalog.service;

import java.util.List;
import java.util.Map;
import mate.cafecatalog.model.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CafeService {

    Cafe save(Cafe cafe);

    Page<Cafe> findAll(Map<String, String> params, PageRequest pageRequest);

    Cafe get(Long id);

    void processRating(Long cafeId, Integer rating);

    Page<Cafe> findAllByIdIn(List<Long> favouriteIds, PageRequest pageRequest);
}
