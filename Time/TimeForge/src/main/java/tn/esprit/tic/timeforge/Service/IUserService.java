package tn.esprit.tic.timeforge.Service;

import tn.esprit.tic.timeforge.Entity.User;

public interface IUserService {
    User getUserById(Long userId);
    User getUserByUsername(String username);

}
