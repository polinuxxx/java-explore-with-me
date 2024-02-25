package ru.practicum.repo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Component;
import ru.practicum.model.Hit;
import ru.practicum.model.QHit;
import ru.practicum.model.StatProjection;

/**
 * Реализация репозитория для запросов с QueryDSL.
 */
@Component
public class CustomizedHitRepositoryImpl implements CustomizedHitRepository {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public CustomizedHitRepositoryImpl(JpaContext context) {
        EntityManager entityManager = context.getEntityManagerByManagedType(Hit.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<StatProjection> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        QHit qHit = QHit.hit;

        List<BooleanExpression> conditions = new ArrayList<>();
        conditions.add(qHit.creationDate.goe(start));
        conditions.add(qHit.creationDate.loe(end));

        if (uris != null && !uris.isEmpty()) {
            conditions.add(qHit.uri.in(uris));
        }

        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        NumberExpression<Long> countExpression = unique ? qHit.ip.countDistinct() : qHit.ip.count();
        NumberPath<Long> aliasHits = Expressions.numberPath(Long.class, "hits");

        JPAQuery<StatProjection> query = queryFactory
                .select(Projections.bean(StatProjection.class,
                        qHit.app,
                        qHit.uri,
                        countExpression.as(aliasHits)))
                .from(qHit)
                .where(finalCondition)
                .groupBy(qHit.app, qHit.uri)
                .orderBy(aliasHits.desc());

        return query.fetch();
    }
}
