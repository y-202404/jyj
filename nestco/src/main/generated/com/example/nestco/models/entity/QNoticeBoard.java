package com.example.nestco.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoticeBoard is a Querydsl query type for NoticeBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeBoard extends EntityPathBase<NoticeBoard> {

    private static final long serialVersionUID = 46689390L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticeBoard noticeBoard = new QNoticeBoard("noticeBoard");

    public final StringPath content = createString("content");

    public final StringPath createDate = createString("createDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> readCount = createNumber("readCount", Integer.class);

    public final StringPath title = createString("title");

    public final QMember uploader;

    public QNoticeBoard(String variable) {
        this(NoticeBoard.class, forVariable(variable), INITS);
    }

    public QNoticeBoard(Path<? extends NoticeBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticeBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticeBoard(PathMetadata metadata, PathInits inits) {
        this(NoticeBoard.class, metadata, inits);
    }

    public QNoticeBoard(Class<? extends NoticeBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.uploader = inits.isInitialized("uploader") ? new QMember(forProperty("uploader")) : null;
    }

}

