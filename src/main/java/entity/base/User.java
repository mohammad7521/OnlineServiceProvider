package entity.base;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;



@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type",
        discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "users")

public abstract class User extends BaseEntity<Integer> {

    @Column(unique = true,nullable = false)
    protected String username;

    @Column(nullable = false)
    protected String password;
    protected String firstname;
    protected String lastname;

    @Column(unique = true)
    protected String email;

    @Transient
    public int getDiscriminatorValue(){
        return Integer.parseInt(this.getClass().getAnnotation(DiscriminatorValue.class).value());
    }
}

