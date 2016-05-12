package cn.itcast.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.dao.RoleDao;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl extends DaoSupportImpl<Role> implements RoleService {
	
	/*@Resource
	private RoleDao roleDao;

	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

	@Override
	public void delete(Long id) {
		roleDao.delete(id);
	}

	@Override
	public void save(Role role) {
		roleDao.sava(role); 
	}

	@Override
	public Role getById(Long id) {
		return roleDao.getById(id);
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}*/
	
}
