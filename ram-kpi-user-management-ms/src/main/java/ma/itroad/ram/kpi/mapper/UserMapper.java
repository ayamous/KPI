package ma.itroad.ram.kpi.mapper;

import ma.itroad.ram.kpi.model.UserDTO;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, UserRepresentation> {
}
