package com.example.nestco.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTransactions is a Querydsl query type for Transactions
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransactions extends EntityPathBase<Transactions> {

    private static final long serialVersionUID = -367805483L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransactions transactions = new QTransactions("transactions");

    public final BooleanPath accepted = createBoolean("accepted");

    public final DateTimePath<java.sql.Timestamp> created_At = createDateTime("created_At", java.sql.Timestamp.class);

    public final QNestcoItems exchangeItem;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public final QMember receiver;

    public final QMember requester;

    public final EnumPath<TransactionStatus> status = createEnum("status", TransactionStatus.class);

    public final DateTimePath<java.sql.Timestamp> update_At = createDateTime("update_At", java.sql.Timestamp.class);

    public final QNestcoItems uploaderItem;

    public QTransactions(String variable) {
        this(Transactions.class, forVariable(variable), INITS);
    }

    public QTransactions(Path<? extends Transactions> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransactions(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransactions(PathMetadata metadata, PathInits inits) {
        this(Transactions.class, metadata, inits);
    }

    public QTransactions(Class<? extends Transactions> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exchangeItem = inits.isInitialized("exchangeItem") ? new QNestcoItems(forProperty("exchangeItem"), inits.get("exchangeItem")) : null;
        this.receiver = inits.isInitialized("receiver") ? new QMember(forProperty("receiver")) : null;
        this.requester = inits.isInitialized("requester") ? new QMember(forProperty("requester")) : null;
        this.uploaderItem = inits.isInitialized("uploaderItem") ? new QNestcoItems(forProperty("uploaderItem"), inits.get("uploaderItem")) : null;
    }

}

