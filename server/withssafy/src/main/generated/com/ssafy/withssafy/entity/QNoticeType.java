package com.ssafy.withssafy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNoticeType is a Querydsl query type for NoticeType
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNoticeType extends EntityPathBase<NoticeType> {

    private static final long serialVersionUID = 1069999958L;

    public static final QNoticeType noticeType = new QNoticeType("noticeType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath type = createString("type");

    public QNoticeType(String variable) {
        super(NoticeType.class, forVariable(variable));
    }

    public QNoticeType(Path<? extends NoticeType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNoticeType(PathMetadata metadata) {
        super(NoticeType.class, metadata);
    }

}

