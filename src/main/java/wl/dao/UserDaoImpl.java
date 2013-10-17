package wl.dao;

import org.springframework.stereotype.Component;

import wl.model.Tuser;

@Component("userDao")
public class UserDaoImpl extends BaseDaoImpl<Tuser> implements UserDaoI
{

}
