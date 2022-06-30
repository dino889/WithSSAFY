package com.ssafy.withssafy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QClassRoom is a Querydsl query type for ClassRoom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QClassRoom extends EntityPathBase<ClassRoom> {

    private static final long serialVersionUID = 896988367L;

    public static final QClassRoom classRoom = new QClassRoom("classRoom");

    public final StringPath area = createString("area");

    public final StringPath classDescription = createString("classDescription");

    public final NumberPath<Integer> generation = createNumber("generation", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QClassRoom(String variable) {
        super(ClassRoom.class, forVariable(variable));
    }

    public QClassRoom(Path<? extends ClassRoom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClassRoom(PathMetadata metadata) {
        super(ClassRoom.class, metadata);
    }

}

