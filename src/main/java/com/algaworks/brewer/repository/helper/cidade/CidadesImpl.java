package com.algaworks.brewer.repository.helper.cidade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.filter.CidadeFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class CidadesImpl implements CidadesQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(cidadeFilter, criteria);
		criteria.createAlias("estado", "e");
		
		return new PageImpl<>(criteria.list(), pageable, total(cidadeFilter));
	}
	
	private void adicionarFiltro(CidadeFilter cidadeFilter, Criteria criteria) {
		if (cidadeFilter != null) {
			
			if (cidadeFilter.getEstado() != null) {
				criteria.add(Restrictions.eq("estado", cidadeFilter.getEstado()));
			}
			if (!StringUtils.isEmpty(cidadeFilter.getNome())) {
				criteria.add(Restrictions.ilike("nome", cidadeFilter.getNome(), MatchMode.ANYWHERE));
			}
		}
	}
	
	private Long total(CidadeFilter cidadeFilter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		adicionarFiltro(cidadeFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
}
