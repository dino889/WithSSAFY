package com.ssafy.withssafy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClassManager is a Querydsl query type for ClassManager
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QClassManager extends EntityPathBase<ClassManager> {

    private static final long serialVersionUID = -1650612423L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClassManager classManager = new QClassManager("classManager");

    public final QClassRoom classRoom;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser user;

    public QClassManager(String variable) {
        this(ClassManager.class, forVariable(variable), INITS);
    }

    public QClassManager(Path<? extends ClassManager> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClassManager(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClassManager(PathMetadata metadata, PathInits inits) {
        this(ClassManager.class, metadata, inits);
    }

    public QClassManager(Class<? extends ClassManager> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.classRoom = inits.isInitialized("classRoom") ? new QClassRoom(forProperty("classRoom")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

