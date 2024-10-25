package com.example.nestco.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOneOnOne is a Querydsl query type for OneOnOne
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOneOnOne extends EntityPathBase<OneOnOne> {

    private static final long serialVersionUID = 1108167617L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOneOnOne oneOnOne = new QOneOnOne("oneOnOne");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember memberId;

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath type = createString("type");

    public QOneOnOne(String variable) {
        this(OneOnOne.class, forVariable(variable), INITS);
    }

    public QOneOnOne(Path<? extends OneOnOne> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOneOnOne(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOneOnOne(PathMetadata metadata, PathInits inits) {
        this(OneOnOne.class, metadata, inits);
    }

    public QOneOnOne(Class<? extends OneOnOne> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberId = inits.isInitialized("memberId") ? new QMember(forProperty("memberId")) : null;
    }

}

