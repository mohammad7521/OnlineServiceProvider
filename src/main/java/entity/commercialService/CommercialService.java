package entity.commercialService;

import entity.base.BaseEntity;
import entity.user.Expert;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity (name = "Service")
@Table(name = "service")

//services that can be provided by experts
public class CommercialService extends BaseEntity<Integer> {

    private String name;

    //self referencing in order to have sub-categories of the main Services.
    @OneToMany(mappedBy = "parentCommercialService",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<CommercialService> subCommercialServices =new HashSet<>();

    @ManyToOne
    @JoinColumn(name="parent_service",referencedColumnName = "id")
    private CommercialService parentCommercialService;

    private double basePrice;

    private String description;

    @ManyToMany
    @JoinTable(name = "service_expert",joinColumns =@JoinColumn(name = "service_Id")
    ,inverseJoinColumns = @JoinColumn(name = "expert_id"))
    private Set<Expert> serviceExperts=new HashSet<>();

    public void addServiceExpert(Expert expert){
        serviceExperts.add(expert);
    }

    public void addSubCommercialService(CommercialService commercialService){
        subCommercialServices.add(commercialService);
    }
}
