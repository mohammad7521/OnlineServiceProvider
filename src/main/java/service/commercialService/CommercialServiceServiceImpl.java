package service.commercialService;



import entity.commercialService.CommercialService;
import exception.DuplicateServiceName;
import repository.commercialService.CommercialServiceRepo;
import service.base.BaseServiceImpl;

import java.sql.Date;
import java.sql.Time;
import java.util.List;


public class CommercialServiceServiceImpl extends BaseServiceImpl<CommercialService,Integer,CommercialServiceRepo>
implements CommercialServiceService{


    public CommercialServiceServiceImpl(CommercialServiceRepo repo) {
        super(repo);
    }


    //handling the exception of adding commercial services with duplicate names
    @Override
    public CommercialService add(CommercialService commercialService) {
        Date date=new Date(System.currentTimeMillis());
        Time time=new Time(System.currentTimeMillis());
        if (checkDuplicateName(commercialService)){
            throw new DuplicateServiceName();
        }

        else
        commercialService.setDate(date);
        commercialService.setTime(time);
        return repo.add(commercialService);
    }


    public boolean checkDuplicateName(CommercialService commercialService){
        boolean flag = false;
        List<CommercialService> list = repo.showAll(CommercialService.class);


        if (list.size()>1) {
            for (CommercialService cs : list) {
                if (commercialService.getName().equals(cs.getName())) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}
