package com.ssafy.withssafy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConsultant is a Querydsl query type for Consultant
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConsultant extends EntityPathBase<Consultant> {

    private static final long serialVersionUID = -2122424417L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConsultant consultant = new QConsultant("consultant");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser user;

    public QConsultant(String variable) {
        this(Consultant.class, forVariable(variable), INITS);
    }

    public QConsultant(Path<? extends Consultant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConsultant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConsultant(PathMetadata metadata, PathInits inits) {
        this(Consultant.class, metadata, inits);
    }

    public QConsultant(Class<? extends Consultant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

