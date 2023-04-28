package mate.cafecatalog.dto.mapper;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T t);
}
