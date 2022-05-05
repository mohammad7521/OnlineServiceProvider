package entity.commercialOrder;


import entity.base.BaseEntity;
import entity.commercialService.CommercialService;
import entity.commericalOffer.CommercialOffer;
import entity.user.Customer;
import lombok.*;

import javax.persistence.*;
import javax.transaction.TransactionScoped;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

//orders of service that are set by the customer
public class CommercialOrder extends BaseEntity<Integer> {


    @ManyToOne
    @JoinColumn(name = "order_customer",referencedColumnName = "id")
    private Customer orderCustomer;


    @OneToOne
    private CommercialService commercialService;

    @OneToMany(mappedBy = "offerCommercialOrder",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<CommercialOffer> orderCommercialOffers =new HashSet<>();

    //the offer that is finally associated with the customer order.
    @OneToOne(cascade = CascadeType.ALL)
    private CommercialOffer chosenCommercialOffer;


    @Column(nullable = false)
    private OrderStatus orderStatus;

    //customer price offer for the job
    private double customerPriceOffer;


    //customer description about the job
    private String customerDescription;

    private LocalDateTime startTime;
    private LocalDateTime finishTime;

    @Transient
    private Duration duration;

    private int score;
    private String comment;



    public void addCommercialOffer(CommercialOffer commercialOffer){
        orderCommercialOffers.add(commercialOffer);
    }

    public void removeCommercialOffer(CommercialOffer commercialOffer){
        orderCommercialOffers.remove(commercialOffer);
    }

}
