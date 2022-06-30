package ma.itroad.ram.kpi.model;


import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Data
public class UserVm {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private Profile profile;
    private Map<String, List<String>> attributes;

}

