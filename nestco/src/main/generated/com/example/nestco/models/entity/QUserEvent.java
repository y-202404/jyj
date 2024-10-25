package com.example.nestco.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEvent is a Querydsl query type for UserEvent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEvent extends EntityPathBase<UserEvent> {

    private static final long serialVersionUID = -1171907825L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserEvent userEvent = new QUserEvent("userEvent");

    public final StringPath eventJoin = createString("eventJoin");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember memberId;

    public final StringPath result = createString("result");

    public QUserEvent(String variable) {
        this(UserEvent.class, forVariable(variable), INITS);
    }

    public QUserEvent(Path<? extends UserEvent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserEvent(PathMetadata metadata, PathInits inits) {
        this(UserEvent.class, metadata, inits);
    }

    public QUserEvent(Class<? extends UserEvent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberId = inits.isInitialized("memberId") ? new QMember(forProperty("memberId")) : null;
    }

}

