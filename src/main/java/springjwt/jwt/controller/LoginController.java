package springjwt.jwt.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springjwt.jwt.domain.User;
import springjwt.jwt.service.IUserService;
import springjwt.jwt.util.AjaxResult;
import springjwt.jwt.util.JwtUtil;
import springjwt.jwt.util.UserLoginToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private IUserService userService;


    @PostMapping("login")
    @ResponseBody
    public AjaxResult login (@RequestBody Map<String,String> map){
        String loginName = map.get("loginName");
        String passWord = map.get("passWord");
        //身份验证
        boolean isSuccess =  userService.checkUser(loginName,passWord);
        if (isSuccess) {
            //模拟数据库查询
            User user = userService.getUser(loginName);
            if (user != null) {
                //返回token
                String token = JwtUtil.sign(loginName, passWord);
                if (token != null) {
                    return AjaxResult.success("成功",token);
                }
            }
        }
        return AjaxResult.fail();
    }

    @PostMapping("getUser")
    @ResponseBody
    @UserLoginToken
    public AjaxResult getUserInfo(HttpServletRequest request, @RequestBody Map<String, String> map){
        String loginName = map.get("loginName");
        String token = request.getHeader("token");
        boolean verity = JwtUtil.verity(token);
        if (verity) {
            User user = userService.getUser(loginName);
            Algorithm algorithm = Algorithm.HMAC256("joijsdfjlsjfljfljl5135313135");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            user.setUserId(String.valueOf(jwt.getClaim("userId").asString()));
            if (user != null) {
                return AjaxResult.success("成功", JSONObject.toJSONString(user));
            }
        }
        return AjaxResult.fail();
    }
}

