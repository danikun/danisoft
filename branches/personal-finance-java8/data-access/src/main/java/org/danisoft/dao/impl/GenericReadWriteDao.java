package org.danisoft.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.danisoft.dao.IReadDao;
import org.danisoft.dao.IWriteDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericReadWriteDao<K extends Serializable,T> implements IReadDao<K,T>, IWriteDao<K,T>{

	private Class<T> clazz;
	private SessionFactory sessionFactory;
	
	protected GenericReadWriteDao(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public K save(T object) {
		return (K)sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void update(T object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);		
	}

	@Override
	public void delete(T object) {
		sessionFactory.getCurrentSession().delete(object);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(K id) {
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked") 
	@Override
	public List<T> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(clazz).list();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
