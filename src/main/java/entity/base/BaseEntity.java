package entity.base;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;


@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity <ID extends Serializable>{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer Id;
    protected Date date;
    protected Time time;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        if (Id == null && that.Id == null)
            return false;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }
}
