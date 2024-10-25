package com.example.nestco.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemThumbnail is a Querydsl query type for ItemThumbnail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemThumbnail extends EntityPathBase<ItemThumbnail> {

    private static final long serialVersionUID = 371775641L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemThumbnail itemThumbnail = new QItemThumbnail("itemThumbnail");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final StringPath imagePath = createString("imagePath");

    public final QNestcoItems items;

    public final NumberPath<Long> thumbnailId = createNumber("thumbnailId", Long.class);

    public QItemThumbnail(String variable) {
        this(ItemThumbnail.class, forVariable(variable), INITS);
    }

    public QItemThumbnail(Path<? extends ItemThumbnail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemThumbnail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemThumbnail(PathMetadata metadata, PathInits inits) {
        this(ItemThumbnail.class, metadata, inits);
    }

    public QItemThumbnail(Class<? extends ItemThumbnail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.items = inits.isInitialized("items") ? new QNestcoItems(forProperty("items"), inits.get("items")) : null;
    }

}

