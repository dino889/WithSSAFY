package com.ssafy.withssafy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitLikeManagement is a Querydsl query type for RecruitLikeManagement
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRecruitLikeManagement extends EntityPathBase<RecruitLikeManagement> {

    private static final long serialVersionUID = -1119638924L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitLikeManagement recruitLikeManagement = new QRecruitLikeManagement("recruitLikeManagement");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRecruit recruit;

    public final QUser user;

    public QRecruitLikeManagement(String variable) {
        this(RecruitLikeManagement.class, forVariable(variable), INITS);
    }

    public QRecruitLikeManagement(Path<? extends RecruitLikeManagement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitLikeManagement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitLikeManagement(PathMetadata metadata, PathInits inits) {
        this(RecruitLikeManagement.class, metadata, inits);
    }

    public QRecruitLikeManagement(Class<? extends RecruitLikeManagement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recruit = inits.isInitialized("recruit") ? new QRecruit(forProperty("recruit"), inits.get("recruit")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

