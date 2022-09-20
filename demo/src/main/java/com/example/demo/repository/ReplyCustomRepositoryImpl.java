package com.example.demo.repository;

import com.example.demo.dto.ReplyDto;
import com.example.demo.entity.QBoard;
import com.example.demo.entity.QReply;
import com.example.demo.entity.Reply;
import com.example.demo.repository.search.BoardSearch;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.example.demo.entity.QBoard.*;
import static com.example.demo.entity.QReply.*;

public class ReplyCustomRepositoryImpl extends QuerydslRepositorySupport implements ReplyCustomRepository {

    public ReplyCustomRepositoryImpl() {
        super(Reply.class);
    }

    @Override
    public Page<ReplyDto> list(Pageable pageable) {
/*        JPQLQuery<Reply> query = from(reply);

        JPQLQuery<ReplyDto> dtoJPQLQuery = query.select(Projections.fields(ReplyDto.class,
                reply.reply_id,
                reply.board,
                reply.replyText,
                reply.createdDate,
                reply.username
                ));

        this.getQuerydsl().applyPagination(pageable,dtoJPQLQuery);
        List<ReplyDto> dtoList = dtoJPQLQuery.fetch();
        long count = dtoJPQLQuery.fetchCount();*/

        return null;
    }

    @Override
    public Page<ReplyDto> getListOfBoard(Long board_id,Pageable pageable) {

        JPQLQuery<Reply> query = from(reply);
        query.leftJoin(board).on(board.eq(reply.board));
        query.where(board.id.eq(board_id));

        JPQLQuery<ReplyDto> dtoJPQLQuery = query.select(Projections.bean(ReplyDto.class,
                reply.id,
                board.id,
                reply.replyText,
                reply.createdDate,
                reply.username,
                reply.modifiedDate));

        this.getQuerydsl().applyPagination(pageable,dtoJPQLQuery);
        List<ReplyDto> dtoList = dtoJPQLQuery.fetch();
        long count = dtoJPQLQuery.fetchCount();

        return new PageImpl<>(dtoList,pageable,count);
    }
}
