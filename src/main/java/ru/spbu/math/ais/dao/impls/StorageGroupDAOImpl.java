package ru.spbu.math.ais.dao.impls;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.spbu.math.ais.dao.StorageGroupDAO;
import ru.spbu.math.ais.model.StorageGroup;

@Repository
public class StorageGroupDAOImpl implements StorageGroupDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public List<StorageGroup> getAllGroups() {
		Session session = sessionFactory.getCurrentSession();
		List<StorageGroup> storageGroups = (List<StorageGroup>)session.createQuery("from StorageGroup").list();
		return storageGroups;
	}

}
