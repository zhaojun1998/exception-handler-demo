package im.zhaojun.service;

import im.zhaojun.exception.AgeDeleteException;
import im.zhaojun.mapper.UserMapper;
import im.zhaojun.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    public int delete(Integer id) {
        // 业务认为不允许删除 18 岁一下的用户.
        if (selectByPrimaryKey(id).getAge() < 18) {
            throw new AgeDeleteException();
        }
        return userMapper.deleteByPrimaryKey(id);
    }

    public int add(User user) {
        return userMapper.insert(user);
    }
}
