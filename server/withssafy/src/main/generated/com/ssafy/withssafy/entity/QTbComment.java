package com.ssafy.withssafy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTbComment is a Querydsl query type for TbComment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbComment extends EntityPathBase<TbComment> {

    private static final long serialVersionUID = -987770259L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTbComment tbComment = new QTbComment("tbComment");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> parent = createNumber("parent", Integer.class);

    public final QTeamBuilding tb_id;

    public final QUser user_id;

    public QTbComment(String variable) {
        this(TbComment.class, forVariable(variable), INITS);
    }

    public QTbComment(Path<? extends TbComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTbComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTbComment(PathMetadata metadata, PathInits inits) {
        this(TbComment.class, metadata, inits);
    }

    public QTbComment(Class<? extends TbComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tb_id = inits.isInitialized("tb_id") ? new QTeamBuilding(forProperty("tb_id"), inits.get("tb_id")) : null;
        this.user_id = inits.isInitialized("user_id") ? new QUser(forProperty("user_id"), inits.get("user_id")) : null;
    }

}

