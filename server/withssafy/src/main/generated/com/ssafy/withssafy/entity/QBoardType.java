package com.ssafy.withssafy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardType is a Querydsl query type for BoardType
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBoardType extends EntityPathBase<BoardType> {

    private static final long serialVersionUID = -699473508L;

    public static final QBoardType boardType = new QBoardType("boardType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath type = createString("type");

    public QBoardType(String variable) {
        super(BoardType.class, forVariable(variable));
    }

    public QBoardType(Path<? extends BoardType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardType(PathMetadata metadata) {
        super(BoardType.class, metadata);
    }

}

