package mate.cafecatalog.dto.mapper;

import mate.cafecatalog.dto.response.RoleResponseDto;
import mate.cafecatalog.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements ResponseDtoMapper<RoleResponseDto, Role> {
    public RoleResponseDto mapToDto(Role role) {
        RoleResponseDto dto = new RoleResponseDto();
        dto.setId(role.getId());
        dto.setName(role.getRoleName().name());
        return dto;
    }
}
