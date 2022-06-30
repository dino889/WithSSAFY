package com.ssafy.withssafy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPro is a Querydsl query type for Pro
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPro extends EntityPathBase<Pro> {

    private static final long serialVersionUID = 1451325961L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPro pro = new QPro("pro");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser user;

    public QPro(String variable) {
        this(Pro.class, forVariable(variable), INITS);
    }

    public QPro(Path<? extends Pro> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPro(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPro(PathMetadata metadata, PathInits inits) {
        this(Pro.class, metadata, inits);
    }

    public QPro(Class<? extends Pro> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

