package entity.commericalOffer;


import entity.commercialOrder.CommercialOrder;
import entity.base.BaseEntity;
import entity.user.Expert;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

//offers that are set to a certain service that is requested by the customer
public class CommercialOffer extends BaseEntity<Integer> {

    //each offer is mapped to only one expert.
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "offer_expert",referencedColumnName = "id")
    private Expert offerExpert;

//    //each offer is associated with a service that is provided by the expert.
//    @OneToOne
//    private Service offerService;

    @ManyToOne
    @JoinColumn(name = "offer_order",referencedColumnName = "id")
    private CommercialOrder offerCommercialOrder;


    //price that is offered for the specified service by the expert.
    private double offeredPrice;


    //expert telling customer when they can start the job
    private LocalDateTime startTime;
    private LocalDateTime finishTime;

    //expert telling customer how long it takes to finish the job
    @Transient
    private Duration duration;

    //it is selected by the customer as the favorite offer
    private boolean isSelected;

    //it is accepted by the expert
    private boolean isAccepted;

    public void removeExpert(){
        setOfferExpert(null);
    }
    public void changeExpert(Expert newExpert){
        setOfferExpert(newExpert);
    }

    public void removeOrder(){
        setOfferCommercialOrder(null);
    }

    public void changeOrder(CommercialOrder newCommercialOrder){
        setOfferCommercialOrder(newCommercialOrder);
    }
}
