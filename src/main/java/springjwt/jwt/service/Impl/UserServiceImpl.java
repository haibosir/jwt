package springjwt.jwt.service.Impl;

import org.springframework.stereotype.Service;
import springjwt.jwt.domain.User;
import springjwt.jwt.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
    @Override
    public boolean checkUser(String loginName, String passWord) {
        return true;
    }

    @Override
    public User getUser(String loginName) {
        User user = new User();
        user.setName("李四");
        user.setPassword("123");
        return user;
    }
}
