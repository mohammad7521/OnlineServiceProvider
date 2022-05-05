package entity.user;

import entity.commercialOrder.CommercialOrder;
import entity.base.User;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@DiscriminatorValue("2")
public class Customer extends User {

    //each customer can have as many addresses as they want.
    @OneToMany(mappedBy = "addressCustomer",fetch = FetchType.EAGER)
    private Set<Address> costumerAddress=new HashSet<>();

    //each customer can have as many orders as they want.
    @OneToMany(mappedBy = "orderCustomer",fetch = FetchType.EAGER)
    private Set<CommercialOrder> customerCommercialOrders =new HashSet<>();

    //customer balance
    private double balance;
}
