package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.util.QueryHelper;

@SuppressWarnings("unchecked")
@Transactional
// 可以被继承
public class DaoSupportImpl<T> implements DaoSupport<T> {

	@Resource
	private SessionFactory sessionFactory;

	private Class<T> clazz; // TODO

	public DaoSupportImpl() {
		ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
		System.out.println("clazz ---> " + clazz);
	}

	/**
	 * 获取当前可用的Session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(T entity) {
		getSession().save(entity);
	}

	@Override
	public void delete(Long id) {
		Object obj = getById(id);
		if (obj != null) {
			getSession().delete(obj);
		}
	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
	}

	@Override
	public T getById(Long id) {
		if (id == null) {
			return null;
		} else {
			return (T) getSession().get(clazz, id);
		}
	}

	@Override
	public List<T> getByIds(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return Collections.EMPTY_LIST;
		} else {
			return getSession()
					.createQuery(
							"FROM " + clazz.getSimpleName()
									+ " WHERE id IN (:ids)")
					.setParameterList("ids", ids).list();
		}
	}

	@Override
	public List<T> findAll() {
		return getSession().createQuery("FROM " + clazz.getSimpleName()).list();
	}

	@Override
	@Deprecated
	// 公共的查询分页信息的方法
	public PageBean getPageBean(int pageNum, int pageSize, String hql,
			List<Object> parameters) {
		System.out.println("============》DaoSupportImpl.getPageBean()");

		// 查询本页的数据列表
		Query listQuery = getSession().createQuery(hql);
		if (listQuery != null) {
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list();

		// 查询总记录数量
		// hql.indexOf("")
		Query countQuery = getSession().createQuery("SELECT COUNT(*)" + hql);
		if (countQuery != null) {
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) countQuery.uniqueResult();

		return new PageBean(pageNum, pageSize, count.intValue(), list);
	}

	@Override
	// 公共的查询分页信息的方法(最终版)
	public PageBean getPageBean(int pageNum, int pageSize, QueryHelper queryHelper) {
		System.out.println("============》DaoSupportImpl.getPageBean(int pageNum, int pageSize, QueryHelper queryHelper)");
		
		// 参数列表
		List<Object> parameters = queryHelper.getParameters();
		
		// 查询本页的数据列表
		Query listQuery = getSession().createQuery(queryHelper.getListQueryHql());
		if (listQuery != null) {
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list();

		// 查询总记录数量
		Query countQuery = getSession().createQuery(queryHelper.getCountQueryHql());
		if (countQuery != null) {
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) countQuery.uniqueResult();

		return new PageBean(pageNum, pageSize, count.intValue(), list);
	}
}
