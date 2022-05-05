package entity.user;


import entity.base.User;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Builder
@DiscriminatorValue("1")
public class Admin extends User {


}
