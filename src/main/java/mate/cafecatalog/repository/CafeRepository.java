package mate.cafecatalog.repository;

import java.util.List;
import mate.cafecatalog.model.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CafeRepository extends JpaRepository<Cafe, Long>, JpaSpecificationExecutor<Cafe> {
    Page<Cafe> findAllByIdIn(List<Long> favouriteIds, PageRequest pageRequest);
}
