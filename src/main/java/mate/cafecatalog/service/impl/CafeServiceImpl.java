package mate.cafecatalog.service.impl;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import mate.cafecatalog.exception.DataProcessingException;
import mate.cafecatalog.model.Cafe;
import mate.cafecatalog.repository.CafeRepository;
import mate.cafecatalog.repository.specification.CafeSpecificationManager;
import mate.cafecatalog.service.CafeService;
import mate.cafecatalog.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService {
    private final CafeRepository cafeRepository;
    private final CafeSpecificationManager cafeSpecificationManager;
    private final CommentService commentService;

    @Override
    public Cafe save(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    @Override
    public Page<Cafe> findAll(Map<String, String> params, PageRequest pageRequest) {
        Specification<Cafe> specification = null;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            Specification<Cafe> sp = cafeSpecificationManager.get(entry.getKey(),
                    entry.getValue().split(","));
            specification = specification == null
                    ? Specification.where(sp) : specification.and(sp);
        }
        return cafeRepository.findAll(specification, pageRequest);
    }

    @Override
    public Cafe get(Long id) {
        return cafeRepository.findById(id).orElseThrow(
                () -> new DataProcessingException("Cafe with id " + id + " not found"));
    }

    @Override
    public void processRating(Long cafeId, Integer rating) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(
                () -> new DataProcessingException("Cafe with id " + cafeId + " not found"));
        if (commentService.getAvgRatingByCafeId(cafeId) == null) {
            cafe.setRating(rating);
        } else {
            cafe.setRating(commentService.getAvgRatingByCafeId(cafeId));
        }
        cafeRepository.save(cafe);
    }

    @Override
    public Page<Cafe> findAllByIdIn(List<Long> favouriteIds, PageRequest pageRequest) {
        return cafeRepository.findAllByIdIn(favouriteIds, pageRequest);
    }
}
