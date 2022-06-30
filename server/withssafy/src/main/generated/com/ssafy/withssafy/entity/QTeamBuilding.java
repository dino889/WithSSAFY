package com.ssafy.withssafy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamBuilding is a Querydsl query type for TeamBuilding
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTeamBuilding extends EntityPathBase<TeamBuilding> {

    private static final long serialVersionUID = 66702421L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamBuilding teamBuilding = new QTeamBuilding("teamBuilding");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath photo_path = createString("photo_path");

    public final NumberPath<Integer> tb_limit = createNumber("tb_limit", Integer.class);

    public final StringPath title = createString("title");

    public final QUser user;

    public QTeamBuilding(String variable) {
        this(TeamBuilding.class, forVariable(variable), INITS);
    }

    public QTeamBuilding(Path<? extends TeamBuilding> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamBuilding(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamBuilding(PathMetadata metadata, PathInits inits) {
        this(TeamBuilding.class, metadata, inits);
    }

    public QTeamBuilding(Class<? extends TeamBuilding> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

