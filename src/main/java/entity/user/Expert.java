package entity.user;

import entity.commericalOffer.CommercialOffer;
import entity.base.User;
import entity.commercialService.CommercialService;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@DiscriminatorValue("3")
public class Expert extends User {

    private SignUpStatus signUpStatus;
    private String photoAddress;
//    private double score;

    //each expert can provide as many services as they like.
    @ManyToMany(mappedBy = "serviceExperts",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<CommercialService> expertCommercialServices = new HashSet<>();

    @OneToMany(mappedBy = "offerExpert",fetch = FetchType.EAGER)
    private Set<CommercialOffer> expertCommercialOffers = new HashSet<>();

    private double balance;

    public void addExpertCommercialService (CommercialService commercialService){
        expertCommercialServices.add(commercialService);
    }

    public void removeExpertCommercialService(CommercialService commercialService){
        expertCommercialServices.remove(commercialService);
    }

    public void addPhotoAddress(String photoAddress){
        this.photoAddress=photoAddress;
    }
}
