package com.example.nestco.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 932486426L;

    public static final QMember member = new QMember("member1");

    public final StringPath address = createString("address");

    public final StringPath block = createString("block");

    public final StringPath createDate = createString("createDate");

    public final NumberPath<Integer> declaration = createNumber("declaration", Integer.class);

    public final StringPath detailedAddress = createString("detailedAddress");

    public final StringPath email = createString("email");

    public final StringPath nickname = createString("nickname");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final NumberPath<Integer> postalAddress = createNumber("postalAddress", Integer.class);

    public final StringPath profileImagePath = createString("profileImagePath");

    public final StringPath provider = createString("provider");

    public final StringPath role = createString("role");

    public final StringPath userId = createString("userId");

    public final StringPath username = createString("username");

    public final StringPath userPassword = createString("userPassword");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

