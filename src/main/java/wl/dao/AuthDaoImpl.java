package wl.dao;

import org.springframework.stereotype.Component;

import wl.model.Tauth;

@Component("authDao")
public class AuthDaoImpl extends BaseDaoImpl<Tauth> implements AuthDaoI
{

}
