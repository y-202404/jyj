package com.example.nestco.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNestcoItems is a Querydsl query type for NestcoItems
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNestcoItems extends EntityPathBase<NestcoItems> {

    private static final long serialVersionUID = 1808264924L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNestcoItems nestcoItems = new QNestcoItems("nestcoItems");

    public final NumberPath<Integer> boardHits = createNumber("boardHits", Integer.class);

    public final QCategory category;

    public final StringPath content = createString("content");

    public final DateTimePath<java.sql.Timestamp> createDate = createDateTime("createDate", java.sql.Timestamp.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath status = createBoolean("status");

    public final ListPath<ItemThumbnail, QItemThumbnail> thumbnails = this.<ItemThumbnail, QItemThumbnail>createList("thumbnails", ItemThumbnail.class, QItemThumbnail.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final DateTimePath<java.sql.Timestamp> updateDate = createDateTime("updateDate", java.sql.Timestamp.class);

    public final QMember uploader;

    public QNestcoItems(String variable) {
        this(NestcoItems.class, forVariable(variable), INITS);
    }

    public QNestcoItems(Path<? extends NestcoItems> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNestcoItems(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNestcoItems(PathMetadata metadata, PathInits inits) {
        this(NestcoItems.class, metadata, inits);
    }

    public QNestcoItems(Class<? extends NestcoItems> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category"), inits.get("category")) : null;
        this.uploader = inits.isInitialized("uploader") ? new QMember(forProperty("uploader")) : null;
    }

}

