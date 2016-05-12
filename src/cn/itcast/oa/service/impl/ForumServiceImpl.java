package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.service.ForumService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class ForumServiceImpl extends DaoSupportImpl<Forum> implements
		ForumService {

	@Override
	public void save(Forum forum) {
		// 保存
		super.save(forum);
		// 设置position的值
		forum.setPosition(forum.getId().intValue());
	}

	@Override
	public List<Forum> findAll() {
		return getSession().createQuery("FROM Forum f ORDER BY f.position")
				.list();
	}

	@Override
	public void moveUp(Long id) {
		// 找出相关的Forum，
		Forum forum = getById(id); // 当前要移动的Forum
		Forum other = (Forum) getSession()
				.createQuery(
						"FROM Forum f WHERE f.position<? ORDER BY f.position DESC")
				.setFirstResult(0).setMaxResults(1)
				.setParameter(0, forum.getPosition()).uniqueResult(); // 上面那个Forum

		if (other == null) {
			return;
		}
		// 交换position的值
		int temp = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);

		// 更新到数据库中
		getSession().update(forum);
		getSession().update(other);
	}

	@Override
	public void moveDown(Long id) {
		// 找出相关的Forum，
		Forum forum = getById(id); // 当前要移动的Forum
		Forum other = (Forum) getSession()
				.createQuery(
						"FROM Forum f WHERE f.position>? ORDER BY f.position ASC")
				.setFirstResult(0).setMaxResults(1)
				.setParameter(0, forum.getPosition()).uniqueResult(); // 下面那个Forum

		if (other == null) {
			return;
		}
		// 交换position的值
		int temp = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);

		// 更新到数据库中
		getSession().update(forum);
		getSession().update(other);

	}

}
