package com.ssafy.withssafy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSbComment is a Querydsl query type for SbComment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSbComment extends EntityPathBase<SbComment> {

    private static final long serialVersionUID = 819684204L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSbComment sbComment = new QSbComment("sbComment");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> parent = createNumber("parent", Long.class);

    public final QStudyBoard studyBoard;

    public final QUser user;

    public final StringPath write_dt = createString("write_dt");

    public QSbComment(String variable) {
        this(SbComment.class, forVariable(variable), INITS);
    }

    public QSbComment(Path<? extends SbComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSbComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSbComment(PathMetadata metadata, PathInits inits) {
        this(SbComment.class, metadata, inits);
    }

    public QSbComment(Class<? extends SbComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studyBoard = inits.isInitialized("studyBoard") ? new QStudyBoard(forProperty("studyBoard"), inits.get("studyBoard")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

