package entity.user;

import entity.base.BaseEntity;
import entity.user.Customer;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Address extends BaseEntity<Integer> {

    private String city;
    private String district;
    private String fullAddress;
    private int plaque;

    @ManyToOne
    @JoinColumn(name="customer_id",referencedColumnName = "id")
    private Customer addressCustomer;
}
