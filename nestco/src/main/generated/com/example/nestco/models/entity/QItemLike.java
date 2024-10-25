package com.example.nestco.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemLike is a Querydsl query type for ItemLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemLike extends EntityPathBase<ItemLike> {

    private static final long serialVersionUID = 298012618L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemLike itemLike = new QItemLike("itemLike");

    public final QNestcoItems item;

    public final DateTimePath<java.sql.Timestamp> likeDate = createDateTime("likeDate", java.sql.Timestamp.class);

    public final NumberPath<Long> likeId = createNumber("likeId", Long.class);

    public final QMember user;

    public QItemLike(String variable) {
        this(ItemLike.class, forVariable(variable), INITS);
    }

    public QItemLike(Path<? extends ItemLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemLike(PathMetadata metadata, PathInits inits) {
        this(ItemLike.class, metadata, inits);
    }

    public QItemLike(Class<? extends ItemLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QNestcoItems(forProperty("item"), inits.get("item")) : null;
        this.user = inits.isInitialized("user") ? new QMember(forProperty("user")) : null;
    }

}

