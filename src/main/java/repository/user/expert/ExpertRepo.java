package repository.user.expert;

import entity.user.Expert;
import repository.base.BaseRepo;
import repository.base.UserRepo;

public interface ExpertRepo extends BaseRepo<Expert,Integer>, UserRepo<Expert> {
}
