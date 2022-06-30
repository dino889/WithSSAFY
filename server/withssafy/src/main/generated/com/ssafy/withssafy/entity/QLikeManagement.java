package com.ssafy.withssafy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeManagement is a Querydsl query type for LikeManagement
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLikeManagement extends EntityPathBase<LikeManagement> {

    private static final long serialVersionUID = -304678306L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeManagement likeManagement = new QLikeManagement("likeManagement");

    public final QBoard board;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser user;

    public QLikeManagement(String variable) {
        this(LikeManagement.class, forVariable(variable), INITS);
    }

    public QLikeManagement(Path<? extends LikeManagement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeManagement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeManagement(PathMetadata metadata, PathInits inits) {
        this(LikeManagement.class, metadata, inits);
    }

    public QLikeManagement(Class<? extends LikeManagement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

