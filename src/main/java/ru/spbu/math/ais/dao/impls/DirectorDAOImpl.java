package ru.spbu.math.ais.dao.impls;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.spbu.math.ais.dao.DirectorDAO;
import ru.spbu.math.ais.model.Director;

@Repository	
public class DirectorDAOImpl implements DirectorDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public List<Director> getAllDirectors() {
		Session session = sessionFactory.getCurrentSession();
		List<Director> directors = (List<Director>)session.createQuery("from Director").list();
		return directors;
	}
}
