package com.ssafy.withssafy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyBoard is a Querydsl query type for StudyBoard
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudyBoard extends EntityPathBase<StudyBoard> {

    private static final long serialVersionUID = 1838653377L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyBoard studyBoard = new QStudyBoard("studyBoard");

    public final StringPath area = createString("area");

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Byte> isOuting = createNumber("isOuting", Byte.class);

    public final StringPath photoPath = createString("photoPath");

    public final NumberPath<Integer> sbLimit = createNumber("sbLimit", Integer.class);

    public final SetPath<StudyMember, QStudyMember> studyMembers = this.<StudyMember, QStudyMember>createSet("studyMembers", StudyMember.class, QStudyMember.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final QUser user;

    public final StringPath writeDateTime = createString("writeDateTime");

    public QStudyBoard(String variable) {
        this(StudyBoard.class, forVariable(variable), INITS);
    }

    public QStudyBoard(Path<? extends StudyBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyBoard(PathMetadata metadata, PathInits inits) {
        this(StudyBoard.class, metadata, inits);
    }

    public QStudyBoard(Class<? extends StudyBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

