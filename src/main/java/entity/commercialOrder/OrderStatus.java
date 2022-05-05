package entity.commercialOrder;


//Different statuses of a single order
public enum OrderStatus {
    WAITING_FOR_EXPERT_OFFERS,
    EXPERT_CHOOSING,
    EXPERT_ON_WAY,
    STARTED,
    DONE,
    PAID,
}
