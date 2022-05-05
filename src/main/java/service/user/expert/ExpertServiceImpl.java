package service.user.expert;

import entity.user.Expert;
import repository.user.expert.ExpertRepo;
import service.base.UserServiceImpl;

public class ExpertServiceImpl extends UserServiceImpl<Expert, ExpertRepo> implements ExpertService {


    public ExpertServiceImpl(ExpertRepo repo) {
        super(repo);
    }
}
