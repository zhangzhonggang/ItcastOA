package cn.itcast.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.dao.DepartmentDao;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.service.DepartmentService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class DepartmentServiceImpl extends DaoSupportImpl<Department> implements DepartmentService {

	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public List<Department> findTopList() {
		return sessionFactory.getCurrentSession().createQuery(
				"FROM Department d WHERE d.parent IS NULL")
				.list();
	}

	@Override
	public List<Department> findChildren(Long parentId) {
		return sessionFactory.getCurrentSession().createQuery(
				"FROM Department d WHERE d.parent.id=?")
				.setParameter(0, parentId)
				.list();
	}
	
	/*@Resource
	private DepartmentDao departmentDao;
	
	@Override
	public List<Department> findAll() {
		return departmentDao.findAll();
	}

	@Override
	public void delete(Long id) {
		departmentDao.delete(id);
	}

	@Override
	public void save(Department model) {
		departmentDao.sava(model);
	}

	@Override
	public Department getById(Long id) {
		return departmentDao.getById(id);
	}

	@Override
	public void update(Department department) {
		departmentDao.update(department);
	}*/

}
