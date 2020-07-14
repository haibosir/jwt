package springjwt.jwt.service;

import springjwt.jwt.domain.User;

public interface IUserService {

    /**
     * 校验用户信息
     * @param loginName
     * @param passWord
     * @return
     */
    boolean checkUser(String loginName, String passWord);

    /**
     * 查询用户信息
     * @param loginName
     * @return
     */
    User getUser(String loginName);
}
