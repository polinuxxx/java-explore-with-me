package ru.practicum.repo.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Component;
import ru.practicum.model.Event;
import ru.practicum.model.EventRequestStatus;
import ru.practicum.model.EventStatus;
import ru.practicum.model.QCategory;
import ru.practicum.model.QEvent;
import ru.practicum.model.QEventRequest;
import ru.practicum.model.QUser;
import ru.practicum.model.SortVariant;
import ru.practicum.model.projection.EventFullProjection;
import ru.practicum.model.projection.EventShortProjection;

/**
 * Реализация репозитория для запросов по событиям с QueryDSL.
 */
@Component
public class CustomizedEventRepositoryImpl implements CustomizedEventRepository {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public CustomizedEventRepositoryImpl(JpaContext context) {
        EntityManager entityManager = context.getEntityManagerByManagedType(Event.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public <T extends EventShortProjection> List<T> findAll(List<Long> users, List<EventStatus> states,
                                              String text, List<Long> categories, Boolean paid,
                                              LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                              Boolean onlyAvailable, SortVariant sort, Pageable pageable,
                                              Boolean detailed) {
        QEvent event = QEvent.event;
        QEventRequest request = QEventRequest.eventRequest;
        QUser user = QUser.user;
        QCategory category = QCategory.category;

        List<BooleanExpression> conditions = new ArrayList<>();
        conditions.add(Expressions.asBoolean(true).isTrue());

        if (users != null && !users.isEmpty()) {
            conditions.add(event.initiator.id.in(users));
        }
        if (states != null && !states.isEmpty()) {
            conditions.add(event.status.in(states));
        }
        if (text != null && !text.isBlank()) {
            conditions.add(event.annotation.containsIgnoreCase(text).or(event.description.containsIgnoreCase(text)));
        }
        if (categories != null && !categories.isEmpty()) {
            conditions.add(event.category.id.in(categories));
        }
        if (paid != null) {
            conditions.add(event.paid.eq(paid));
        }
        if (rangeStart != null) {
            conditions.add(event.eventDate.goe(rangeStart));
        }
        if (rangeEnd != null) {
            conditions.add(event.eventDate.loe(rangeEnd));
        }
        if (onlyAvailable != null && onlyAvailable) {
            conditions.add(event.participantLimit.eq(0)
                    .or(request.id.count().lt(event.participantLimit)));
        }

        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        NumberExpression<Integer> countExpression = request.id.count().castToNum(Integer.class);
        NumberPath<Integer> aliasConfirmedRequests = Expressions.numberPath(Integer.class, "confirmedRequests");

        QBean<EventShortProjection> projection;
        if (detailed) {
            projection = Projections.bean(EventFullProjection.class,
                    event.id,
                    event.initiator,
                    event.annotation,
                    event.category,
                    event.eventDate,
                    event.paid,
                    event.title,
                    countExpression.as(aliasConfirmedRequests),
                    event.views,
                    event.description,
                    event.participantLimit,
                    event.requestModeration,
                    event.location,
                    event.publicationDate,
                    event.status);
        } else {
            projection = Projections.bean(EventShortProjection.class,
                    event.id,
                    event.initiator,
                    event.annotation,
                    event.category,
                    event.eventDate,
                    event.paid,
                    event.title,
                    countExpression.as(aliasConfirmedRequests),
                    event.views);
        }

        JPAQuery<EventShortProjection> query = queryFactory
                .select(projection)
                .from(request, request)
                .rightJoin(request.event, event).on(request.status.eq(EventRequestStatus.CONFIRMED))
                .join(event.initiator, user)
                .join(event.category, category)
                .where(finalCondition)
                .groupBy(event, user, category)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(sort.equals(SortVariant.EVENT_DATE) ? event.eventDate.desc() : event.views.desc(),
                        event.id.asc());

        return (List<T>) query.fetch();
    }
}
